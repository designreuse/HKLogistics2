package com.hk.logistics.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the PincodeCourierMapping entity. This class is used in PincodeCourierMappingResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /pincode-courier-mappings?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PincodeCourierMappingCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter routingCode;

    private BooleanFilter applicableForCheapestCourier;

    private DoubleFilter estimatedDeliveryDays;

    private BooleanFilter pickupAvailable;

    private BooleanFilter prepaidAir;

    private BooleanFilter prepaidGround;

    private BooleanFilter codAir;

    private BooleanFilter codGround;

    private BooleanFilter reverseAir;

    private BooleanFilter reverseGround;

    private BooleanFilter cardOnDeliveryAir;

    private BooleanFilter cardOnDeliveryGround;

    private BooleanFilter deliveryTypeOne;

    private BooleanFilter deliveryTypeTwo;

    private LongFilter pincodeId;

    private LongFilter vendorWHCourierMappingId;

    private LongFilter sourceDestinationMappingId;

    public PincodeCourierMappingCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRoutingCode() {
        return routingCode;
    }

    public void setRoutingCode(StringFilter routingCode) {
        this.routingCode = routingCode;
    }

    public BooleanFilter getApplicableForCheapestCourier() {
        return applicableForCheapestCourier;
    }

    public void setApplicableForCheapestCourier(BooleanFilter applicableForCheapestCourier) {
        this.applicableForCheapestCourier = applicableForCheapestCourier;
    }

    public DoubleFilter getEstimatedDeliveryDays() {
        return estimatedDeliveryDays;
    }

    public void setEstimatedDeliveryDays(DoubleFilter estimatedDeliveryDays) {
        this.estimatedDeliveryDays = estimatedDeliveryDays;
    }

    public BooleanFilter getPickupAvailable() {
        return pickupAvailable;
    }

    public void setPickupAvailable(BooleanFilter pickupAvailable) {
        this.pickupAvailable = pickupAvailable;
    }

    public BooleanFilter getPrepaidAir() {
        return prepaidAir;
    }

    public void setPrepaidAir(BooleanFilter prepaidAir) {
        this.prepaidAir = prepaidAir;
    }

    public BooleanFilter getPrepaidGround() {
        return prepaidGround;
    }

    public void setPrepaidGround(BooleanFilter prepaidGround) {
        this.prepaidGround = prepaidGround;
    }

    public BooleanFilter getCodAir() {
        return codAir;
    }

    public void setCodAir(BooleanFilter codAir) {
        this.codAir = codAir;
    }

    public BooleanFilter getCodGround() {
        return codGround;
    }

    public void setCodGround(BooleanFilter codGround) {
        this.codGround = codGround;
    }

    public BooleanFilter getReverseAir() {
        return reverseAir;
    }

    public void setReverseAir(BooleanFilter reverseAir) {
        this.reverseAir = reverseAir;
    }

    public BooleanFilter getReverseGround() {
        return reverseGround;
    }

    public void setReverseGround(BooleanFilter reverseGround) {
        this.reverseGround = reverseGround;
    }

    public BooleanFilter getCardOnDeliveryAir() {
        return cardOnDeliveryAir;
    }

    public void setCardOnDeliveryAir(BooleanFilter cardOnDeliveryAir) {
        this.cardOnDeliveryAir = cardOnDeliveryAir;
    }

    public BooleanFilter getCardOnDeliveryGround() {
        return cardOnDeliveryGround;
    }

    public void setCardOnDeliveryGround(BooleanFilter cardOnDeliveryGround) {
        this.cardOnDeliveryGround = cardOnDeliveryGround;
    }

    public BooleanFilter getDeliveryTypeOne() {
        return deliveryTypeOne;
    }

    public void setDeliveryTypeOne(BooleanFilter deliveryTypeOne) {
        this.deliveryTypeOne = deliveryTypeOne;
    }

    public BooleanFilter getDeliveryTypeTwo() {
        return deliveryTypeTwo;
    }

    public void setDeliveryTypeTwo(BooleanFilter deliveryTypeTwo) {
        this.deliveryTypeTwo = deliveryTypeTwo;
    }

    public LongFilter getPincodeId() {
        return pincodeId;
    }

    public void setPincodeId(LongFilter pincodeId) {
        this.pincodeId = pincodeId;
    }

    public LongFilter getVendorWHCourierMappingId() {
        return vendorWHCourierMappingId;
    }

    public void setVendorWHCourierMappingId(LongFilter vendorWHCourierMappingId) {
        this.vendorWHCourierMappingId = vendorWHCourierMappingId;
    }

    public LongFilter getSourceDestinationMappingId() {
        return sourceDestinationMappingId;
    }

    public void setSourceDestinationMappingId(LongFilter sourceDestinationMappingId) {
        this.sourceDestinationMappingId = sourceDestinationMappingId;
    }

    @Override
    public String toString() {
        return "PincodeCourierMappingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (routingCode != null ? "routingCode=" + routingCode + ", " : "") +
                (applicableForCheapestCourier != null ? "applicableForCheapestCourier=" + applicableForCheapestCourier + ", " : "") +
                (estimatedDeliveryDays != null ? "estimatedDeliveryDays=" + estimatedDeliveryDays + ", " : "") +
                (pickupAvailable != null ? "pickupAvailable=" + pickupAvailable + ", " : "") +
                (prepaidAir != null ? "prepaidAir=" + prepaidAir + ", " : "") +
                (prepaidGround != null ? "prepaidGround=" + prepaidGround + ", " : "") +
                (codAir != null ? "codAir=" + codAir + ", " : "") +
                (codGround != null ? "codGround=" + codGround + ", " : "") +
                (reverseAir != null ? "reverseAir=" + reverseAir + ", " : "") +
                (reverseGround != null ? "reverseGround=" + reverseGround + ", " : "") +
                (cardOnDeliveryAir != null ? "cardOnDeliveryAir=" + cardOnDeliveryAir + ", " : "") +
                (cardOnDeliveryGround != null ? "cardOnDeliveryGround=" + cardOnDeliveryGround + ", " : "") +
                (deliveryTypeOne != null ? "deliveryTypeOne=" + deliveryTypeOne + ", " : "") +
                (deliveryTypeTwo != null ? "deliveryTypeTwo=" + deliveryTypeTwo + ", " : "") +
                (pincodeId != null ? "pincodeId=" + pincodeId + ", " : "") +
                (vendorWHCourierMappingId != null ? "vendorWHCourierMappingId=" + vendorWHCourierMappingId + ", " : "") +
                (sourceDestinationMappingId != null ? "sourceDestinationMappingId=" + sourceDestinationMappingId + ", " : "") +
            "}";
    }

}
