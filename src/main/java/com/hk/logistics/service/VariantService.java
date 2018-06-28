package com.hk.logistics.service;

import com.hk.logistics.service.dto.PincodeDeliveryInfoRequest;
import com.hk.logistics.service.dto.PincodeDeliveryInfoResponse;
import com.hk.logistics.service.dto.StoreVariantAPIObj;
import com.hk.logistics.service.dto.VariantServiceabilityRequest;

public interface VariantService {

	PincodeDeliveryInfoResponse getVariantDeliveryInfoByPincode(PincodeDeliveryInfoRequest pincodeDeliveryInfoRequest);

	StoreVariantAPIObj  getVariantServiceabilityDetails(VariantServiceabilityRequest variantServiceabilityRequest);

	Boolean checkIfProductIsServiceableAtSourcePincode(String pincode, String productVariantId);
	
}
