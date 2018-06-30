package com.hk.logistics.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import com.hk.logistics.constants.EnumAwbStatus;
import com.hk.logistics.domain.Awb;
import com.hk.logistics.domain.Channel;
import com.hk.logistics.domain.Courier;
import com.hk.logistics.domain.CourierChannel;
import com.hk.logistics.enums.EnumChannel;
import com.hk.logistics.repository.AwbRepository;
import com.hk.logistics.repository.ChannelRepository;
import com.hk.logistics.repository.CourierChannelRepository;
import com.hk.logistics.repository.CourierRepository;
import com.hk.logistics.service.AwbService;
import com.hk.logistics.service.PincodeCourierService;
import com.hk.logistics.service.ShipmentPricingEngine;
import com.hk.logistics.service.VariantService;
import com.hk.logistics.service.dto.AwbAttachAPIDto;
import com.hk.logistics.service.dto.AwbChangeAPIDto;
import com.hk.logistics.service.dto.AwbCourierRequest;
import com.hk.logistics.service.dto.AwbCourierResponse;
import com.hk.logistics.service.dto.AwbDTO;
import com.hk.logistics.service.dto.AwbResponse;
import com.hk.logistics.service.dto.BrightChangeCourierRequest;
import com.hk.logistics.service.dto.CourierChangeAPIDto;
import com.hk.logistics.service.dto.CourierDetailResponse;
import com.hk.logistics.service.dto.CourierNameAndShortCodeDto;
import com.hk.logistics.service.dto.CourierResponse;
import com.hk.logistics.service.dto.DtoJsonConstants;
import com.hk.logistics.service.dto.HealthkartResponse;
import com.hk.logistics.service.dto.PincodeDeliveryInfoRequest;
import com.hk.logistics.service.dto.PincodeDeliveryInfoResponse;
import com.hk.logistics.service.dto.ReturnServiceabilityRequest;
import com.hk.logistics.service.dto.ReturnServiceabilityResponse;
import com.hk.logistics.service.dto.ShipmentPricingRequest;
import com.hk.logistics.service.dto.ShipmentPricingResponse;
import com.hk.logistics.service.dto.StoreVariantAPIObj;
import com.hk.logistics.service.dto.VariantServiceabilityRequest;
import com.hk.logistics.service.mapper.AwbMapper;

@RestController
@RequestMapping("/rest/api")
public class GenericResource {

	private final Logger log = LoggerFactory.getLogger(GenericResource.class);
	@Autowired
	VariantService variantService;
	@Autowired
	PincodeCourierService pincodeCourierService;
	@Autowired
    AwbService awbService;
	@Autowired
	ShipmentPricingEngine shipmentPricingEngine;
	@Autowired
    CourierRepository courierRepository;
	@Autowired
    ChannelRepository channelRepository;
	@Autowired
    CourierChannelRepository courierChannelRepository;
	@Autowired
    AwbRepository awbRepository;
	@Autowired
    AwbMapper awbMapper;

	@PostMapping("/pincode/variant/delivery/days")
	public PincodeDeliveryInfoResponse estimatedDeliverDate(PincodeDeliveryInfoRequest pincodeDeliveryInfoRequest) {
		return variantService.getVariantDeliveryInfoByPincode(pincodeDeliveryInfoRequest);
	}

	@PostMapping("/pincode/courier/fetchCouriers/{st}/{channel}")
	@Timed
	public CourierResponse getAllCouriersName(@PathParam(DtoJsonConstants.STORE_ID) String storeId, @PathParam(DtoJsonConstants.CHANNEL) String channel) {
		log.debug("REST request to get a page of Couriers");
		CourierResponse courierResponse = new CourierResponse(Long.parseLong(storeId));
		List<CourierChannel> courierChannels = pincodeCourierService.getAllCourierListForStoreAndChannel(storeId,channel);
		List<String> courierNameList = new ArrayList<>();
		for(CourierChannel courierChannel:courierChannels){
			courierNameList.add(courierChannel.getCourier().getName());
		}
		if(courierNameList!=null && courierNameList.size()!=0){
			courierResponse.setCourierName(courierNameList);
		}
		else{
			courierResponse.setException(true);
			courierResponse.addMessage("Could not fetch courier from DB");
		}

		return courierResponse;
	}

