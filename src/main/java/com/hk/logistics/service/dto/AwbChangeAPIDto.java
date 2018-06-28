package com.hk.logistics.service.dto;

public class AwbChangeAPIDto {

	String courierName;
	String newAwbNumber;
	String oldAwbNumber;
	String channel;
	String store;
	String vendorCode;
	String warehouseId;
	
	public String getCourierName() {
		return courierName;
	}
	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}
	public String getNewAwbNumber() {
		return newAwbNumber;
	}
	public void setNewAwbNumber(String newAwbNumber) {
		this.newAwbNumber = newAwbNumber;
	}
	public String getOldAwbNumber() {
		return oldAwbNumber;
	}
	public void setOldAwbNumber(String oldAwbNumber) {
		this.oldAwbNumber = oldAwbNumber;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
}
