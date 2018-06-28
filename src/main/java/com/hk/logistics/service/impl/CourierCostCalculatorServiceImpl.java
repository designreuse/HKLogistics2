package com.hk.logistics.service.impl;

import com.hk.logistics.constants.EnumAwbStatus;
import com.hk.logistics.domain.*;
import com.hk.logistics.enums.EnumChannel;
import com.hk.logistics.repository.*;
import com.hk.logistics.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;

@Service
public class CourierCostCalculatorServiceImpl implements CourierCostCalculatorService {

	private static Logger logger = LoggerFactory.getLogger(CourierCostCalculatorServiceImpl.class);

	@Autowired
    PincodeCourierService pincodeCourierService;
	@Autowired
    PincodeRepository pincodeRepository;
	@Autowired
    SourceDestinationMappingRepository sourceDestinationMappingRepository;
	@Autowired
    CourierChannelRepository courierChannelRepository;
	@Autowired
    PincodeCourierMappingRepository pincodeCourierMappingRepository;
	@Autowired
    PincodeRegionZoneRepository pincodeRegionZoneRepository;
	@Autowired
    CourierPricingEngineRepository courierPricingEngineRepository;
	@Autowired
    ShipmentPricingEngine shipmentPricingEngine;
	@Autowired
    AwbService awbService;
	@Autowired
    VendorWHCourierMappingRepository vendorWHCourierMappingRepository;
	@Autowired
    VendorService vendorService;
	@Autowired
    WarehouseService warehouseService;
	@Autowired
    ChannelRepository channelRepository;
	@Autowired
    AwbRepository awbRepository;
	@Autowired
    VariantService variantService;

	@Override
	public List<Courier> getBestAvailableCourierList(String destinationPincode, String sourcePincode, boolean cod, Long srcWarehouse, Double amount, Double weight,
                                                     boolean ground, boolean cardOnDelivery, String channel, String vendor, String productVariantId, String store) {

		TreeMap<Courier, Long> courierCostingMap = getCourierCostingMap(destinationPincode,sourcePincode, cod, srcWarehouse, amount, weight, ground, null, true, cardOnDelivery, channel,vendor, productVariantId,store);

		Map<Courier, Long> sortedMap = courierCostingMap.descendingMap();

		List<Courier> cheapestCourierList = new ArrayList<Courier>();

		for (Entry<Courier, Long> entry : sortedMap.entrySet()) {// :TODO Get reviewed
			/*if (EnumCourier.Speedpost.getId().equals(entry.getKey().getId())) {
				if (courierCostingMap.size() <= 1) {
					cheapestCourierList.add(entry.getKey());
				}
			} else {*/
			cheapestCourierList.add(entry.getKey());
			/*}*/
		}

		return cheapestCourierList;
	}


	public TreeMap<Courier, Long> getCourierCostingMap(String destinationPincode, String sourcePincode, boolean cod, Long srcWarehouse, Double amount,
                                                       Double weight, boolean ground, Date shipmentDate, boolean onlyCheapestCourierApplicable, boolean cardOnDelivery, String channel, String vendor, String productVariantId, String store) {
		Pincode pincodeObj = pincodeRepository.findByPincode(destinationPincode);
		if(org.apache.commons.lang3.StringUtils.isEmpty(sourcePincode) && srcWarehouse!=null){
			sourcePincode=warehouseService.getPincodeByWarehouse(srcWarehouse);
		}
		List<String> sourcePincodes=new ArrayList<>(Arrays.asList(sourcePincode));
		List<String> finalsourcePincodesList=new ArrayList<String>();
		for(String sourcePincode1:sourcePincodes){
			Boolean checkSourceServiceability=variantService.checkIfProductIsServiceableAtSourcePincode(sourcePincode1,productVariantId);
			if(checkSourceServiceability){
				finalsourcePincodesList.add(sourcePincode);
			}
		}
		List<SourceDestinationMapping> sourceDestinationMappings=sourceDestinationMappingRepository.findBySourcePincodeInAndDestinationPincode(finalsourcePincodesList, destinationPincode);
		List<Long> warehouses=new ArrayList<>(Arrays.asList(srcWarehouse));
		List<PincodeCourierMapping> pincodeCourierMappings = pincodeCourierService.getPincodeCourierMappingList(warehouses, channel, sourceDestinationMappings, vendor, null, null, null);
		List<Courier> courierList=new ArrayList<Courier>();
		for(PincodeCourierMapping pincodeCourierMapping:pincodeCourierMappings){
			Courier courier=pincodeCourierMapping.getVendorWHCourierMapping().getCourierChannel().getCourier();
			if(channel.equals(EnumChannel.MP.getName())){
				if(courier.isVendorShipping()){
					courierList.add(courier);
				}
			}
			else{
				courierList.add(courier);
			}
		}

		return getCourierCostingMap(pincodeObj,courierList, destinationPincode, cod, srcWarehouse, amount, weight, ground, shipmentDate);
	}

