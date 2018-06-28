package com.hk.logistics.service.dto;

import java.util.List;

public class CourierDetailResponse {

	private List<CourierNameAndShortCodeDto> courierDetail;

	public List<CourierNameAndShortCodeDto> getCourierDetail() {
		return courierDetail;
	}

	public void setCourierDetail(List<CourierNameAndShortCodeDto> courierDetail) {
		this.courierDetail = courierDetail;
	}
 
}
