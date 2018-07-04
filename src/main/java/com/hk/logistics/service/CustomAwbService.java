package com.hk.logistics.service;

import org.springframework.stereotype.Service;

import com.hk.logistics.domain.Awb;
import com.hk.logistics.domain.AwbStatus;
import com.hk.logistics.domain.Channel;
import com.hk.logistics.domain.Courier;
import com.hk.logistics.domain.CourierChannel;
import com.hk.logistics.domain.VendorWHCourierMapping;
import com.hk.logistics.service.dto.AwbAttachAPIDto;
import com.hk.logistics.service.dto.AwbChangeAPIDto;
import com.hk.logistics.service.dto.AwbCourierRequest;
import com.hk.logistics.service.dto.AwbCourierResponse;
import com.hk.logistics.service.dto.AwbResponse;
import com.hk.logistics.service.dto.BrightChangeCourierRequest;
import com.hk.logistics.service.dto.CourierChangeAPIDto;

public interface CustomAwbService {

	Awb getAvailableAwbByVendorWHCourierMappingAndCodAndAwbStatusAndChannel(VendorWHCourierMapping vendorWHCourierMapping,
			Boolean cod, AwbStatus awbStatus, Channel channel);

	Awb attachAwbForShipment(Courier suggestedCourier, CourierChannel courierChannel,
			VendorWHCourierMapping vendorWHCourierMapping, Boolean cod);

	AwbResponse attachAwb(AwbAttachAPIDto awbAttachAPIDto);

	AwbCourierResponse getAwbCourierResponse(AwbCourierRequest awbCourierRequest);

	Awb changeCourier(CourierChangeAPIDto awbChaneAPIDto);

	Awb changeAwbNumber(AwbChangeAPIDto awbChangeAPIDto);

	Awb attachAwbForBright(BrightChangeCourierRequest brightChangeCourierRequest, Courier courier);

	String markAwbUnused(String courierShortCode, String awbNumber, String store, String channel, String vendorCode,
			Long warehouse);

	Awb validateAwb(Courier courier, String awbNumber, String fulfillmentCentreCode, String store, String channelName,
			String isCod);

	Awb markAwbUnused(Courier courier, String awbNumber, String fulfillmentCentreCode, String store, String channelName,
			String isCod);
}
