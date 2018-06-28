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
 * Criteria class for the PincodeRegionZone entity. This class is used in PincodeRegionZoneResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /pincode-region-zones?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PincodeRegionZoneCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter regionTypeId;

    private LongFilter courierGroupId;

    private LongFilter sourceDestinationMappingId;

    public PincodeRegionZoneCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getRegionTypeId() {
        return regionTypeId;
    }

    public void setRegionTypeId(LongFilter regionTypeId) {
        this.regionTypeId = regionTypeId;
    }

    public LongFilter getCourierGroupId() {
        return courierGroupId;
    }

    public void setCourierGroupId(LongFilter courierGroupId) {
        this.courierGroupId = courierGroupId;
    }

    public LongFilter getSourceDestinationMappingId() {
        return sourceDestinationMappingId;
    }

    public void setSourceDestinationMappingId(LongFilter sourceDestinationMappingId) {
        this.sourceDestinationMappingId = sourceDestinationMappingId;
    }

    @Override
    public String toString() {
        return "PincodeRegionZoneCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (regionTypeId != null ? "regionTypeId=" + regionTypeId + ", " : "") +
                (courierGroupId != null ? "courierGroupId=" + courierGroupId + ", " : "") +
                (sourceDestinationMappingId != null ? "sourceDestinationMappingId=" + sourceDestinationMappingId + ", " : "") +
            "}";
    }

}
