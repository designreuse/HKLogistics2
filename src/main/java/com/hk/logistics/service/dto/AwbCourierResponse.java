package com.hk.logistics.service.dto;

public class AwbCourierResponse {

    private String courierName;
    private Long courierId;
    private String awbNumber;
    private String routingCode;
    private String trackLink;
    private boolean hkShippingProvided;
    private Long operationsBitset;

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getAwbNumber() {
        return awbNumber;
    }

    public void setAwbNumber(String awbNumber) {
        this.awbNumber = awbNumber;
    }

    public String getRoutingCode() {
        return routingCode;
    }

    public void setRoutingCode(String routingCode) {
        this.routingCode = routingCode;
    }

    public String getTrackLink() {
        return trackLink;
    }

    public void setTrackLink(String trackLink) {
        this.trackLink = trackLink;
    }

    public boolean isHkShippingProvided() {
        return hkShippingProvided;
    }

    public void setHkShippingProvided(boolean hkShippingProvided) {
        this.hkShippingProvided = hkShippingProvided;
    }

    public Long getCourierId() {
      return courierId;
    }

    public void setCourierId(Long courierId) {
     this.courierId = courierId;
    }

    public Long getOperationsBitset() {
        return operationsBitset;
    }

    public void setOperationsBitset(Long operationsBitset) {
        this.operationsBitset = operationsBitset;
    }
}
