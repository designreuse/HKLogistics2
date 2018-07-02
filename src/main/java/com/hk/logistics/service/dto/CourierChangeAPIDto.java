package com.hk.logistics.service.dto;

public class CourierChangeAPIDto {
	
	String courierShortCode;
	String channel;
	String warehouseId;
	String weight;
	String awbNumber;
	String store;
	boolean cod;
	
	public String getCourierShortCode() {
		return courierShortCode;
	}
	public void setCourierShortCode(String courierShortCode) {
		this.courierShortCode = courierShortCode;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getAwbNumber() {
		return awbNumber;
	}
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public boolean isCod() {
		return cod;
	}
	public void setCod(boolean cod) {
		this.cod = cod;
	}

}
