package com.hk.logistics.service;

import com.hk.logistics.domain.*;
import com.hk.logistics.service.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

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

    List<AwbDTO> upload(List<AwbDTO> batch);

    VendorWHCourierMappingDTO getVendorWHCourierMappingByCourierAndWHId(Long courierId, Long whId);

    VendorWHCourierMappingDTO getVendorWHCourierMappingByCourierAndVendorShortCode(Long courierId,
			String vendorShortCode);

    List<AwbExcelPojo> getAwbsForExcelDownload(AwbCriteria criteria);

    AwbDTO isAwbEligibleForDeletion(Long courierId, String awbNumber, Long whId, Boolean cod);
}
