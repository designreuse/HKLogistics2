package com.hk.logistics.service.dto;

import java.util.List;

public class VariantServiceabilityRequest {

	StoreVariantAPIObj svObj;
	String channel;
	String destinationPincode;
	String vendorCode;
	List<Long> warehouseList;
	boolean isGroundShipped ;
	boolean isHkFulfilled;
	String store;

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
	public String getDestinationPincode() {
		return destinationPincode;
	}
	public void setDestinationPincode(String destinationPincode) {
		this.destinationPincode = destinationPincode;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public List<Long> getWarehouseList() {
		return warehouseList;
	}
	public void setWarehouseList(List<Long> warehouseList) {
		this.warehouseList = warehouseList;
	}
	public boolean isGroundShipped() {
		return isGroundShipped;
	}
	public void setGroundShipped(boolean isGroundShipped) {
		this.isGroundShipped = isGroundShipped;
	}
	public boolean isHkFulfilled() {
		return isHkFulfilled;
	}
	public void setHkFulfilled(boolean isHkFulfilled) {
		this.isHkFulfilled = isHkFulfilled;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
}
