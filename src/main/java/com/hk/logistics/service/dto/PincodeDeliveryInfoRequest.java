package com.hk.logistics.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PincodeDeliveryInfoRequest {

	@JsonProperty(DtoJsonConstants.DESTINATION_PINCODE)
	private String destinationPincode;
	@JsonProperty(DtoJsonConstants.STORE_ID)
	private Long storeId;
	private ServiceabilityApiDTO svApiObj;

	public String getDestinationPincode() {
		return destinationPincode;
	}

	public void setDestinationPincode(String destinationPincode) {
		this.destinationPincode = destinationPincode;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public ServiceabilityApiDTO getSvApiObj() {
		return svApiObj;
	}

	public void setSvApiObj(ServiceabilityApiDTO svApiObj) {
		this.svApiObj = svApiObj;
	}

}
