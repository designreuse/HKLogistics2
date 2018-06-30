package com.hk.logistics.service.impl;

import com.hk.logistics.domain.*;
import com.hk.logistics.repository.*;
import com.hk.logistics.service.PincodeCourierService;
import com.hk.logistics.service.VariantService;
import com.hk.logistics.service.VendorService;
import com.hk.logistics.service.WarehouseService;
import com.hk.logistics.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VariantServiceImpl implements VariantService {


	@Autowired
	PincodeRepository pincodeRepository;
	@Autowired
	VendorService vendorService;
	@Autowired
	CourierChannelRepository courierChannelRepository;
	@Autowired
	VendorWHCourierMappingRepository vendorWhCourierMappingRepository;
	@Autowired
	SourceDestinationMappingRepository sourceDestinationMappingRepository;
	@Autowired
	PincodeCourierMappingRepository pincodeCourierMappingRepository;
	@Autowired
	PincodeCourierService pincodeCourierService;
	@Autowired
	WarehouseService warehouseService;
	@Autowired
	ChannelRepository channelRepository;
	@Autowired
	ProductVariantRepository productVariantRepository;

	public static volatile Map<String, ProductVariantDTO> productVariantMap=new HashMap<>();

	@Override
	public Boolean checkIfProductIsServiceableAtSourcePincode(String pincode,String productVariantId){
		ProductVariant productVariant=productVariantRepository.findByVariantIdAndPincode(productVariantId, pincode);
		if(productVariant!=null && !productVariant.isServiceable()){
			return false;
		}
		return true;
	}

	@Override
	public PincodeDeliveryInfoResponse getVariantDeliveryInfoByPincode(PincodeDeliveryInfoRequest pincodeDeliveryInfoRequest) {

		PincodeDeliveryInfoResponse pincodeDeliveryInfoResponse=new PincodeDeliveryInfoResponse(StoreConstants.DEFAULT_STORE_ID);
		validatePincodeDeliverInfoRequest(pincodeDeliveryInfoRequest, pincodeDeliveryInfoResponse);
		if(pincodeDeliveryInfoResponse.isException()){
			return pincodeDeliveryInfoResponse;
		}
		Pincode destinationPincode=pincodeRepository.findByPincode(pincodeDeliveryInfoRequest.getDestinationPincode());
		validateDestinationPincode(pincodeDeliveryInfoResponse, destinationPincode);
		if(pincodeDeliveryInfoResponse.isException()){
			return pincodeDeliveryInfoResponse;
		}

		ServiceabilityApiDTO svapiObj=pincodeDeliveryInfoRequest.getSvApiObj();
		String vendor=svapiObj.getVendorShortCode();
		List<String> finalsourcePincodesList=new ArrayList<String>();
		List<Long> warehouses=new ArrayList<>();
		if (destinationPincode != null && (vendor!=null || (svapiObj.getFulfillmentCentreCodes() != null && svapiObj.getFulfillmentCentreCodes().size() > 0))) {
			String vendorPincode= VendorService.vendorShortCodes.get(vendor);
			if(svapiObj.isHkFulfilled()){
				for(String locationCode:svapiObj.getFulfillmentCentreCodes()){
					WarehouseDTO warehouseDTO=warehouseService.getWarehouseDTOByFulfillmentCentreCode(locationCode);
					if(warehouseDTO!=null){
						if (svapiObj.getLocationCodes().contains(warehouseDTO.getId())) {
							warehouses.add(Long.parseLong(locationCode));
						}
						String pincode=warehouseDTO.getPincode();
						Boolean checkSourceServiceability=checkIfProductIsServiceableAtSourcePincode(warehouseDTO.getPincode(),svapiObj.getProductVariantId());
						if(checkSourceServiceability){
							finalsourcePincodesList.add(pincode);
						}
					}
				}
			}
			else{
				finalsourcePincodesList.add(vendorPincode);
			}
		}
		List<SourceDestinationMapping> sourceDestinationMapping=sourceDestinationMappingRepository.findBySourcePincodeInAndDestinationPincode(finalsourcePincodesList, destinationPincode.getPincode());

		String store=pincodeDeliveryInfoRequest.getStoreId().toString();
		List<ShipmentServiceType> shipmentServiceTypes=pincodeCourierService.getShipmentServiceTypes(svapiObj.isGroundShipped(),false,false,false);
		Integer estimatedDeliveryDays=pincodeCourierService.getEstimatedDeliveryDaysInfo(pincodeDeliveryInfoResponse, sourceDestinationMapping,
				vendor,warehouses,svapiObj.getCourierChannel(), store, svapiObj.isHkFulfilled());

		if(estimatedDeliveryDays==null){
			pincodeDeliveryInfoResponse.addMessage(MessageConstants.COURIER_SERVICE_NOT_AVAILABLE);
			pincodeDeliveryInfoResponse.setException(true);
		}
		shipmentServiceTypes=null;
		shipmentServiceTypes=pincodeCourierService.getShipmentServiceTypes(svapiObj.isGroundShipped(),true,false,false);//To check Cod
		List<PincodeCourierMapping> pincodeCourierMappings=pincodeCourierService.getPincodeCourierMappingList(warehouses,svapiObj.getCourierChannel(),
				sourceDestinationMapping, vendor,store,
				shipmentServiceTypes, svapiObj.isHkFulfilled());
		if(pincodeCourierMappings!=null){
			pincodeDeliveryInfoResponse.setEstmDeliveryDays(estimatedDeliveryDays);
			pincodeDeliveryInfoResponse.setCodAllowed(true);
		}
		if(svapiObj.isVendorShipping() || svapiObj.isVendorShippingCod()) {
			if (!svapiObj.isVendorShippingCod() ) {
				pincodeDeliveryInfoResponse.setCodAllowed(false);
			}
		}
		return null;
	}


	public PincodeDeliveryInfoResponse validateDestinationPincode(PincodeDeliveryInfoResponse pincodeDeliveryInfoResponse,
			Pincode destinationPincode) {
		if(destinationPincode==null){
			pincodeDeliveryInfoResponse.addMessage(MessageConstants.SERVICE_NOT_AVAILABLE_ON_PINCODE);
			pincodeDeliveryInfoResponse.setException(true);
		}
		return pincodeDeliveryInfoResponse;
	}

	public PincodeDeliveryInfoResponse validatePincodeDeliverInfoRequest(PincodeDeliveryInfoRequest pincodeDeliveryInfoRequest, PincodeDeliveryInfoResponse pincodeDeliveryInfoResponse){
		if(pincodeDeliveryInfoRequest==null || pincodeDeliveryInfoRequest.getStoreId()==null ){
			pincodeDeliveryInfoResponse.addMessage(MessageConstants.REQ_PARAMETERS_INVALID);
			pincodeDeliveryInfoResponse.setException(true);
			return pincodeDeliveryInfoResponse;
		}
		//TODO : add store entity null check
		ServiceabilityApiDTO serviceabilityApiDTO=pincodeDeliveryInfoRequest.getSvApiObj();
		if(serviceabilityApiDTO==null || serviceabilityApiDTO.getProductVariantId()==null || pincodeDeliveryInfoRequest.getDestinationPincode()==null){
			pincodeDeliveryInfoResponse.addMessage(MessageConstants.REQ_PARAMETERS_INVALID);
			pincodeDeliveryInfoResponse.setException(true);
			return pincodeDeliveryInfoResponse;
		}
		if(serviceabilityApiDTO.getCourierChannel()==null){
			pincodeDeliveryInfoResponse.addMessage(MessageConstants.COURIER_CHANNEL_NOT_AVAILABLE);
			pincodeDeliveryInfoResponse.setException(true);
			return pincodeDeliveryInfoResponse;
		}

		return pincodeDeliveryInfoResponse;

	}

	@Override
	public StoreVariantAPIObj getVariantServiceabilityDetails(VariantServiceabilityRequest variantServiceabilityRequest) {

		StoreVariantAPIObj svObj=variantServiceabilityRequest.getSvObj();
		String channel=variantServiceabilityRequest.getChannel();
		Pincode destinationPincode=pincodeRepository.findByPincode(variantServiceabilityRequest.getDestinationPincode());
		String vendor=variantServiceabilityRequest.getVendorCode();
		List<Long> warehouseList=variantServiceabilityRequest.getWarehouseList();
		boolean isGroundShipped =variantServiceabilityRequest.isGroundShipped();
		List<String> finalsourcePincodesList=new ArrayList<>();
		if (destinationPincode != null && (vendor!=null || (warehouseList != null && warehouseList.size() > 0))) {
			String vendorPincode= VendorService.vendorShortCodes.get(svObj.getVendorShortCode());
			boolean shippable = false;
			if(variantServiceabilityRequest.isHkFulfilled()){
				for(Long locationCode:warehouseList){
					WarehouseDTO warehouseDTO=WarehouseService.warehouseMap.get(locationCode); 
					if(warehouseDTO!=null){
						String pincode=warehouseDTO.getPincode();
						Boolean checkSourceServiceability=checkIfProductIsServiceableAtSourcePincode(warehouseDTO.getPincode(),
								svObj.getVariantId());
						if(checkSourceServiceability){
							finalsourcePincodesList.add(pincode);
						}
					}
				}
			}
			else{
				finalsourcePincodesList.add(vendorPincode);
			}
			List<SourceDestinationMapping> sourceDestinationMapping=sourceDestinationMappingRepository.findBySourcePincodeInAndDestinationPincode(finalsourcePincodesList, destinationPincode.getPincode());


			List<ShipmentServiceType> shipmentServiceTypes=pincodeCourierService.getShipmentServiceTypes(isGroundShipped,false,false,false);//To check Cod
			List<PincodeCourierMapping> pincodeCourierMappings=pincodeCourierService.getPincodeCourierMappingList(warehouseList,variantServiceabilityRequest.getChannel(),sourceDestinationMapping, vendor,
					variantServiceabilityRequest.getStore(),shipmentServiceTypes, variantServiceabilityRequest.isHkFulfilled());
			if(pincodeCourierMappings!=null && pincodeCourierMappings.size()>0){
				shippable=true;
			}
			//checking ship-ability
			svObj.setShippable(shippable);
			// Checking cod
			boolean isCodAllowed = false;
			shipmentServiceTypes=null;
			shipmentServiceTypes=pincodeCourierService.getShipmentServiceTypes(isGroundShipped,true,false,false);
			List<PincodeCourierMapping> pincodeCourierMappingsForCOD=pincodeCourierService.getPincodeCourierMappingList(warehouseList,variantServiceabilityRequest.getChannel(),sourceDestinationMapping, vendor,
					variantServiceabilityRequest.getStore(),shipmentServiceTypes, variantServiceabilityRequest.isHkFulfilled());
			if(pincodeCourierMappingsForCOD!=null && pincodeCourierMappingsForCOD.size()>0){
				isCodAllowed=true;
			}
			if(svObj.isVendorShipping()) {
				if (!svObj.isVendorShippingCod()) {
					isCodAllowed = false;
				}
			}
			svObj.setCodAllowed(isCodAllowed);

			// Checking cardOnDelivery
			if(variantServiceabilityRequest.isHkFulfilled()) {
				//serviceTypeList = pincodeCourierService.getShipmentServiceType(isGroundShipped, false, false, true);
				shipmentServiceTypes=null;
				shipmentServiceTypes=pincodeCourierService.getShipmentServiceTypes(isGroundShipped,false,false,true);
				List<PincodeCourierMapping> pincodeCourierMappingsForCardOnDelivery=pincodeCourierService.getPincodeCourierMappingList(warehouseList,variantServiceabilityRequest.getChannel(),sourceDestinationMapping, vendor,
						variantServiceabilityRequest.getStore(),shipmentServiceTypes, variantServiceabilityRequest.isHkFulfilled());
				if(pincodeCourierMappingsForCardOnDelivery!=null && pincodeCourierMappingsForCardOnDelivery.size()!=0)
					svObj.setCardOnDeliveryAllowed(true);
			}
		}
		return svObj;
	}

}
