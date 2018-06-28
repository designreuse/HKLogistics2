package com.hk.logistics.service.dto;

public class ShipmentPricingResponse {
    Double estmShipmentCharge;
    Double estmCollectionCharge;
    Double extraCharge;

    public Double getEstmShipmentCharge() {
        return estmShipmentCharge;
    }

    public void setEstmShipmentCharge(Double estmShipmentCharge) {
        this.estmShipmentCharge = estmShipmentCharge;
    }

    public Double getEstmCollectionCharge() {
        return estmCollectionCharge;
    }

    public void setEstmCollectionCharge(Double estmCollectionCharge) {
        this.estmCollectionCharge = estmCollectionCharge;
    }

    public Double getExtraCharge() {
        return extraCharge;
    }

    public void setExtraCharge(Double extraCharge) {
        this.extraCharge = extraCharge;
    }
}
