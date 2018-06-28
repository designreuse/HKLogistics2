package com.hk.logistics.service.dto;

public class PincodeDeliveryInfoResponse extends AbstractBaseResponse {

	private Integer estmDeliveryDays;
	private boolean codAllowed;
	private String pin;

	public PincodeDeliveryInfoResponse(Long storeId) {
		super(storeId);
	}

	public Integer getEstmDeliveryDays() {
		return estmDeliveryDays;
	}

	public void setEstmDeliveryDays(Integer estmDeliveryDays) {
		this.estmDeliveryDays = estmDeliveryDays;
	}

	public boolean isCodAllowed() {
		return codAllowed;
	}

	public void setCodAllowed(boolean codAllowed) {
		this.codAllowed = codAllowed;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

}
