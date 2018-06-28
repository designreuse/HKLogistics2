package com.hk.logistics.service.dto;

import com.hk.logistics.domain.Pincode;

import java.util.List;

public class VariantServiceabilityAPIDto {

	StoreVariantAPIObj svObj;
	String channel;
	Pincode destinationPincode;
	Pincode sourcePincode;
	String vendorCode;
	List<String> warehouseList;
	boolean isGroundShipped;

	public StoreVariantAPIObj getSvObj() {
		return svObj;
	}
	public void setSvObj(StoreVariantAPIObj svObj) {
		this.svObj = svObj;
	}

	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Pincode getDestinationPincode() {
		return destinationPincode;
	}
	public void setDestinationPincode(Pincode destinationPincode) {
		this.destinationPincode = destinationPincode;
	}
	public Pincode getSourcePincode() {
		return sourcePincode;
	}
	public void setSourcePincode(Pincode sourcePincode) {
		this.sourcePincode = sourcePincode;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public List<String> getWarehouseList() {
		return warehouseList;
	}
	public void setWarehouseList(List<String> warehouseList) {
		this.warehouseList = warehouseList;
	}
	public boolean isGroundShipped() {
		return isGroundShipped;
	}
	public void setGroundShipped(boolean isGroundShipped) {
		this.isGroundShipped = isGroundShipped;
	}


}
