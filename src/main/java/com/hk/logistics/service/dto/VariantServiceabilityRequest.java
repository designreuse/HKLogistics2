package com.hk.logistics.service.dto;

import java.util.List;

public class VariantServiceabilityRequest {

	StoreVariantAPIObj svObj;
	String channel;
	String destinationPincode;
	String vendorCode;
	List<Long> warehouseList;
	boolean groundShipped ;
	boolean hkFulfilled;
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
		return groundShipped;
	}
	public void setGroundShipped(boolean groundShipped) {
		this.groundShipped = groundShipped;
	}
	public boolean isHkFulfilled() {
		return hkFulfilled;
	}
	public void setHkFulfilled(boolean hkFulfilled) {
		this.hkFulfilled = hkFulfilled;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
}
