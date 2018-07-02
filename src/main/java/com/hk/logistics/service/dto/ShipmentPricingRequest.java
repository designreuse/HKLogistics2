package com.hk.logistics.service.dto;

public class ShipmentPricingRequest {
    Double shipmentBoxWeight;
    Long courierId;
    Long boxSizeId;
    String pincode;
    String warehouseFcCode;
    Double shippingOrderAmount;
    Long paymentModeId;
    String courierShortCode;

    public Double getShipmentBoxWeight() {
        return shipmentBoxWeight;
    }

    public void setShipmentBoxWeight(Double shipmentBoxWeight) {
        this.shipmentBoxWeight = shipmentBoxWeight;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public Long getBoxSizeId() {
        return boxSizeId;
    }

    public void setBoxSizeId(Long boxSizeId) {
        this.boxSizeId = boxSizeId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getWarehouseFcCode() {
        return warehouseFcCode;
    }

    public void setWarehouseFcCode(String warehouseFcCode) {
        this.warehouseFcCode = warehouseFcCode;
    }

    public Double getShippingOrderAmount() {
        return shippingOrderAmount;
    }

    public void setShippingOrderAmount(Double shippingOrderAmount) {
        this.shippingOrderAmount = shippingOrderAmount;
    }

    public Long getPaymentModeId() {
        return paymentModeId;
    }

    public void setPaymentModeId(Long paymentModeId) {
        this.paymentModeId = paymentModeId;
    }

	public String getCourierName() {
		return courierShortCode;
	}

	public void setCourierName(String courierName) {
		this.courierShortCode = courierName;
	}

	public String getCourierShortCode() {
		return courierShortCode;
	}

	public void setCourierShortCode(String courierShortCode) {
		this.courierShortCode = courierShortCode;
	}

}
