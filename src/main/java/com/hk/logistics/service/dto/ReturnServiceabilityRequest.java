package com.hk.logistics.service.dto;

import java.util.List;

public class ReturnServiceabilityRequest {

	List<Long> warehouses;
	String destinationPincode;
	String vendor;
	String channel;
	boolean isGroundShipped;
	boolean isHkFulfilled;
	
	public List<Long> getWarehouses() {
		return warehouses;
	}
	public void setWarehouses(List<Long> warehouses) {
		this.warehouses = warehouses;
	}
	public String getDestinationPincode() {
		return destinationPincode;
	}
	public void setDestinationPincode(String destinationPincode) {
		this.destinationPincode = destinationPincode;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
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
	
}
