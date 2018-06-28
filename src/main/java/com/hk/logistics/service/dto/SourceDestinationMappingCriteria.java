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
 * Criteria class for the SourceDestinationMapping entity. This class is used in SourceDestinationMappingResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /source-destination-mappings?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SourceDestinationMappingCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter sourcePincode;

    private StringFilter destinationPincode;

    public SourceDestinationMappingCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSourcePincode() {
        return sourcePincode;
    }

    public void setSourcePincode(StringFilter sourcePincode) {
        this.sourcePincode = sourcePincode;
    }

    public StringFilter getDestinationPincode() {
        return destinationPincode;
    }

    public void setDestinationPincode(StringFilter destinationPincode) {
        this.destinationPincode = destinationPincode;
    }

    @Override
    public String toString() {
        return "SourceDestinationMappingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sourcePincode != null ? "sourcePincode=" + sourcePincode + ", " : "") +
                (destinationPincode != null ? "destinationPincode=" + destinationPincode + ", " : "") +
            "}";
    }

}
