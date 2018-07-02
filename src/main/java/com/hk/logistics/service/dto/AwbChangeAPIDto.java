package com.hk.logistics.service.dto;

public class AwbChangeAPIDto {

	String courierShortCode;
	String newAwbNumber;
	String oldAwbNumber;
	String channel;
	String store;
	String vendorCode;
	String warehouseId;
	boolean cod;
	
	public String getCourierShortCode() {
		return courierShortCode;
	}
	public void setCourierShortCode(String courierShortCode) {
		this.courierShortCode = courierShortCode;
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
	public boolean isCod() {
		return cod;
	}
	public void setCod(boolean cod) {
		this.cod = cod;
	}
}