	@PostMapping("/pincode/courier/fetchCouriersNameAndShortCode/{st}/{channel}")
	@Timed
	public CourierDetailResponse getAllCouriersNameAndShortCode(@PathParam(DtoJsonConstants.STORE_ID) String storeId, @PathParam(DtoJsonConstants.CHANNEL) String channel) {
		log.debug("REST request to get a page of Couriers");
		CourierDetailResponse courierResponse = new CourierDetailResponse();
		List<CourierChannel> courierChannels = pincodeCourierService.getAllCourierListForStoreAndChannel(storeId,channel);
		List<CourierNameAndShortCodeDto> courierNameAndShortCodeDtos=new ArrayList<>();
		for(CourierChannel courierChannel:courierChannels){
			CourierNameAndShortCodeDto courierNameAndShortCodeDto=new CourierNameAndShortCodeDto();
			courierNameAndShortCodeDto.setName(courierChannel.getCourier().getName());
			courierNameAndShortCodeDto.setShortCode(courierNameAndShortCodeDto.getShortCode());
			courierNameAndShortCodeDtos.add(courierNameAndShortCodeDto);
		}
		if(courierNameAndShortCodeDtos!=null && courierNameAndShortCodeDtos.size()!=0){
			courierResponse.setCourierDetail(courierNameAndShortCodeDtos);
		}
		return courierResponse;
	}

	@PostMapping("/pincode/courier/markAwbUnused/{courierShortCode}/{awbNumber}/{store}/{channel}/{vendorCode}")
	@Timed
	public String markAwbUnused(@PathParam(DtoJsonConstants.COURIER_SHORT_CODE) String courierShortCode , @PathParam(DtoJsonConstants.AWB_NO) String awbNumber,
                                @PathParam(DtoJsonConstants.STORE) String store, @PathParam(DtoJsonConstants.CHANNEL) String channel, @PathParam(DtoJsonConstants.VENDOR_CODE) String vendorCode){
		if (awbNumber == null || awbNumber.isEmpty() || courierShortCode == null || courierShortCode.isEmpty()) {
			log.error("Awb no or courier name cannot be null");
			return null;
		}
		String msg=awbService.markAwbUnused(courierShortCode, awbNumber, store, channel, vendorCode, null);
		return new Gson().toJson(msg);
	}

	@PostMapping("/pincode/shipment/pricing")
	@Timed
	public String getShipmentPricingResponseForBrightShipment(ShipmentPricingRequest shipmentPricingRequest) {
		ShipmentPricingResponse shipmentPricingResponse = shipmentPricingEngine.getShipmentPricingResponseForBright(shipmentPricingRequest);
		return new Gson().toJson(shipmentPricingResponse);
	}

	@PostMapping("/pincode/awb/attachAwbForCourier")
    @Timed
    public AwbCourierResponse attachAwbForBrightCourierChange(@Valid @RequestBody BrightChangeCourierRequest brightChangeCourierRequest) throws URISyntaxException {
    	Courier courier=courierRepository.findByShortCode(brightChangeCourierRequest.getCourierShortCode());
    	Awb awb=awbService.attachAwbForBright(brightChangeCourierRequest,courier);
    	AwbCourierResponse awbCourierResponse=new AwbCourierResponse();
    	awbCourierResponse.setAwbNumber(awb.getAwbNumber());
        awbCourierResponse.setCourierId(courier.getId());
        awbCourierResponse.setCourierName(courier.getName());
        awbCourierResponse.setTrackLink(awb.getTrackingLink());

       // awbCourierResponse.setOperationsBitset(courier.getOperationsBitset());TODO to be removed
    	return awbCourierResponse;
    }

