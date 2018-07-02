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

import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import org.apache.commons.collections4.CollectionUtils;
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
    @Autowired
    VendorWHCourierMappingQueryService vendorWHCourierMappingQueryService;
    @Autowired
    AwbQueryService awbQueryService;
    
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
    public Awb getAvailableAwbByVendorWHCourierMappingAndCodAndAwbStatus(VendorWHCourierMapping vendorWHCourierMapping, Boolean cod, AwbStatus awbStatus) {
        List<Awb> awbList = awbRepository.findByVendorWHCourierMappingAndCodAndAwbStatus(vendorWHCourierMapping,cod,awbStatus);
        if (awbList != null && !awbList.isEmpty()) {
            return awbList.get(0);
        }
        return null;
    }

    @Override
    public Awb attachAwb(AwbAttachAPIDto awbAttachAPIDto){
        WarehouseDTO warehouseDTO=warehouseService.getWarehouseDTOByFulfillmentCentreCode(awbAttachAPIDto.getFulfillmentCentreCode());
        List<String> variantIds = Arrays.asList(awbAttachAPIDto.getProductVariantId().split("\\s*,\\s*"));
        for(String variantId:variantIds){
            Boolean checkSourceServiceability=variantService.checkIfProductIsServiceableAtSourcePincode(warehouseDTO.getPincode(),variantId);
            if(!checkSourceServiceability)
                return null;
        }
        String vendor=VendorService.vendorShortCodes.get(awbAttachAPIDto.getVendorShortCode())!=null?awbAttachAPIDto.getVendorShortCode():null;
        Long warehouse=warehouseDTO.getId();
        List<Courier> couriers=courierCostCalculatorService.getBestAvailableCourierList(awbAttachAPIDto.getDestinationPincode(),null, awbAttachAPIDto.isCod(), warehouse,
            awbAttachAPIDto.getAmount(), awbAttachAPIDto.getWeight(), awbAttachAPIDto.isGround(),awbAttachAPIDto.isCardOnDelivery(), awbAttachAPIDto.getChannel(),
            vendor, awbAttachAPIDto.getProductVariantId(), null);
        Channel channel=channelRepository.findByNameAndStore(awbAttachAPIDto.getChannel(), awbAttachAPIDto.getStore());
        CourierChannel courierChannel=courierChannelRepository.findByCourierAndChannel(couriers.get(0),channel);
        VendorWHCourierMapping vendorWHCourierMapping=vendorWHCourierMappingRepository.findByWarehouseAndCourierAndActive(warehouse, courierChannel.getCourier(), true);
        Awb awb=attachAwbForShipment(couriers.get(0), courierChannel, vendorWHCourierMapping, awbAttachAPIDto.isCod());
        return awb;//:TODO CardoN Delivery logic
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
        suggestedAwb=awbService.getAvailableAwbByVendorWHCourierMappingAndCodAndAwbStatus(vendorWHCourierMapping,cod,EnumAwbStatus.Unused.getAsAwbStatus());
        //suggestedAwb = awbService.getAvailableAwbForCourierAndChannelByWarehouseCodStatus(suggestedCourier, shippingOrder.getBaseOrder().getStore().getCourierChannel(), null, shippingOrder.getWarehouse(), isCod, EnumAwbStatus.Unused.getAsAwbStatus(), count, true);
        return suggestedAwb;
    }

    @Override
    public AwbCourierResponse getAwbCourierResponse(AwbCourierRequest awbCourierRequest) {

        List<String> variantIds = Arrays.asList(awbCourierRequest.getVariantId().split("\\s*,\\s*"));
        for(String variantId:variantIds){
            Boolean checkSourceServiceability=variantService.checkIfProductIsServiceableAtSourcePincode(awbCourierRequest.getSourcePincode(),variantId);
            if(!checkSourceServiceability)
                return null;
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
        Date orderPlacedDate = awbCourierRequest.getOrderDate();

        String vendor=VendorService.vendorShortCodes.get(vendorShortCode)!=null?vendorShortCode:null;
        if (awbCourierRequest.getChannel()!=null) {
            Map<Courier, Awb> courierAwbMap = courierCostCalculatorService.getCourierAwbMap(awbCourierRequest.getDestinationPincode(), awbCourierRequest.getSourcePincode(), warehouse, isGroundShipped,
                isCod, totalAmount, estmWeight, vendor, orderPlacedDate, awbCourierRequest.getChannel(), awbCourierRequest.getVariantId(),awbCourierRequest.getStoreId());

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
            Awb awb=awbRepository.findByAwbNumber(awbChangeAPIDto.getAwbNumber());
            awb.setAwbStatus(EnumAwbStatus.Used.getAsAwbStatus());
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
        Courier courier=courierRepository.findByShortCode(awbChangeAPIDto.getCourierName());
        CourierChannel courierChannel=courierChannelRepository.findByCourierAndChannel(courier, channel);
        WarehouseDTO warehouse=WarehouseService.warehouseMap.get(awbChangeAPIDto.getWarehouseId());
        VendorWHCourierMapping vendorWHCourierMapping=vendorWHCourierMappingRepository.findByVendorAndWarehouseAndCourierAndActive(null,warehouse.getId(), courierChannel.getCourier(), true);
        Awb awb=awbRepository.findByChannelAndAwbNumber(channel,awbChangeAPIDto.getNewAwbNumber());
        awb.setAwbStatus(EnumAwbStatus.Used.getAsAwbStatus());
        awbRepository.save(awb);
        Awb oldAwb=awbRepository.findByChannelAndAwbNumber(channel,awbChangeAPIDto.getNewAwbNumber());
        oldAwb.setAwbStatus(EnumAwbStatus.Used.getAsAwbStatus());
        return null;
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
            Awb oldAwb=awbRepository.findByVendorWHCourierMappingAndAwbNumber(vendorWHCourierMapping, brightChangeCourierRequest.getOldAwbNumberToPreserve());
            oldAwb.setAwbStatus(EnumAwbStatus.Unused.getAsAwbStatus());
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
    
    @Override
	@Transactional
	public List<AwbDTO> upload(List<AwbDTO> batch) {
		log.debug("Request to upload Awb : {}", batch);
		List<Awb> inList = batch.parallelStream().map(dto -> awbMapper.toEntity(dto))
				.collect(Collectors.toList());
		List<Awb> outList = awbRepository.saveAll(inList);
		List<AwbDTO> result = outList.parallelStream().map(awb -> awbMapper.toDto(awb))
				.collect(Collectors.toList());
		awbSearchRepository.saveAll(inList);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public VendorWHCourierMappingDTO getVendorWHCourierMappingByCourierAndWHId(Long courierId, Long whId) {
		VendorWHCourierMappingCriteria vendorWHCourierMappingCriteria = new VendorWHCourierMappingCriteria();
		LongFilter courierIdFilter = new LongFilter();
		courierIdFilter.setEquals(courierId);

		LongFilter whIdFilter = new LongFilter();
		whIdFilter.setEquals(whId);

		vendorWHCourierMappingCriteria.setCourierId(courierIdFilter);
		vendorWHCourierMappingCriteria.setWarehouse(whIdFilter);

		List<VendorWHCourierMappingDTO> list = vendorWHCourierMappingQueryService
				.findByCriteria(vendorWHCourierMappingCriteria);

		if (CollectionUtils.isNotEmpty(list))
			return list.get(0);

		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public VendorWHCourierMappingDTO getVendorWHCourierMappingByCourierAndVendorShortCode(Long courierId,
			String vendorShortCode) {
		VendorWHCourierMappingCriteria vendorWHCourierMappingCriteria = new VendorWHCourierMappingCriteria();
		LongFilter courierIdFilter = new LongFilter();
		courierIdFilter.setEquals(courierId);

		StringFilter vendorFilter = new StringFilter();
		vendorFilter.setEquals(vendorShortCode);

		vendorWHCourierMappingCriteria.setCourierId(courierIdFilter);
		vendorWHCourierMappingCriteria.setVendor(vendorFilter);

		List<VendorWHCourierMappingDTO> list = vendorWHCourierMappingQueryService
				.findByCriteria(vendorWHCourierMappingCriteria);

		if (CollectionUtils.isNotEmpty(list))
			return list.get(0);

		return null;
	}

	@Override
	public List<AwbExcelPojo> getAwbsForExcelDownload(AwbCriteria criteria) {
		VendorWHCourierMappingCriteria vendorWHCourierMappingCriteria = new VendorWHCourierMappingCriteria();
		vendorWHCourierMappingCriteria.setCourierId(criteria.getCourierId());
		List<VendorWHCourierMappingDTO> vendorWHCourierMappingList = vendorWHCourierMappingQueryService
				.findByCriteria(vendorWHCourierMappingCriteria);

		List<AwbExcelPojo> awbExcelPojoList = new ArrayList<AwbExcelPojo>();
		if (CollectionUtils.isNotEmpty(vendorWHCourierMappingList)) {
			for (VendorWHCourierMappingDTO vendorWHCourierMapping : vendorWHCourierMappingList) {
				LongFilter vendorWHCourierMappingFilter = new LongFilter();
				vendorWHCourierMappingFilter.setEquals(vendorWHCourierMapping.getId());
				criteria.setVendorWHCourierMappingId(vendorWHCourierMappingFilter);
				criteria.setCourierId(null);

				List<AwbDTO> dtoList = awbQueryService.findByCriteria(criteria);
				if (CollectionUtils.isNotEmpty(dtoList)) {

					awbExcelPojoList = mapToAwbExcelPojo(dtoList, vendorWHCourierMapping.getCourierId(),
							vendorWHCourierMapping.getVendor(), vendorWHCourierMapping.getWarehouse());
				}
			}
		}
		return awbExcelPojoList;
	}
	
	private List<AwbExcelPojo> mapToAwbExcelPojo(List<AwbDTO> dtoList, Long courierId, String vendorShortCode, Long whId) {
		List<AwbExcelPojo> list = dtoList.parallelStream().map(dto -> convertToAwbExcelPojo(dto, courierId, vendorShortCode, whId))
				.collect(Collectors.toList());
		return list;
	}
	
	private AwbExcelPojo convertToAwbExcelPojo(AwbDTO dto, Long courierId, String vendorShortCode, Long whId) {
		AwbExcelPojo pojo = new AwbExcelPojo();
		pojo.setAwbNumber(dto.getAwbNumber());
		pojo.setCod(dto.isCod());
		pojo.setAwbStatus(dto.getAwbStatusStatus());
		pojo.setChannelName(dto.getChannelName());
		pojo.setVendorShortCode(vendorShortCode);
		pojo.setWhId(whId);

		return pojo;
	}

	@Override
	@Transactional(readOnly=true)
	public AwbDTO isAwbEligibleForDeletion(Long courierId, String awbNumber, Long whId, Boolean cod) {{
		// :: TODO 
	    List<AwbStatus> awbStatusList = Arrays.asList(EnumAwbStatus.Unused.getAsAwbStatus(), EnumAwbStatus.Used.getAsAwbStatus());
	    
	    VendorWHCourierMappingCriteria vendorWHCourierMappingCriteria = new VendorWHCourierMappingCriteria();
	    
	    LongFilter courierIdFilter = new LongFilter();
	    courierIdFilter.equals(courierId);
	    
	    LongFilter whIdFilter = new LongFilter();
	    whIdFilter.equals(whId);
	    
	    vendorWHCourierMappingCriteria.setCourierId(courierIdFilter);
	    vendorWHCourierMappingCriteria.setWarehouse(whIdFilter);
	    List<VendorWHCourierMappingDTO> vendorWHCourierMappingDTOList = vendorWHCourierMappingQueryService.findByCriteria(vendorWHCourierMappingCriteria);
	    
	    List<Long> vendorWHCourierMappingIds = new ArrayList<Long>();
	    
	    if(CollectionUtils.isNotEmpty(vendorWHCourierMappingDTOList))
	    {
	    	vendorWHCourierMappingDTOList.forEach(dto-> vendorWHCourierMappingIds.add(dto.getId()));
	    }
	    
	    AwbCriteria awbCriteria = new AwbCriteria();
	    LongFilter vendorWHCourierMappingIdFilter = new LongFilter();
	    vendorWHCourierMappingIdFilter.setIn(vendorWHCourierMappingIds);
	    
	    StringFilter awbNumberFilter = new StringFilter();
	    awbNumberFilter.setEquals(awbNumber);
	    
	    BooleanFilter codFilter = new BooleanFilter();
	    codFilter.equals(cod);
	    
	    LongFilter awbStatusIdFilter = new LongFilter();
	    awbStatusIdFilter.setIn(Arrays.asList(1L,2L));
	    
	    awbCriteria.setVendorWHCourierMappingId(vendorWHCourierMappingIdFilter);
	    awbCriteria.setAwbStatusId(awbStatusIdFilter);
	    awbCriteria.setAwbNumber(awbNumberFilter);
	    awbCriteria.setCod(codFilter);
	    AwbDTO awbFromDb = awbQueryService.findByCriteria(awbCriteria).get(0);
	    if (awbFromDb != null) {
	    	// ::TODO 
	     //String shipmentQuery = " select  s.awb from Shipment s where s.awb.id = :awbId";
	      //List<Awb> awbList = getCurrentSession().createQuery(shipmentQuery).setParameter("awbId", awbFromDb.getId()).list();
	      //if (awbList == null || awbList.size() == 0) {
	        return awbFromDb;
	      //}
	    }
	    return null;
	  }}

}
