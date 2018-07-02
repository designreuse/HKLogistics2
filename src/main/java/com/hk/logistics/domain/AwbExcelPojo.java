package com.hk.logistics.domain;

import java.io.Serializable;

import com.poiji.annotation.ExcelCellName;

public class AwbExcelPojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7377773125806014415L;

	@ExcelCellName("COURIER_ID")	
	private Long courierId;
	
	@ExcelCellName("CHANNEL_NAME")
	private String channelName;
	
	@ExcelCellName("AWB_NUMBER")
	private String awbNumber;
	
	@ExcelCellName("COD")
	private Boolean cod;
	
	@ExcelCellName("WAREHOUSE")
	private Long whId;
	
	@ExcelCellName("VENDOR")
	private String vendorShortCode;
	
	private String awbStatus;

	public Long getCourierId() {
		return courierId;
	}

	public void setCourierId(Long courierId) {
		this.courierId = courierId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getAwbNumber() {
		return awbNumber;
	}

	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}

	public Boolean getCod() {
		return cod;
	}

	public void setCod(Boolean cod) {
		this.cod = cod;
	}

	public Long getWhId() {
		return whId;
	}

	public void setWhId(Long whId) {
		this.whId = whId;
	}

	public String getVendorShortCode() {
		return vendorShortCode;
	}

	public void setVendorShortCode(String vendorShortCode) {
		this.vendorShortCode = vendorShortCode;
	}

	public String getAwbStatus() {
		return awbStatus;
	}

	public void setAwbStatus(String awbStatus) {
		this.awbStatus = awbStatus;
	}
	
	
}
