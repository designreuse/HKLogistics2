package com.hk.logistics.service;

import com.hk.logistics.domain.*;
import com.hk.logistics.enums.EnumCostParamter;
import com.hk.logistics.enums.EnumCourier;
import com.hk.logistics.enums.EnumPaymentMode;
import com.hk.logistics.repository.*;
import com.hk.logistics.service.dto.ShipmentPricingRequest;
import com.hk.logistics.service.dto.ShipmentPricingResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class ShipmentPricingEngine {

	private static Logger logger = LoggerFactory.getLogger(ShipmentPricingEngine.class);
	@Autowired
    CourierRepository courierRepository;
	@Autowired
	WarehouseService warehouseService;
	@Autowired
    PincodeRepository pincodeRepository;
	@Autowired
    PincodeRegionZoneRepository pincodeRegionZoneRepository;
	@Autowired
    SourceDestinationMappingRepository sourceDestinationMappingRepository;
	@Autowired
    CourierPricingEngineRepository courierPricingEngineRepository;
	@Autowired
    ShipmentPricingEngine shipmentPricingEngine;

	public Double calculateShipmentCost(CourierPricingEngine courierPricingEngine, Double weight){
		if (courierPricingEngine == null) {
			return 0D;
		}
		/*String costParamters=courierPricingEngine.getCostParameters();
		Map<String, Double> pricingParamtersMap=new HashMap<>();
		try {
			JSONObject jsonObject=new JSONObject(costParamters);
			Iterator<String> it=jsonObject.keys();
			while(it.hasNext()){
				String key=it.next();
				String value=jsonObject.getString(key);
				pricingParamtersMap.put(key, Double.parseDouble(value));
			}
		} catch (JSONException e) {
			logger.error("error while mapping Json"+e.getMessage());
			e.printStackTrace();
		}
		Double weightLeft=weight;
		Double baseCost=0.0D;
		for(int i=1;i<=pricingParamtersMap.size();i++){
			Double baseweight=pricingParamtersMap.get(EnumCostParamter.BASE_WEIGHT.getName()+i);
			if (weightLeft > 0 ) {
				baseCost += pricingParamtersMap.get(EnumCostParamter.BASE_COST.getName()+i);
				weightLeft-=baseweight;
			}
		}*/
		Double weightLeft=courierPricingEngine.getFirstBaseWt()+courierPricingEngine.getSecondBaseWt()+courierPricingEngine.getThirdBaseWt();
		Double additionalWeight = weight - (weightLeft);
		Double remainder = 0D;
		Double baseCost = courierPricingEngine.getFirstBaseCost();
        if (weight > courierPricingEngine.getFirstBaseWt()) {
            baseCost += courierPricingEngine.getSecondBaseCost();
            if(weight>courierPricingEngine.getFirstBaseWt()+courierPricingEngine.getSecondBaseWt()){
            	baseCost += courierPricingEngine.getThirdBaseCost();
            }
        }
		if (additionalWeight > 0) {
			remainder = additionalWeight % courierPricingEngine.getAdditionalWt();
		}

		int slabs = (int) (additionalWeight / courierPricingEngine.getAdditionalWt());

		if (remainder > 0) slabs = slabs + 1;
		Double additionalCost = additionalWeight > 0D ? slabs * courierPricingEngine.getAdditionalCost() : 0D;
		return (baseCost + additionalCost) * (1 + courierPricingEngine.getFuelSurcharge());
	}

	public Double calculateReconciliationCost(CourierPricingEngine courierPricingEngine, Double amount, Boolean cod) {
		Double reconciliationCharges = 0D;
		if (courierPricingEngine != null) {
			if (cod) {
				reconciliationCharges = amount > courierPricingEngine.getCodCutoffAmount()
						? amount * courierPricingEngine.getVariableCodCharges() : courierPricingEngine.getMinCodCharges();
			} else {
				reconciliationCharges = amount * 0.022;
			}
		}
		return reconciliationCharges;
	}

	public ShipmentPricingResponse getShipmentPricingResponseForBright(ShipmentPricingRequest shipmentPricingRequest) {
		ShipmentPricingResponse shipmentPricingResponse = new ShipmentPricingResponse();

		//input parameters from request
		Double shipmentBoxWeight = shipmentPricingRequest.getShipmentBoxWeight();
		Courier courier = courierRepository.findByShortCode(shipmentPricingRequest.getCourierShortCode());
		Pincode pincodeObj = pincodeRepository.findByPincode(shipmentPricingRequest.getPincode());
		Long srcWarehouse = warehouseService.getWarehouseCodeByFulfillmentCentreCode(shipmentPricingRequest.getWarehouseFcCode());
		Double shippingOrderAmount = shipmentPricingRequest.getShippingOrderAmount();
		Long paymentModeId = shipmentPricingRequest.getPaymentModeId();

		//results
		Double shipmentCost = 0.0D;
		Double reconciliationCost = 0.0D;
		Double packagingCost = 0.0D;

		//calculating shipmentCost
		Double weight = shipmentBoxWeight * 1000;
		// weight remains as physical weight if HK delivery or courier with physical weight restriction
		// if (courier.getOperationsBitset() % EnumCourierOperations.PHYSICAL_WT_PREFERRED.getId() != 0) {
		//}
		/*if (EnumCourier.HK_Delivery.getId().equals(courier.getId())) {
			if (pincodeObj.getNearestHub() != null) {
				HKReachPricingEngine hkReachPricingEngine = courierService.getHkReachPricingEngine(srcWarehouse,
						pincodeObj.getNearestHub(), null);
				if (hkReachPricingEngine != null) {
					shipmentCost = calculateHKReachCost(hkReachPricingEngine, weight, pincodeObj);
				}
			} else {
				shipmentCost = -1.0D;
			}
		} else {*/// TODO check if HKReach Pricing works
		String sourcePincode=warehouseService.getPincodeByWarehouse(srcWarehouse);
		SourceDestinationMapping sourceDestinationMapping=sourceDestinationMappingRepository.findBySourcePincodeAndDestinationPincode(sourcePincode, shipmentPricingRequest.getPincode());
		PincodeRegionZone pincodeRegionZone = pincodeRegionZoneRepository.findBySourceDestinationMappingAndCourierGroup(sourceDestinationMapping,courier.getCourierGroup());
		CourierPricingEngine courierPricingInfo = courierPricingEngineRepository.findByCourierAndRegionTypeAndValidUpto(courier,
				pincodeRegionZone.getRegionType(), null);
		if (courierPricingInfo != null) {
			shipmentCost =shipmentPricingEngine.calculateShipmentCost(courierPricingInfo, weight);
		} else {
			shipmentCost = null;
		}
		//calculating reconciliationCost //TODO logic
		/*if (pincodeObj == null || courier == null || courier.getId().equals(EnumCourier.MIGRATE.getId())) {
			reconciliationCost = null;
		} else {*///TODO check logic
			if (paymentModeId.equals(EnumPaymentMode.ONLINE_PAYMENT.getId())
					|| paymentModeId.equals(EnumPaymentMode.COD_TO_ONLINE.getId())
					|| paymentModeId.equals(EnumPaymentMode.PAY_AT_STORE.getId())) {
				Double reconCost = shippingOrderAmount * 0.02;
				//reconCost *= (1 + EnumTax.VAT_15.getValue());
				reconciliationCost = reconCost;
			} else if (paymentModeId.equals(EnumPaymentMode.COD.getId())) {
				if (EnumCourier.HK_Delivery.getId().equals(courier.getId())) {
					reconciliationCost = 0D;
				} else {
					if (courierPricingInfo == null) {
						reconciliationCost = null;
					} else if (paymentModeId.equals(EnumPaymentMode.COD.getId())) {
						reconciliationCost = calculateReconciliationCost(courierPricingInfo, shippingOrderAmount, true);
					} else {
						reconciliationCost = calculateReconciliationCost(courierPricingInfo, shippingOrderAmount, true);
					}
				}
			} else {
				reconciliationCost = 0D;
			}

		//calculating packagingCost
		//packagingCost = boxSize != null && boxSize.getId() != -1 ? boxSize.getPackagingCost() : 15.5D;
		shipmentPricingResponse.setEstmShipmentCharge(shipmentCost);
		shipmentPricingResponse.setEstmCollectionCharge(reconciliationCost);
		shipmentPricingResponse.setExtraCharge(packagingCost);

		return shipmentPricingResponse;
	}

}
