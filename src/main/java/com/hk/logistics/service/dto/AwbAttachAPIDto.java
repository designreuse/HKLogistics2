package com.hk.logistics.service.dto;

public class AwbAttachAPIDto {

	String destinationPincode;
	boolean cod; 
	String fulfillmentCentreCode;
	Double amount;
	Double weight;
	boolean ground;
	boolean isMarketPlaced;
	boolean cardOnDelivery;
	String channel;
	String vendorShortCode;
	String productVariantId;
	String store;
	
	public String getDestinationPincode() {
		return destinationPincode;
	}
	public void setDestinationPincode(String destinationPincode) {
		this.destinationPincode = destinationPincode;
	}
	public boolean isCod() {
		return cod;
	}
	public void setCod(boolean cod) {
		this.cod = cod;
	}
	public String getFulfillmentCentreCode() {
		return fulfillmentCentreCode;
	}
	public void setFulfillmentCentreCode(String fulfillmentCentreCode) {
		this.fulfillmentCentreCode = fulfillmentCentreCode;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public boolean isGround() {
		return ground;
	}
	public void setGround(boolean ground) {
		this.ground = ground;
	}
	public boolean isMarketPlaced() {
		return isMarketPlaced;
	}
	public void setMarketPlaced(boolean isMarketPlaced) {
		this.isMarketPlaced = isMarketPlaced;
	}
	public boolean isCardOnDelivery() {
		return cardOnDelivery;
	}
	public void setCardOnDelivery(boolean cardOnDelivery) {
		this.cardOnDelivery = cardOnDelivery;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getVendorShortCode() {
		return vendorShortCode;
	}
	public void setVendorShortCode(String vendorShortCode) {
		this.vendorShortCode = vendorShortCode;
	}
	public String getProductVariantId() {
		return productVariantId;
	}
	public void setProductVariantId(String productVariantId) {
		this.productVariantId = productVariantId;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	
}
