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
 * Criteria class for the Pincode entity. This class is used in PincodeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /pincodes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PincodeCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter pincode;

    private StringFilter region;

    private StringFilter locality;

    private DoubleFilter lastMileCost;

    private StringFilter tier;

    private LongFilter cityId;

    private LongFilter stateId;

    private LongFilter zoneId;

    private LongFilter hubId;

    public PincodeCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPincode() {
        return pincode;
    }

    public void setPincode(StringFilter pincode) {
        this.pincode = pincode;
    }

    public StringFilter getRegion() {
        return region;
    }

    public void setRegion(StringFilter region) {
        this.region = region;
    }

    public StringFilter getLocality() {
        return locality;
    }

    public void setLocality(StringFilter locality) {
        this.locality = locality;
    }

    public DoubleFilter getLastMileCost() {
        return lastMileCost;
    }

    public void setLastMileCost(DoubleFilter lastMileCost) {
        this.lastMileCost = lastMileCost;
    }

    public StringFilter getTier() {
        return tier;
    }

    public void setTier(StringFilter tier) {
        this.tier = tier;
    }

    public LongFilter getCityId() {
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
    }

    public LongFilter getStateId() {
        return stateId;
    }

    public void setStateId(LongFilter stateId) {
        this.stateId = stateId;
    }

    public LongFilter getZoneId() {
        return zoneId;
    }

    public void setZoneId(LongFilter zoneId) {
        this.zoneId = zoneId;
    }

    public LongFilter getHubId() {
        return hubId;
    }

    public void setHubId(LongFilter hubId) {
        this.hubId = hubId;
    }

    @Override
    public String toString() {
        return "PincodeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (pincode != null ? "pincode=" + pincode + ", " : "") +
                (region != null ? "region=" + region + ", " : "") +
                (locality != null ? "locality=" + locality + ", " : "") +
                (lastMileCost != null ? "lastMileCost=" + lastMileCost + ", " : "") +
                (tier != null ? "tier=" + tier + ", " : "") +
                (cityId != null ? "cityId=" + cityId + ", " : "") +
                (stateId != null ? "stateId=" + stateId + ", " : "") +
                (zoneId != null ? "zoneId=" + zoneId + ", " : "") +
                (hubId != null ? "hubId=" + hubId + ", " : "") +
            "}";
    }

}