	@GetMapping("awb/validateAwb/{courierShortCode}/{awbNumber}/{isCod}/{store}/{channel}/{fulfillmentCentreCode}")
	@Timed
    public String validateAwb(@PathParam(DtoJsonConstants.COURIER_SHORT_CODE) String courierShortCode , @PathParam(DtoJsonConstants.AWB_NO) String awbNumber,
                              @PathParam(DtoJsonConstants.IS_COD) String isCod, @PathParam(DtoJsonConstants.STORE) String store, @PathParam(DtoJsonConstants.CHANNEL) String channel,
                              @PathParam(DtoJsonConstants.FULFILLMENT_CENTRE_CODE) String fulfillmentCentreCode){

        HealthkartResponse healthkartResponse = null;
        try{
            if (awbNumber == null || courierShortCode == null) {
                log.error("Awb no or courier short code cannot be null!!");
                healthkartResponse = new HealthkartResponse(HealthkartResponse.STATUS_ERROR,"Awb no is already used!!Please use another awb!!");
            }else {
            	Courier courier=courierRepository.findByShortCode(courierShortCode);
                if(courier != null){
                	Awb awb=awbService.validateAwb(courier,awbNumber,fulfillmentCentreCode,store,channel,isCod);
                    if (awb != null){
                        if(awb.getAwbStatus().equals(EnumAwbStatus.Used.getAsAwbStatus())){
                            healthkartResponse = new HealthkartResponse(HealthkartResponse.STATUS_ERROR,"Awb no is already used!!Please use another awb!!");
                        }else if (awb.getAwbStatus().equals(EnumAwbStatus.Attach.getAsAwbStatus())){
                            healthkartResponse = new HealthkartResponse(HealthkartResponse.STATUS_ERROR,"Awb no is already attached with some order!!Please use another awb!!");
                        }else {
                            healthkartResponse = new HealthkartResponse(HealthkartResponse.STATUS_OK,"Valid awb number.");
                        }
                    }else{
                        healthkartResponse = new HealthkartResponse(HealthkartResponse.STATUS_ERROR,"Awb no does not exist!!Please use another awb!!");
                    }
                }else {
                    healthkartResponse = new HealthkartResponse(HealthkartResponse.STATUS_ERROR,"Courier not found!!");
                }
            }
        }catch (Exception ex){
            log.error("Exception occurred while validating " + awbNumber + " awb number for " + courierShortCode + " courier !!",ex);
            ex.printStackTrace();
            healthkartResponse = new HealthkartResponse(HealthkartResponse.STATUS_ERROR,"Exception occurred while validating " + awbNumber + " awb number for " + courierShortCode + " courier !!");
        }
        return new Gson().toJson(healthkartResponse);
    }

    @GetMapping("/awb/markAwbUsed/{courierShortCode}/{awbNumber}/{store}/{channel}/{fulfillmentCentreCode}")
    public String markAwbUsed(@PathParam(DtoJsonConstants.COURIER_SHORT_CODE) String courierShortCode , @PathParam(DtoJsonConstants.AWB_NO) String awbNumber, @PathParam(DtoJsonConstants.STORE) String store,
                              @PathParam(DtoJsonConstants.CHANNEL) String channel,
                              @PathParam(DtoJsonConstants.FULFILLMENT_CENTRE_CODE) String fulfillmentCentreCode){
        HealthkartResponse healthkartResponse = null;
        if (awbNumber == null || courierShortCode == null) {
            healthkartResponse = new HealthkartResponse(HealthkartResponse.STATUS_ERROR,"Awb no or courier short Code cannot be null!!");
        }else {
        	Courier courier=courierRepository.findByShortCode(courierShortCode);
            if(courier != null){
            	Awb awb=awbService.markAwbUnused(courier,awbNumber,fulfillmentCentreCode,store,channel,null);
                if (awb != null){
                    if(awb.getAwbStatus().equals(EnumAwbStatus.Attach.getAsAwbStatus())){
                        healthkartResponse = new HealthkartResponse(HealthkartResponse.STATUS_ERROR,"Awb no is already attached with some order!!Please use another awb!!");
                    }else if (awb.getAwbStatus().equals(EnumAwbStatus.Used.getAsAwbStatus())){
                        healthkartResponse = new HealthkartResponse(HealthkartResponse.STATUS_ERROR,"Awb no is already used with some order!!Please use another awb!!");
                    }else {
                    	awb.setAwbStatus(EnumAwbStatus.Used.getAsAwbStatus());
                        healthkartResponse = new HealthkartResponse(HealthkartResponse.STATUS_OK,"Awb mark as used successfully.");
                    }
                }else {
                    healthkartResponse = new HealthkartResponse(HealthkartResponse.STATUS_ERROR,"Awb no does not exist!!");
                }
            }else {
                healthkartResponse = new HealthkartResponse(HealthkartResponse.STATUS_ERROR,"Courier not found!!");
            }
        }
        return new Gson().toJson(healthkartResponse);
    }