	private TreeMap<Courier, Long> getCourierCostingMap(Pincode pincodeObj, List<Courier> applicableCourierList, String pincode, boolean cod, Long srcWarehouse, Double amount,
                                                        Double weight, boolean ground, Date shipmentDate) {
		Double totalCost = 0D;

		if (pincodeObj == null || applicableCourierList == null || applicableCourierList.isEmpty()) {
			logger.error("Could not fetch applicable couriers while making courier costing map for pincode " + pincode
					+ "cod " + cod + " ground " + ground);
			return new TreeMap<Courier, Long>();
		}
		String sourcePincode=warehouseService.getPincodeByWarehouse(srcWarehouse);
		SourceDestinationMapping sourceDestinationMapping=sourceDestinationMappingRepository.findBySourcePincodeAndDestinationPincode(sourcePincode,pincode);
		Set<CourierGroup> courierGroups=new HashSet<>();
		for(Courier courier:applicableCourierList){
			courierGroups.add(courier.getCourierGroup());
		}
		List<PincodeRegionZone> sortedApplicableZoneList =
				pincodeRegionZoneRepository.findBySourceDestinationMappingAndCourierGroupIn(sourceDestinationMapping,courierGroups);
		Map<Courier, Long> courierCostingMap = new HashMap<Courier, Long>();
		for (PincodeRegionZone pincodeRegionZone : sortedApplicableZoneList) {
			for (Courier courier : applicableCourierList) {
				CourierPricingEngine courierPricingInfo = courierPricingEngineRepository.findByCourierAndRegionTypeAndValidUpto(courier,
						pincodeRegionZone.getRegionType(), shipmentDate);
				if (courierPricingInfo == null) {
					continue;
				}
				totalCost = shipmentPricingEngine.calculateShipmentCost(courierPricingInfo, weight);
				if (cod) {
					totalCost += shipmentPricingEngine.calculateReconciliationCost(courierPricingInfo, amount, cod);
				}
				logger.debug("courier " + courier.getName() + "totalCost " + totalCost);
				courierCostingMap.put(courier, Math.round(totalCost));
			}
		}
		TreeMap<Courier, Long> sortedCourierCostingTreeMap = new TreeMap();
		List<Entry<Courier,Long>> list=new ArrayList<Entry<Courier,Long>>(courierCostingMap.entrySet());
		com.hk.logistics.comparator.Comparator comparator=new com.hk.logistics.comparator.Comparator();
		Collections.sort(list,comparator);
		Map<Courier, Long> sortedMap=new HashMap<>(list.size());
		for(Entry<Courier, Long> entry:list){
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		sortedCourierCostingTreeMap.putAll(courierCostingMap);

		return sortedCourierCostingTreeMap;
	}

	@Override
	public Map<Courier, Awb> getCourierAwbMap(String destinationPincode, String sourcePincode, Long warehouse, boolean groundShipped, boolean cod, Double amount, Double weight,
                                              String vendorCode, Date orderPlacedDate, String channel, String variantId, Long storeId) {
		Map<Courier, Awb> courierAwbMap = new HashMap<Courier, Awb>();

		Boolean checkSourceServiceability=variantService.checkIfProductIsServiceableAtSourcePincode(sourcePincode,variantId);
		if(!checkSourceServiceability){
			return null;
		}
		List<Courier> couriers=getBestAvailableCourierList( destinationPincode, sourcePincode, cod, warehouse, amount, weight, groundShipped, false, channel,vendorCode, null, null);
		if (couriers == null || couriers.isEmpty()){
			return courierAwbMap;
		}
		logger.info("*************** Best Available Couriers ************************** for vendor = " + vendorCode + " , pincode = " + sourcePincode);
		StringBuilder log = new StringBuilder("Best Available Couriers = ");
		if (couriers != null && !couriers.isEmpty()) {
			for (Courier c : couriers) {
				log.append(c != null ? c.getName() + ", " : "");
			}
		}
		logger.info(log.toString());
		String store=storeId.toString();
		Channel channel1=channelRepository.findByNameAndStore(channel, store);

		for (Courier courier : couriers) {
			if (channel.equals(EnumChannel.MP.getName())) {
				if (courier.isVendorShipping()) {
					//courierAwbMap.put(courier, null); //TODO Review
					//break;
				}
			}
			String vendor= VendorService.vendorShortCodes.get(vendorCode)!=null?vendorCode:null;
			CourierChannel courierChannel=courierChannelRepository.findByCourierAndChannel(courier,channel1);
			VendorWHCourierMapping vendorWHCourierMapping=vendorWHCourierMappingRepository.findByVendorAndCourierChannelAndActive(vendor, courierChannel, true);
			Awb awb = awbService.getAvailableAwbByVendorWHCourierMappingAndCodAndAwbStatus(vendorWHCourierMapping, cod, EnumAwbStatus.Unused.getAsAwbStatus());
			if (awb != null) {
				awb.setAwbStatus(EnumAwbStatus.Used.getAsAwbStatus());
				courierAwbMap.put(courier, awb);
				break;
			}
		}
		return courierAwbMap;
	}

}
