package com.hk.logistics.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the Awb entity. This class is used in AwbResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /awbs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AwbCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter awbNumber;

    private StringFilter awbBarCode;

    private BooleanFilter cod;

    private LocalDateFilter createDate;

    private StringFilter returnAwbNumber;

    private StringFilter returnAwbBarCode;

    private StringFilter trackingLink;

    private LongFilter channelId;

    private LongFilter vendorWHCourierMappingId;

    private LongFilter awbStatusId;

    public AwbCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAwbNumber() {
        return awbNumber;
    }

    public void setAwbNumber(StringFilter awbNumber) {
        this.awbNumber = awbNumber;
    }

    public StringFilter getAwbBarCode() {
        return awbBarCode;
    }

    public void setAwbBarCode(StringFilter awbBarCode) {
        this.awbBarCode = awbBarCode;
    }

    public BooleanFilter getCod() {
        return cod;
    }

    public void setCod(BooleanFilter cod) {
        this.cod = cod;
    }

    public LocalDateFilter getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateFilter createDate) {
        this.createDate = createDate;
    }

    public StringFilter getReturnAwbNumber() {
        return returnAwbNumber;
    }

    public void setReturnAwbNumber(StringFilter returnAwbNumber) {
        this.returnAwbNumber = returnAwbNumber;
    }

    public StringFilter getReturnAwbBarCode() {
        return returnAwbBarCode;
    }

    public void setReturnAwbBarCode(StringFilter returnAwbBarCode) {
        this.returnAwbBarCode = returnAwbBarCode;
    }

    public StringFilter getTrackingLink() {
        return trackingLink;
    }

    public void setTrackingLink(StringFilter trackingLink) {
        this.trackingLink = trackingLink;
    }

    public LongFilter getChannelId() {
        return channelId;
    }

    public void setChannelId(LongFilter channelId) {
        this.channelId = channelId;
    }

    public LongFilter getVendorWHCourierMappingId() {
        return vendorWHCourierMappingId;
    }

    public void setVendorWHCourierMappingId(LongFilter vendorWHCourierMappingId) {
        this.vendorWHCourierMappingId = vendorWHCourierMappingId;
    }

    public LongFilter getAwbStatusId() {
        return awbStatusId;
    }

    public void setAwbStatusId(LongFilter awbStatusId) {
        this.awbStatusId = awbStatusId;
    }

    @Override
    public String toString() {
        return "AwbCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (awbNumber != null ? "awbNumber=" + awbNumber + ", " : "") +
                (awbBarCode != null ? "awbBarCode=" + awbBarCode + ", " : "") +
                (cod != null ? "cod=" + cod + ", " : "") +
                (createDate != null ? "createDate=" + createDate + ", " : "") +
                (returnAwbNumber != null ? "returnAwbNumber=" + returnAwbNumber + ", " : "") +
                (returnAwbBarCode != null ? "returnAwbBarCode=" + returnAwbBarCode + ", " : "") +
                (trackingLink != null ? "trackingLink=" + trackingLink + ", " : "") +
                (channelId != null ? "channelId=" + channelId + ", " : "") +
                (vendorWHCourierMappingId != null ? "vendorWHCourierMappingId=" + vendorWHCourierMappingId + ", " : "") +
                (awbStatusId != null ? "awbStatusId=" + awbStatusId + ", " : "") +
            "}";
    }

}
