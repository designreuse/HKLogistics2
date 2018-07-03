package com.hk.logistics.service.impl;

import com.hk.logistics.constants.EnumAwbStatus;
import com.hk.logistics.domain.*;
import com.hk.logistics.enums.EnumChannel;
import com.hk.logistics.enums.EnumWarehouse;
import com.hk.logistics.repository.*;
import com.hk.logistics.service.*;
import com.hk.logistics.repository.search.AwbSearchRepository;
import com.hk.logistics.service.dto.*;
import com.hk.logistics.service.mapper.AwbMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Awb.
 */
@Service
@Transactional
public class AwbServiceImpl implements AwbService {

	private final Logger log = LoggerFactory.getLogger(AwbServiceImpl.class);

	private final AwbRepository awbRepository;

	private final AwbMapper awbMapper;

	private final AwbSearchRepository awbSearchRepository;

	@Autowired
	VendorWHCourierMappingRepository vendorWHCourierMappingRepository;
	@Autowired
	AwbService awbService;
	@Autowired
	CourierCostCalculatorService courierCostCalculatorService;
	@Autowired
	ChannelRepository channelRepository;
	@Autowired
	CourierChannelRepository courierChannelRepository;
	@Autowired
	PincodeRepository pincodeRepository;
	@Autowired
	WarehouseService warehouseService;
	@Autowired
	VendorService vendorService;
	@Autowired
	PincodeCourierService pincodeCourierService;
	@Autowired
	CourierRepository courierRepository;
	@Autowired
	VariantService variantService;

	public AwbServiceImpl(AwbRepository awbRepository, AwbMapper awbMapper, AwbSearchRepository awbSearchRepository) {
		this.awbRepository = awbRepository;
		this.awbMapper = awbMapper;
		this.awbSearchRepository = awbSearchRepository;
	}

