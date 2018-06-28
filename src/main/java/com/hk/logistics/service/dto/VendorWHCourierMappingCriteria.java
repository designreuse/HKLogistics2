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
 * Criteria class for the VendorWHCourierMapping entity. This class is used in VendorWHCourierMappingResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /vendor-wh-courier-mappings?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VendorWHCourierMappingCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private BooleanFilter active;

    private StringFilter vendor;

    private LongFilter warehouse;

    private LongFilter courierChannelId;

    public VendorWHCourierMappingCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public StringFilter getVendor() {
        return vendor;
    }

    public void setVendor(StringFilter vendor) {
        this.vendor = vendor;
    }

    public LongFilter getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(LongFilter warehouse) {
        this.warehouse = warehouse;
    }

    public LongFilter getCourierChannelId() {
        return courierChannelId;
    }

    public void setCourierChannelId(LongFilter courierChannelId) {
        this.courierChannelId = courierChannelId;
    }

    @Override
    public String toString() {
        return "VendorWHCourierMappingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (vendor != null ? "vendor=" + vendor + ", " : "") +
                (warehouse != null ? "warehouse=" + warehouse + ", " : "") +
                (courierChannelId != null ? "courierChannelId=" + courierChannelId + ", " : "") +
            "}";
    }

}
