package com.hk.logistics.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class AwbCourierRequest {

	@JsonProperty(DtoJsonConstants.PINCODE)
	private String destinationPincode;
	private String vendor;
	private Double weightOfBatch;
	private boolean groundShipping;
	private boolean cod;
	private Double amount;
	@JsonProperty(DtoJsonConstants.VENDOR_PINCODE)
	private String sourcePincode;
	private String fulfilmentCenterCode;
	private Date orderDate;
	@JsonProperty(DtoJsonConstants.STORE_ID)
	private Long storeId;
	private String channel;// :TODO Added channel
	private String variantId;//TODO add variant Id

	public AwbCourierRequest() {
	}

	/*public AwbCourierRequest(AwbBrightCourierRequest awbBrightCourierRequest) {
    this.destinationPincode = awbBrightCourierRequest.getDestinationPincode();
    this.vendor = awbBrightCourierRequest.getVendor();
    this.weightOfBatch = awbBrightCourierRequest.getWeightOfBatch();
    this.groundShipping = awbBrightCourierRequest.isGroundShipping();
    this.cod = awbBrightCourierRequest.isCod();
    this.amount = awbBrightCourierRequest.getAmount();
    this.sourcePincode = awbBrightCourierRequest.getSourcePincode();
    this.fulfilmentCenterCode = awbBrightCourierRequest.getFulfilmentCenterCode();
  }*/// TODO: BrightCourier

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

	public Double getWeightOfBatch() {
		return weightOfBatch;
	}

	public void setWeightOfBatch(Double weightOfBatch) {
		this.weightOfBatch = weightOfBatch;
	}

	public boolean isGroundShipping() {
		return groundShipping;
	}

	public void setGroundShipping(boolean groundShipping) {
		this.groundShipping = groundShipping;
	}

	public boolean isCod() {
		return cod;
	}

	public void setCod(boolean cod) {
		this.cod = cod;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getSourcePincode() {
		return sourcePincode;
	}

	public void setSourcePincode(String sourcePincode) {
		this.sourcePincode = sourcePincode;
	}

	public String getFulfilmentCenterCode() {
		return fulfilmentCenterCode;
	}

	public void setFulfilmentCenterCode(String fulfilmentCenterCode) {
		this.fulfilmentCenterCode = fulfilmentCenterCode;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getVariantId() {
		return variantId;
	}

	public void setVariantId(String variantId) {
		this.variantId = variantId;
	}

}
