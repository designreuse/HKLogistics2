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
 * Criteria class for the Courier entity. This class is used in CourierResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /couriers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourierCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private StringFilter shortCode;

    private BooleanFilter active;

    private LongFilter parentCourierId;

    private BooleanFilter hkShipping;

    private BooleanFilter vendorShipping;

    private BooleanFilter reversePickup;

    private LongFilter courierChannelId;

    private LongFilter courierGroupId;

    public CourierCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getShortCode() {
        return shortCode;
    }

    public void setShortCode(StringFilter shortCode) {
        this.shortCode = shortCode;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getParentCourierId() {
        return parentCourierId;
    }

    public void setParentCourierId(LongFilter parentCourierId) {
        this.parentCourierId = parentCourierId;
    }

    public BooleanFilter getHkShipping() {
        return hkShipping;
    }

    public void setHkShipping(BooleanFilter hkShipping) {
        this.hkShipping = hkShipping;
    }

    public BooleanFilter getVendorShipping() {
        return vendorShipping;
    }

    public void setVendorShipping(BooleanFilter vendorShipping) {
        this.vendorShipping = vendorShipping;
    }

    public BooleanFilter getReversePickup() {
        return reversePickup;
    }

    public void setReversePickup(BooleanFilter reversePickup) {
        this.reversePickup = reversePickup;
    }

    public LongFilter getCourierChannelId() {
        return courierChannelId;
    }

    public void setCourierChannelId(LongFilter courierChannelId) {
        this.courierChannelId = courierChannelId;
    }

    public LongFilter getCourierGroupId() {
        return courierGroupId;
    }

    public void setCourierGroupId(LongFilter courierGroupId) {
        this.courierGroupId = courierGroupId;
    }

    @Override
    public String toString() {
        return "CourierCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (shortCode != null ? "shortCode=" + shortCode + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (parentCourierId != null ? "parentCourierId=" + parentCourierId + ", " : "") +
                (hkShipping != null ? "hkShipping=" + hkShipping + ", " : "") +
                (vendorShipping != null ? "vendorShipping=" + vendorShipping + ", " : "") +
                (reversePickup != null ? "reversePickup=" + reversePickup + ", " : "") +
                (courierChannelId != null ? "courierChannelId=" + courierChannelId + ", " : "") +
                (courierGroupId != null ? "courierGroupId=" + courierGroupId + ", " : "") +
            "}";
    }

}
