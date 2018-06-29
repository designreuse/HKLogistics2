package com.hk.logistics.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PincodeCourierMapping entity.
 */
public class PincodeCourierMappingDTO implements Serializable {

    private Long id;

    private String routingCode;

    @NotNull
    private Boolean applicableForCheapestCourier;

    private Double estimatedDeliveryDays;

    @NotNull
    private Boolean pickupAvailable;

    @NotNull
    private Boolean prepaidAir;

    @NotNull
    private Boolean prepaidGround;

    @NotNull
    private Boolean codAir;

    @NotNull
    private Boolean codGround;

    @NotNull
    private Boolean reverseAir;

    @NotNull
    private Boolean reverseGround;

    @NotNull
    private Boolean cardOnDeliveryAir;

    @NotNull
    private Boolean cardOnDeliveryGround;

    private Boolean deliveryTypeOne;

    private Boolean deliveryTypeTwo;

    private Long vendorWHCourierMappingId;

    private Long sourceDestinationMappingId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoutingCode() {
        return routingCode;
    }

    public void setRoutingCode(String routingCode) {
        this.routingCode = routingCode;
    }

    public Boolean isApplicableForCheapestCourier() {
        return applicableForCheapestCourier;
    }

    public void setApplicableForCheapestCourier(Boolean applicableForCheapestCourier) {
        this.applicableForCheapestCourier = applicableForCheapestCourier;
    }

    public Double getEstimatedDeliveryDays() {
        return estimatedDeliveryDays;
    }

    public void setEstimatedDeliveryDays(Double estimatedDeliveryDays) {
        this.estimatedDeliveryDays = estimatedDeliveryDays;
    }

    public Boolean isPickupAvailable() {
        return pickupAvailable;
    }

    public void setPickupAvailable(Boolean pickupAvailable) {
        this.pickupAvailable = pickupAvailable;
    }

    public Boolean isPrepaidAir() {
        return prepaidAir;
    }

    public void setPrepaidAir(Boolean prepaidAir) {
        this.prepaidAir = prepaidAir;
    }

    public Boolean isPrepaidGround() {
        return prepaidGround;
    }

    public void setPrepaidGround(Boolean prepaidGround) {
        this.prepaidGround = prepaidGround;
    }

    public Boolean isCodAir() {
        return codAir;
    }

    public void setCodAir(Boolean codAir) {
        this.codAir = codAir;
    }

    public Boolean isCodGround() {
        return codGround;
    }

    public void setCodGround(Boolean codGround) {
        this.codGround = codGround;
    }

    public Boolean isReverseAir() {
        return reverseAir;
    }

    public void setReverseAir(Boolean reverseAir) {
        this.reverseAir = reverseAir;
    }

    public Boolean isReverseGround() {
        return reverseGround;
    }

    public void setReverseGround(Boolean reverseGround) {
        this.reverseGround = reverseGround;
    }

    public Boolean isCardOnDeliveryAir() {
        return cardOnDeliveryAir;
    }

    public void setCardOnDeliveryAir(Boolean cardOnDeliveryAir) {
        this.cardOnDeliveryAir = cardOnDeliveryAir;
    }

    public Boolean isCardOnDeliveryGround() {
        return cardOnDeliveryGround;
    }

    public void setCardOnDeliveryGround(Boolean cardOnDeliveryGround) {
        this.cardOnDeliveryGround = cardOnDeliveryGround;
    }

    public Boolean isDeliveryTypeOne() {
        return deliveryTypeOne;
    }

    public void setDeliveryTypeOne(Boolean deliveryTypeOne) {
        this.deliveryTypeOne = deliveryTypeOne;
    }

    public Boolean isDeliveryTypeTwo() {
        return deliveryTypeTwo;
    }

    public void setDeliveryTypeTwo(Boolean deliveryTypeTwo) {
        this.deliveryTypeTwo = deliveryTypeTwo;
    }

    public Long getVendorWHCourierMappingId() {
        return vendorWHCourierMappingId;
    }

    public void setVendorWHCourierMappingId(Long vendorWHCourierMappingId) {
        this.vendorWHCourierMappingId = vendorWHCourierMappingId;
    }

    public Long getSourceDestinationMappingId() {
        return sourceDestinationMappingId;
    }

    public void setSourceDestinationMappingId(Long sourceDestinationMappingId) {
        this.sourceDestinationMappingId = sourceDestinationMappingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PincodeCourierMappingDTO pincodeCourierMappingDTO = (PincodeCourierMappingDTO) o;
        if (pincodeCourierMappingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pincodeCourierMappingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PincodeCourierMappingDTO{" +
            "id=" + getId() +
            ", routingCode='" + getRoutingCode() + "'" +
            ", applicableForCheapestCourier='" + isApplicableForCheapestCourier() + "'" +
            ", estimatedDeliveryDays=" + getEstimatedDeliveryDays() +
            ", pickupAvailable='" + isPickupAvailable() + "'" +
            ", prepaidAir='" + isPrepaidAir() + "'" +
            ", prepaidGround='" + isPrepaidGround() + "'" +
            ", codAir='" + isCodAir() + "'" +
            ", codGround='" + isCodGround() + "'" +
            ", reverseAir='" + isReverseAir() + "'" +
            ", reverseGround='" + isReverseGround() + "'" +
            ", cardOnDeliveryAir='" + isCardOnDeliveryAir() + "'" +
            ", cardOnDeliveryGround='" + isCardOnDeliveryGround() + "'" +
            ", deliveryTypeOne='" + isDeliveryTypeOne() + "'" +
            ", deliveryTypeTwo='" + isDeliveryTypeTwo() + "'" +
            ", vendorWHCourierMapping=" + getVendorWHCourierMappingId() +
            ", sourceDestinationMapping=" + getSourceDestinationMappingId() +
            "}";
    }
}
