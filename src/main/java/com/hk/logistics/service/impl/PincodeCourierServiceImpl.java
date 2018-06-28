package com.hk.logistics.service.impl;

import com.hk.logistics.criteria.SearchCriteria;
import com.hk.logistics.domain.*;
import com.hk.logistics.enums.EnumShipmentServiceType;
import com.hk.logistics.repository.*;
import com.hk.logistics.service.PincodeCourierService;
import com.hk.logistics.service.WarehouseService;
import com.hk.logistics.service.dto.PincodeDeliveryInfoResponse;
import com.hk.logistics.service.dto.ReturnServiceabilityRequest;
import com.hk.logistics.service.dto.WarehouseDTO;
import com.hk.logistics.specification.PincodeCourierSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PincodeCourierServiceImpl implements PincodeCourierService {

	@Autowired
    CourierChannelRepository courierChannelRepository;
	@Autowired
    VendorWHCourierMappingRepository vendorWHCourierMappingRepository;
	@Autowired
    PincodeCourierMappingRepository pincodeCourierMappingRepository;
	@Autowired
    PincodeCourierService pincodeCourierService;
	@Autowired
    ChannelRepository channelRepository;
	@Autowired
    SourceDestinationMappingRepository sourceDestinationMappingRepository;
	@Autowired
    CourierRepository courierRepository;

	@Override
	public Integer getEstimatedDeliveryDays(List<PincodeCourierMapping> pincodeCourierMappings) {
		Double maxDeliveryDays=0.0D;
		for(PincodeCourierMapping pincodeCourierMapping:pincodeCourierMappings){
			Double estimatedDeliveryDays = pincodeCourierMapping.getEstimatedDeliveryDays();
			maxDeliveryDays+=estimatedDeliveryDays;
		}
		if(pincodeCourierMappings.size()>0){
			return (maxDeliveryDays.intValue()/pincodeCourierMappings.size());
		}
		return null;
	}

	@Override
	public Integer getEstimatedDeliveryDaysInfo(PincodeDeliveryInfoResponse pincodeDeliveryInfoResponse, List<SourceDestinationMapping> sourceDestinationMapping, String vendor,
                                                List<Long> warehouses, String channel, String store, Boolean isHkFulfilled) {
		List<PincodeCourierMapping> pincodeCourierMappings=getPincodeCourierMappingList(warehouses,channel,sourceDestinationMapping,
				vendor, null, null, null);
		if(pincodeCourierMappings!=null && pincodeCourierMappings.size()>0){
			Integer estimatedDeliveryDays=pincodeCourierService.getEstimatedDeliveryDays(pincodeCourierMappings);
			return estimatedDeliveryDays;
		}
		return null;
	}

	@Override
	public List<PincodeCourierMapping> getPincodeCourierMappingList(List<Long> warehouses, String channel,
                                                                    List<SourceDestinationMapping> sourceDestinationMapping, String vendor, String store, List<ShipmentServiceType> shipmentServiceTypes, Boolean isHkFulfilled){
		if(vendor!=null){
			Channel channel1=channelRepository.findByNameAndStore(channel, store);
			List<CourierChannel> courierChannels=courierChannelRepository.findByChannel(channel1);
			if(courierChannels!=null){
				List<VendorWHCourierMapping> vendorWHCourierMappings=getVendorWHMappings(warehouses,vendor,courierChannels, isHkFulfilled);
				if(vendorWHCourierMappings!=null){
					if(vendorWHCourierMappings!=null){
						List<PincodeCourierMapping> pincodeCourierMappings=getListOfPincodeCourierMappingOnShipmentServiceType(shipmentServiceTypes,sourceDestinationMapping,vendorWHCourierMappings);
						return pincodeCourierMappings;
					}
				}
			}
		}
		return null;
	}

	@Override
	public List<PincodeCourierMapping> getPincodeCourierMappingListOnShipmentServiceTypes(String channel, List<SourceDestinationMapping> sourceDestinationMapping, String vendor, List<ShipmentServiceType> shipmentServiceType, List<Long> warehouses, Boolean isHkFulfilled) {
		if(vendor!=null){
			Channel channel1=channelRepository.findByNameAndStore(channel, null);
			List<CourierChannel> courierChannels=courierChannelRepository.findByChannel(channel1);
			if(courierChannels!=null){
				List<VendorWHCourierMapping> vendorWHCourierMappings=getVendorWHMappings(warehouses,vendor,courierChannels, isHkFulfilled);
				if(vendorWHCourierMappings!=null){
					List<PincodeCourierMapping> pincodeCourierMappings=getListOfPincodeCourierMappingOnShipmentServiceType(shipmentServiceType,sourceDestinationMapping,vendorWHCourierMappings);
					return pincodeCourierMappings;
				}
			}
		}
		return null;
	}

	public List<PincodeCourierMapping> getListOfPincodeCourierMappingOnShipmentServiceType(List<ShipmentServiceType> shipmentServiceType, List<SourceDestinationMapping> sourceDestinationMapping,
                                                                                           List<VendorWHCourierMapping> vendorWHCourierMappings) {

		PincodeCourierSpecification pincodeCourierSpecification1=new PincodeCourierSpecification(new SearchCriteria("sourceDestinationMapping",":",sourceDestinationMapping));
		PincodeCourierSpecification pincodeCourierSpecification2=new PincodeCourierSpecification(new SearchCriteria("vendorWHCourierMapping",":",vendorWHCourierMappings));
		if(shipmentServiceType!=null){
			if(shipmentServiceType.size()==1){
				PincodeCourierSpecification pincodeCourierSpecification3=new PincodeCourierSpecification(new SearchCriteria(shipmentServiceType.get(0).getName(),":",true));
				List<PincodeCourierMapping> pincodeCourierMappings=pincodeCourierMappingRepository.findAll(Specification.where(pincodeCourierSpecification1).and(pincodeCourierSpecification2).
						and(pincodeCourierSpecification3));
				return pincodeCourierMappings;
			}
			else if(shipmentServiceType.size()==3){
				PincodeCourierSpecification pincodeCourierSpecification3=new PincodeCourierSpecification(new SearchCriteria(shipmentServiceType.get(0).getName(),":",true));
				PincodeCourierSpecification pincodeCourierSpecification4=new PincodeCourierSpecification(new SearchCriteria(shipmentServiceType.get(1).getName(),":",true));
				PincodeCourierSpecification pincodeCourierSpecification5=new PincodeCourierSpecification(new SearchCriteria(shipmentServiceType.get(2).getName(),":",true));
				List<PincodeCourierMapping> pincodeCourierMappings=pincodeCourierMappingRepository.findAll(Specification.where(pincodeCourierSpecification1).and(pincodeCourierSpecification2).
						and(pincodeCourierSpecification3).and(pincodeCourierSpecification4).and(pincodeCourierSpecification5));
				return pincodeCourierMappings;
			}
		}
		return null;
	}

	@Override
	public Boolean checkIfReturnServiceabilityAvailable(ReturnServiceabilityRequest returnServiceabilityRequest){
		List<String> sourcePincodes=new ArrayList<>();
		if(returnServiceabilityRequest.isHkFulfilled()){
			for(Long warehouse:returnServiceabilityRequest.getWarehouses()){
				WarehouseDTO warehouseDTO= WarehouseService.warehouseMap.get(warehouse.toString());
				if(warehouseDTO!=null && warehouseDTO.getPincode()!=null){
					sourcePincodes.add(warehouseDTO.getPincode());
				}
			}
		}
		List<ShipmentServiceType> shipmentServiceTypes=pincodeCourierService.getShipmentServiceTypes(returnServiceabilityRequest.isGroundShipped(),false,false,false);
		List<SourceDestinationMapping> sourceDestinationMapping=sourceDestinationMappingRepository.findBySourcePincodeInAndDestinationPincode(sourcePincodes, returnServiceabilityRequest.getDestinationPincode());
		List<PincodeCourierMapping> pincodeCourierMappings=pincodeCourierService.getPincodeCourierMappingList(returnServiceabilityRequest.getWarehouses(),returnServiceabilityRequest.getChannel(),
				sourceDestinationMapping,returnServiceabilityRequest.getVendor(), null, shipmentServiceTypes, returnServiceabilityRequest.isHkFulfilled());
		if(pincodeCourierMappings!=null && pincodeCourierMappings.size()!=0){
			return true;
		}
		return false;
	}

	@Override
	public String getRoutingCode(String destinationPincode, Long warehouse, Courier courier, boolean isGroundShipped, boolean isCod, String channel, String sourcePincode, String vendor, String store) {
		String routingCode = null;
		//ShipmentServiceType shipmentServiceType = getShipmentServiceTypeFromProperties(isGroundShipped, isCod);
		List<Long> warehouseList = null;
		List<Courier> courierList = null;
		if (warehouse != null) {
			warehouseList = new ArrayList<Long>();
			warehouseList.add(warehouse);
		}
		if (courier != null) {
			courierList = new ArrayList<Courier>();
			courierList.add(courier);
		}
		SourceDestinationMapping sourceDestinationMapping=sourceDestinationMappingRepository.findBySourcePincodeAndDestinationPincode(sourcePincode, destinationPincode);
		List<PincodeCourierMapping> pincodeCourierMappings =getPincodeCourierMappingList(warehouseList, channel, new ArrayList<SourceDestinationMapping>(Arrays.asList(sourceDestinationMapping)),vendor, store, null, null);
		PincodeCourierMapping pincodeCourierMapping = pincodeCourierMappings != null && !pincodeCourierMappings.isEmpty() ? pincodeCourierMappings.get(0) : null;

		if (pincodeCourierMapping != null) {
			routingCode = pincodeCourierMapping.getRoutingCode();
		}
		return routingCode;
	}

	@Override
	public List<ShipmentServiceType> getShipmentServiceTypes(boolean isGroundShipped, boolean checkForCod, boolean isReversePickup, boolean checkForCardOnDelivery) {
		if (isGroundShipped) {
			if (checkForCod) {
				return Arrays.asList(EnumShipmentServiceType.Cod_Ground.asShipmentServiceType());
			} else if(checkForCardOnDelivery) {
				return Arrays.asList(EnumShipmentServiceType.CardOnDelivery_Ground.asShipmentServiceType());
			} else if (isReversePickup) {
				return Arrays.asList(EnumShipmentServiceType.ReversePickup_Ground.asShipmentServiceType());
			} else {
				return EnumShipmentServiceType.getGroundShipmentServiceTypes();
			}
		} else {
			if (checkForCod) {
				return Arrays.asList(EnumShipmentServiceType.Cod_Air.asShipmentServiceType());
			} else if(checkForCardOnDelivery) {
				return Arrays.asList(EnumShipmentServiceType.CardOnDelivery_Air.asShipmentServiceType());
			} else if (isReversePickup) {
				return Arrays.asList(EnumShipmentServiceType.ReversePickup_Air.asShipmentServiceType());
			} else {
				return EnumShipmentServiceType.getAirShipmentServiceTypes();
			}
		}
	}

	public List<VendorWHCourierMapping> getVendorWHMappings(List<Long> warehouses, String vendor, List<CourierChannel> courierChannels, Boolean isHkFulfilled){
		List<VendorWHCourierMapping> vendorWHCourierMappings=new ArrayList<VendorWHCourierMapping>();
		if(isHkFulfilled){
			vendorWHCourierMappings=vendorWHCourierMappingRepository.findByWarehouseInAndCourierChannelInAndActive(warehouses, courierChannels, true);
		}
		else{
			vendorWHCourierMappings=vendorWHCourierMappingRepository.findByVendorAndCourierChannelInAndActive(vendor, courierChannels,true);
		}
		return vendorWHCourierMappings;
	}

	@Override
	public List<CourierChannel> getAllCourierListForStoreAndChannel(String storeId, String channelName){
		Channel channel=channelRepository.findByNameAndStore(channelName,storeId);
		List<CourierChannel> courierChannels=courierChannelRepository.findByChannel(channel);
		return courierChannels;
	}
}