    @PostMapping("/storeOrder/availability")
    public StoreVariantAPIObj getVariantServiceabilityDetails(@Valid @RequestBody VariantServiceabilityRequest variantServiceabilityRequest) {
    	StoreVariantAPIObj storeVariantAPIObj=variantService.getVariantServiceabilityDetails(variantServiceabilityRequest);
    	return storeVariantAPIObj;
    }

    @PostMapping("/storeOrder/return/serviceability")
    @Timed
    public String getServiceabilityForReturnRequest(ReturnServiceabilityRequest returnServiceabilityRequest) {
        Boolean isReturnAvailable=pincodeCourierService.checkIfReturnServiceabilityAvailable(returnServiceabilityRequest);
        ReturnServiceabilityResponse returnServiceabilityResponse=new ReturnServiceabilityResponse();
        returnServiceabilityResponse.setIsReturnAvailable(isReturnAvailable);
        return new Gson().toJson(isReturnAvailable);
    }

    @PostMapping("/opr/awb/courier")
    @Timed
    public AwbCourierResponse getAwbCourierResponse(@Valid @RequestBody AwbCourierRequest awbCourierRequest) throws URISyntaxException {
    	AwbCourierResponse awbCourierResponse=awbService.getAwbCourierResponse(awbCourierRequest);
    	return awbCourierResponse;
    }

    @PostMapping("/opr/awb/courier1")
    @Timed
    public AwbDTO attachAwbForCourier(AwbAttachAPIDto awbAttachAPIDto) throws URISyntaxException {
    	Awb awb=awbService.attachAwb(awbAttachAPIDto);
    	AwbDTO result = awbMapper.toDto(awb);
    	return result;
    }

    @PostMapping("/courier/change")
    public AwbResponse changeCourier(CourierChangeAPIDto courierChangeAPIDto) {
    	Awb awb=awbService.changeCourier(courierChangeAPIDto);
    	AwbResponse awbResponse=new AwbResponse();
    	awbResponse.setAwbBarCode(awb.getAwbBarCode());
    	awbResponse.setAwbNumber(awb.getAwbNumber());
    	Courier courier=awb.getVendorWHCourierMapping().getCourier();
    	awbResponse.setCourierShortCode(courier.getShortCode());
    	awbResponse.setCourierName(courier.getName());
    	awbResponse.setTrackingLink(awb.getTrackingLink());
    	return awbResponse;
    }

    @PostMapping("/awb/change")
	public AwbResponse changeAwbNumber(AwbChangeAPIDto awbChangeAPIDto) {
		//ShippingOrder shippingOrder = shipment.getShippingOrder();
		//Awb currentAwb = shipment.getAwb();
		Awb awb=awbService.changeAwbNumber(awbChangeAPIDto);
		AwbResponse awbResponse=new AwbResponse();
    	awbResponse.setAwbBarCode(awb.getAwbBarCode());
    	awbResponse.setAwbNumber(awb.getAwbNumber());
    	Courier courier=awb.getVendorWHCourierMapping().getCourier();
    	awbResponse.setCourierShortCode(courier.getShortCode());
    	awbResponse.setCourierName(courier.getName());
    	awbResponse.setTrackingLink(awb.getTrackingLink());
    	return awbResponse;
	}

    @GetMapping("/awb/preserveForBright/{awbNumber}/{store}")
    @Timed
    public String preserveBrightAwb(@PathParam(DtoJsonConstants.AWB_NO) String awbNumber, @PathParam(DtoJsonConstants.STORE) String store) {
        if (awbNumber == null || awbNumber.isEmpty()) {
            return null;
        }
        Channel channel=channelRepository.findByNameAndStore(EnumChannel.BRIGHT.getName(), store);
        Awb awb = awbRepository.findByChannelAndAwbNumber(channel, awbNumber);
        if (awb != null){
            awb.setAwbStatus(EnumAwbStatus.Unused.getAsAwbStatus());
            awbRepository.save(awb);
        }
        Gson gson = new Gson();
        return ((awb != null && awb.getAwbStatus().getId().equals(EnumAwbStatus.Unused.getId())) ? gson.toJson(Boolean.TRUE) : gson.toJson(Boolean.FALSE));
    }
}
