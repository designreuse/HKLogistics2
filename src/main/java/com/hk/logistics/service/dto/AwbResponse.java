package com.hk.logistics.service.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AwbResponse {

	@NotNull
	private String awbNumber;
	@NotNull
	private String awbBarCode;
	@NotNull
	private Boolean cod;
	@NotNull
	private LocalDate createDate;
	private String returnAwbNumber;
	private String returnAwbBarCode;
	private String trackingLink;
	private String courierShortCode;
	private String courierName;

	public String getAwbNumber() {
		return awbNumber;
	}

	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}

	public String getAwbBarCode() {
		return awbBarCode;
	}

	public void setAwbBarCode(String awbBarCode) {
		this.awbBarCode = awbBarCode;
	}

	public Boolean isCod() {
		return cod;
	}

	public void setCod(Boolean cod) {
		this.cod = cod;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public String getReturnAwbNumber() {
		return returnAwbNumber;
	}

	public void setReturnAwbNumber(String returnAwbNumber) {
		this.returnAwbNumber = returnAwbNumber;
	}

	public String getReturnAwbBarCode() {
		return returnAwbBarCode;
	}

	public void setReturnAwbBarCode(String returnAwbBarCode) {
		this.returnAwbBarCode = returnAwbBarCode;
	}

	public String getTrackingLink() {
		return trackingLink;
	}

	public void setTrackingLink(String trackingLink) {
		this.trackingLink = trackingLink;
	}

	public String getCourierShortCode() {
		return courierShortCode;
	}

	public void setCourierShortCode(String courierShortCode) {
		this.courierShortCode = courierShortCode;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public Boolean getCod() {
		return cod;
	}

}
