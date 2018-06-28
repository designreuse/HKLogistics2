package com.hk.logistics.service.dto;

public class StoreVariantAPIObj {

	private String  variantId;
	private Double  mrp;
	private Double  costPrice;
	private String  locationCode;
	private Long    qty;
	private boolean oos;
	private boolean shippable = false;
	private boolean codAllowed = false;
	private Long    dispatchDays;
	private Long    deliveryDays;
	private String vendorShortCode;
	private boolean vendorShipping;
	private boolean vendorShippingCod;
	private boolean cardOnDeliveryAllowed = false;
	private String storeId;//TODO give storeId from api

	public StoreVariantAPIObj() {
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getVariantId() {
		return variantId;
	}

	public void setVariantId(String variantId) {
		this.variantId = variantId;
	}

	public Double getMrp() {
		return mrp;
	}

	public void setMrp(Double mrp) {
		this.mrp = mrp;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public boolean isOos() {
		return oos;
	}

	public void setOos(boolean oos) {
		this.oos = oos;
	}

	public boolean isShippable() {
		return shippable;
	}

	public void setShippable(boolean shippable) {
		this.shippable = shippable;
	}

	public boolean isCodAllowed() {
		return codAllowed;
	}

	public void setCodAllowed(boolean codAllowed) {
		this.codAllowed = codAllowed;
	}

	public Long getDispatchDays() {
		return dispatchDays;
	}

	public void setDispatchDays(Long dispatchDays) {
		this.dispatchDays = dispatchDays;
	}

	public Long getDeliveryDays() {
		return deliveryDays;
	}

	public void setDeliveryDays(Long deliveryDays) {
		this.deliveryDays = deliveryDays;
	}

	public String getVendorShortCode() {
		return vendorShortCode;
	}

	public void setVendorShortCode(String vendorShortCode) {
		this.vendorShortCode = vendorShortCode;
	}

	public boolean isVendorShipping() {
		return vendorShipping;
	}

	public void setVendorShipping(boolean vendorShipping) {
		this.vendorShipping = vendorShipping;
	}

	public boolean isVendorShippingCod() {
		return vendorShippingCod;
	}

	public void setVendorShippingCod(boolean vendorShippingCod) {
		this.vendorShippingCod = vendorShippingCod;
	}

	public boolean isCardOnDeliveryAllowed() {
		return cardOnDeliveryAllowed;
	}

	public void setCardOnDeliveryAllowed(boolean cardOnDeliveryAllowed) {
		this.cardOnDeliveryAllowed = cardOnDeliveryAllowed;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
}
