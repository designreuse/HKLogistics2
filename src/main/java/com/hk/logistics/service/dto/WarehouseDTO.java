package com.hk.logistics.service.dto;

public class WarehouseDTO {

	private Long id;
	private String pincode;
	private String fulfilmentCenterCode;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getFulfilmentCenterCode() {
		return fulfilmentCenterCode;
	}
	public void setFulfilmentCenterCode(String fulfilmentCenterCode) {
		this.fulfilmentCenterCode = fulfilmentCenterCode;
	}
	
	
}