	/**
	 * Save a awb.
	 *
	 * @param awbDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public AwbDTO save(AwbDTO awbDTO) {
		log.debug("Request to save Awb : {}", awbDTO);
		Awb awb = awbMapper.toEntity(awbDTO);
		awb = awbRepository.save(awb);
		AwbDTO result = awbMapper.toDto(awb);
		awbSearchRepository.save(awb);
		return result;
	}

	/**
	 * Get all the awbs.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AwbDTO> findAll() {
		log.debug("Request to get all Awbs");
		return awbRepository.findAll().stream()
				.map(awbMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}


	/**
	 * Get one awb by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<AwbDTO> findOne(Long id) {
		log.debug("Request to get Awb : {}", id);
		return awbRepository.findById(id)
				.map(awbMapper::toDto);
	}

	/**
	 * Delete the awb by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Awb : {}", id);
		awbRepository.deleteById(id);
		awbSearchRepository.deleteById(id);
	}

	/**
	 * Search for the awb corresponding to the query.
	 *
	 * @param query the query of the search
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AwbDTO> search(String query) {
		log.debug("Request to search Awbs for query {}", query);
		return StreamSupport
				.stream(awbSearchRepository.search(queryStringQuery(query)).spliterator(), false)
				.map(awbMapper::toDto)
				.collect(Collectors.toList());
	}



	@Override
	public Awb getAvailableAwbByVendorWHCourierMappingAndCodAndAwbStatusAndChannel(VendorWHCourierMapping vendorWHCourierMapping, Boolean cod, AwbStatus awbStatus, Channel channel) {
		List<Awb> awbList = awbRepository.findByVendorWHCourierMappingAndCodAndAwbStatusAndChannel(vendorWHCourierMapping,
				cod,awbStatus, channel);
		if (awbList != null && !awbList.isEmpty()) {
			return awbList.get(0);
		}
		return null;
	}

	@Override
	public AwbResponse attachAwb(AwbAttachAPIDto awbAttachAPIDto){
		WarehouseDTO warehouseDTO=warehouseService.getWarehouseDTOByFulfillmentCentreCode(awbAttachAPIDto.getFulfillmentCentreCode());
		List<String> variantIds = Arrays.asList(awbAttachAPIDto.getProductVariantId().split("\\s*,\\s*"));
		for(String variantId:variantIds){
			Boolean checkSourceServiceability=variantService.checkIfProductIsServiceableAtSourcePincode(warehouseDTO.getPincode(),variantId);
			if(!checkSourceServiceability)
				return null;
		}
		String vendor=VendorService.vendorShortCodes.get(awbAttachAPIDto.getVendorShortCode())!=null?awbAttachAPIDto.getVendorShortCode():null;
		Long warehouse=warehouseDTO.getId();
		Boolean isHkFulFilled=awbAttachAPIDto.isMarketPlaced()?false:true;
		List<Courier> couriers=courierCostCalculatorService.getBestAvailableCourierList(awbAttachAPIDto.getDestinationPincode(),null, awbAttachAPIDto.isCod(), warehouse,
				awbAttachAPIDto.getAmount(), awbAttachAPIDto.getWeight(), awbAttachAPIDto.isGround(),awbAttachAPIDto.isCardOnDelivery(), awbAttachAPIDto.getChannel(),
				vendor, awbAttachAPIDto.getProductVariantId(), awbAttachAPIDto.getStore(), null, isHkFulFilled);
		Channel channel=channelRepository.findByNameAndStore(awbAttachAPIDto.getChannel(), awbAttachAPIDto.getStore());
		CourierChannel courierChannel=courierChannelRepository.findByCourierAndChannel(couriers.get(0),channel);
		VendorWHCourierMapping vendorWHCourierMapping=vendorWHCourierMappingRepository.findByWarehouseAndCourierAndActive(warehouse, courierChannel.getCourier(), true);
		Awb awb=attachAwbForShipment(couriers.get(0), courierChannel, vendorWHCourierMapping, awbAttachAPIDto.isCod());
		if (awb != null) {
			Courier courier=awb.getVendorWHCourierMapping().getCourier();
			AwbResponse awbCourierResponse=new AwbResponse();
			awbCourierResponse.setAwbBarCode(awb.getAwbBarCode());
			awbCourierResponse.setCourierName(courier.getName());
			awbCourierResponse.setCourierShortCode(courier.getShortCode());
			//awbCourierResponse.setOperationsBitset(courier.getOperationsBitset());//TODO Operation bitset response remove
			awbCourierResponse.setAwbNumber(awb.getAwbNumber());
			awbCourierResponse.setTrackingLink(awb.getTrackingLink());
			return awbCourierResponse;
		}
	return new AwbResponse();//:TODO CardoN Delivery logic
}

@Override
public Awb attachAwbForShipment(Courier suggestedCourier,CourierChannel courierChannel, VendorWHCourierMapping vendorWHCourierMapping,Boolean cod) {
	Awb awb = fetchAwbForShipment(suggestedCourier,vendorWHCourierMapping,courierChannel,cod);
	if (awb != null) {
		awb.setAwbStatus(EnumAwbStatus.Attach.getAsAwbStatus());
		awbRepository.save(awb);
	}
	return awb;
}

@Transactional
private Awb fetchAwbForShipment(Courier suggestedCourier, VendorWHCourierMapping vendorWHCourierMapping, CourierChannel courierChannel,Boolean cod) {
	Awb suggestedAwb;
	suggestedAwb=awbService.getAvailableAwbByVendorWHCourierMappingAndCodAndAwbStatusAndChannel(vendorWHCourierMapping,cod,EnumAwbStatus.Unused.getAsAwbStatus(), courierChannel.getChannel());
	//suggestedAwb = awbService.getAvailableAwbForCourierAndChannelByWarehouseCodStatus(suggestedCourier, shippingOrder.getBaseOrder().getStore().getCourierChannel(), null, shippingOrder.getWarehouse(), isCod, EnumAwbStatus.Unused.getAsAwbStatus(), count, true);
	return suggestedAwb;
}

@Override
public AwbCourierResponse getAwbCourierResponse(AwbCourierRequest awbCourierRequest) {

	if(awbCourierRequest.getVariantId()!=null && !awbCourierRequest.getVariantId().isEmpty()){
		List<String> variantIds = Arrays.asList(awbCourierRequest.getVariantId().split("\\s*,\\s*"));
		if(variantIds!=null && variantIds.size()>0){
			for(String variantId:variantIds){
				Boolean checkSourceServiceability=variantService.checkIfProductIsServiceableAtSourcePincode(awbCourierRequest.getSourcePincode(),variantId);
				if(!checkSourceServiceability)
					return null;
			}
		}
	}
	Boolean isHkFulfilled=true;
	if(EnumChannel.MP.getName().equals(awbCourierRequest.getChannel())){
		isHkFulfilled=false;
	}
	AwbCourierResponse awbCourierResponse = new AwbCourierResponse();
	String routingCode = null;
	Courier courier = null;
	Awb awb = null;
	Long warehouse=null;
	boolean isCod = awbCourierRequest.isCod();
	if (null != awbCourierRequest.getFulfilmentCenterCode()) {
		warehouse = warehouseService.getWarehouseCodeByFulfillmentCentreCode(awbCourierRequest.getFulfilmentCenterCode());
	} else if(WarehouseService.warehouseMap.containsKey(EnumWarehouse.HK_AQUA_WH_4_ID.getId())){
		warehouse = EnumWarehouse.HK_AQUA_WH_4_ID.getId();
	}
	boolean isGroundShipped = awbCourierRequest.isGroundShipping();
	Double totalAmount = awbCourierRequest.getAmount();
	Double estmWeight = awbCourierRequest.getWeightOfBatch();
	String vendorShortCode = awbCourierRequest.getVendor();
	boolean hkShippingProvided = true;
	LocalDate orderPlacedDate = awbCourierRequest.getOrderDate();

	String vendor=VendorService.vendorShortCodes.get(vendorShortCode)!=null?vendorShortCode:null;
	if (awbCourierRequest.getChannel()!=null) {
		Map<Courier, Awb> courierAwbMap = courierCostCalculatorService.getCourierAwbMap(awbCourierRequest.getDestinationPincode(), awbCourierRequest.getSourcePincode(), warehouse, isGroundShipped,
				isCod, totalAmount, estmWeight, vendor, orderPlacedDate, awbCourierRequest.getChannel(), awbCourierRequest.getVariantId(),awbCourierRequest.getStoreId(), isHkFulfilled);

		if (!courierAwbMap.isEmpty() && !courierAwbMap.values().isEmpty()) {
			awb = courierAwbMap.values().iterator().next();
			courier = courierAwbMap.keySet().iterator().next();
			if (courier != null) {
				routingCode=pincodeCourierService.getRoutingCode(awbCourierRequest.getDestinationPincode(), warehouse, courier, isGroundShipped, isCod,awbCourierRequest.getChannel(),
						awbCourierRequest.getSourcePincode(),vendorShortCode,
						awbCourierRequest.getStoreId().toString());
			}
		}
	}

	if (courier != null) {
		awbCourierResponse.setCourierName(courier.getName());
		awbCourierResponse.setCourierId(courier.getId());
		awbCourierResponse.setRoutingCode(routingCode);
		//awbCourierResponse.setOperationsBitset(courier.getOperationsBitset());//TODO Operation bitset response remove
		if (awbCourierRequest.getChannel().equals(EnumChannel.MP.getName())) {
			if (courier.isVendorShipping()) {
				hkShippingProvided = false;
			}
		}
		awbCourierResponse.setHkShippingProvided(hkShippingProvided);
		if (hkShippingProvided && awb != null) {
			awbCourierResponse.setAwbNumber(awb.getAwbNumber());
			awbCourierResponse.setTrackLink(awb.getTrackingLink());
		}
	} else {
		awbCourierResponse = null;
	}

	return awbCourierResponse;
}

@Override
public Awb changeCourier(CourierChangeAPIDto awbChangeAPIDto) {
	//ShippingOrder shippingOrder = shipment.getShippingOrder();
	//Awb currentAwb = shipment.getAwb();
	Courier courier=courierRepository.findByShortCode(awbChangeAPIDto.getCourierShortCode());
	Channel channel=channelRepository.findByNameAndStore(awbChangeAPIDto.getChannel(), awbChangeAPIDto.getStore());
	CourierChannel courierChannel=courierChannelRepository.findByCourierAndChannel(courier, channel);
	Long warehouse=Long.parseLong(awbChangeAPIDto.getWarehouseId());
	VendorWHCourierMapping vendorWHCourierMapping=vendorWHCourierMappingRepository.findByVendorAndWarehouseAndCourierAndActive(null,warehouse, courierChannel.getCourier(), true);
	Awb suggestedAwb = attachAwbForShipment(courier, courierChannel, vendorWHCourierMapping ,true);
	if (suggestedAwb != null) {
		//shipment.setAwb(suggestedAwb);
		//shipment = save(shipment);
		Awb awb=awbRepository.findByVendorWHCourierMappingAndAwbNumberAndCodAndChannel(vendorWHCourierMapping,awbChangeAPIDto.getAwbNumber(),awbChangeAPIDto.isCod(),channel);
		awb.setAwbStatus(EnumAwbStatus.Used.getAsAwbStatus());
		awbRepository.save(awb);
		//getOprStatusSyncToApiService().updateShipmentAwbChanged(shipment);
		return suggestedAwb;
	}
	return null;
}

@Override
public Awb changeAwbNumber(AwbChangeAPIDto awbChangeAPIDto) {
	//ShippingOrder shippingOrder = shipment.getShippingOrder();
	//Awb currentAwb = shipment.getAwb();
	Channel channel=channelRepository.findByNameAndStore(awbChangeAPIDto.getChannel(), awbChangeAPIDto.getStore());
	Courier courier=courierRepository.findByShortCode(awbChangeAPIDto.getCourierShortCode());
	CourierChannel courierChannel=courierChannelRepository.findByCourierAndChannel(courier, channel);
	Long warehouseId=Long.parseLong(awbChangeAPIDto.getWarehouseId());
	WarehouseDTO warehouse=WarehouseService.warehouseMap.get(warehouseId);
	VendorWHCourierMapping vendorWHCourierMapping=vendorWHCourierMappingRepository.findByVendorAndWarehouseAndCourierAndActive
			(null,warehouse.getId(), courierChannel.getCourier(), true);
	Awb oldAwb=awbRepository.findByVendorWHCourierMappingAndAwbNumberAndCodAndChannel(vendorWHCourierMapping,
			awbChangeAPIDto.getOldAwbNumber(),awbChangeAPIDto.isCod(),channel);
	Awb awb=awbRepository.findByVendorWHCourierMappingAndAwbNumberAndCodAndChannel(vendorWHCourierMapping,
			awbChangeAPIDto.getNewAwbNumber(),awbChangeAPIDto.isCod(),channel);
	if(awb==null){
		awb=new Awb();
		awb.setAwbBarCode(oldAwb.getAwbBarCode());
		awb.setAwbNumber(oldAwb.getAwbNumber());
		awb.setChannel(oldAwb.getChannel());
		awb.setCod(oldAwb.isCod());
		awb.setTrackingLink(oldAwb.getTrackingLink());
		awb.setVendorWHCourierMapping(oldAwb.getVendorWHCourierMapping());
		awb.setCreateDate(LocalDate.now());
		awb.setAwbNumber(awbChangeAPIDto.getNewAwbNumber());
		awb.setAwbStatus(EnumAwbStatus.Used.getAsAwbStatus());
		awbRepository.save(awb);
	}
	oldAwb.setAwbStatus(EnumAwbStatus.Used.getAsAwbStatus());
	return awb;
}

@Override
public Awb attachAwbForBright(BrightChangeCourierRequest brightChangeCourierRequest,Courier courier){
	if(courier==null)
		courier=courierRepository.findByShortCode(brightChangeCourierRequest.getCourierShortCode());
	Channel channel=channelRepository.findByNameAndStore(brightChangeCourierRequest.getChannel(), brightChangeCourierRequest.getStore());
	CourierChannel courierChannel=courierChannelRepository.findByCourierAndChannel(courier,channel);
	Long warehouse=warehouseService.getWarehouseCodeByFulfillmentCentreCode(brightChangeCourierRequest.getWarehouseFcCode());
	VendorWHCourierMapping vendorWHCourierMapping=vendorWHCourierMappingRepository.findByVendorAndWarehouseAndCourierAndActive(null,warehouse, courierChannel.getCourier(), true);
	Awb awb=awbService.attachAwbForShipment(courier,courierChannel, vendorWHCourierMapping ,(Boolean)brightChangeCourierRequest.isCod());
	if(brightChangeCourierRequest.getOldAwbNumberToPreserve()!=null){
		Awb oldAwb=awbRepository.findByVendorWHCourierMappingAndAwbNumberAndCodAndChannel(vendorWHCourierMapping, brightChangeCourierRequest.getOldAwbNumberToPreserve(),brightChangeCourierRequest.isCod(),channel);
		oldAwb.setAwbStatus(EnumAwbStatus.Used.getAsAwbStatus());
		awbRepository.save(oldAwb);
	}
	return awb;
}

@Override
public String markAwbUnused(String courierShortCode ,String awbNumber,String store, String channel, String vendorCode, Long warehouse){
	Courier courier = courierRepository.findByShortCode(courierShortCode);
	Channel channel2=channelRepository.findByNameAndStore(channel,store);
	CourierChannel courierChannel=courierChannelRepository.findByCourierAndChannel(courier, channel2);
	VendorWHCourierMapping vendorWHCourierMapping=vendorWHCourierMappingRepository.findByVendorAndWarehouseAndCourierAndActive(vendorCode,warehouse, courierChannel.getCourier(), true);
	String msg=null;
	if(courierChannel != null){
		Awb awb = awbRepository.findByVendorWHCourierMappingAndAwbNumber(vendorWHCourierMapping,awbNumber);
		if(awb != null && awb.getAwbStatus().equals(EnumAwbStatus.Used.getAsAwbStatus())){
			awb.setAwbStatus(EnumAwbStatus.Unused.getAsAwbStatus());
			awbRepository.save(awb);
			msg = "Awb mark unused successfully : ";
		}else {
			msg = "Awb is not in used state : ";
		}
	}else {
		msg = "Courier not found";
	}
	return msg;
}

@Override
public Awb validateAwb(Courier courier ,String awbNumber, String fulfillmentCentreCode,String store,String channelName,String isCod){
	Channel channel=channelRepository.findByNameAndStore(channelName, store);
	CourierChannel courierChannel=courierChannelRepository.findByCourierAndChannel(courier,channel);
	Long warehouse=warehouseService.getWarehouseCodeByFulfillmentCentreCode(fulfillmentCentreCode);
	VendorWHCourierMapping vendorWHCourierMapping=vendorWHCourierMappingRepository.findByVendorAndWarehouseAndCourierAndActive(null,warehouse, courierChannel.getCourier(), true);
	Awb awb = awbRepository.findByVendorWHCourierMappingAndAwbNumberAndCod(vendorWHCourierMapping,awbNumber, Boolean.parseBoolean(isCod));
	return awb;
}

@Override
public Awb markAwbUnused(Courier courier ,String awbNumber, String fulfillmentCentreCode,String store,String channelName,String isCod){
	Channel channel=channelRepository.findByNameAndStore(channelName, store);
	CourierChannel courierChannel=courierChannelRepository.findByCourierAndChannel(courier,channel);
	Long warehouse=warehouseService.getWarehouseCodeByFulfillmentCentreCode(fulfillmentCentreCode);
	VendorWHCourierMapping vendorWHCourierMapping=vendorWHCourierMappingRepository.findByVendorAndWarehouseAndCourierAndActive(null,warehouse, courierChannel.getCourier(), true);
	if(com.mysql.jdbc.StringUtils.isNullOrEmpty(isCod)){
		Awb awb = awbRepository.findByVendorWHCourierMappingAndAwbNumber(vendorWHCourierMapping,awbNumber);
		return awb;
	}
	Awb awb = awbRepository.findByVendorWHCourierMappingAndAwbNumberAndCod(vendorWHCourierMapping,awbNumber, Boolean.parseBoolean(isCod));
	return awb;
}
}
