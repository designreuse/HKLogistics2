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
 * Criteria class for the ProductVariant entity. This class is used in ProductVariantResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /product-variants?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductVariantCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter variantId;

    private BooleanFilter serviceable;

    private StringFilter pincode;

    public ProductVariantCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVariantId() {
        return variantId;
    }

    public void setVariantId(StringFilter variantId) {
        this.variantId = variantId;
    }

    public BooleanFilter getServiceable() {
        return serviceable;
    }

    public void setServiceable(BooleanFilter serviceable) {
        this.serviceable = serviceable;
    }

    public StringFilter getPincode() {
        return pincode;
    }

    public void setPincode(StringFilter pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "ProductVariantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (variantId != null ? "variantId=" + variantId + ", " : "") +
                (serviceable != null ? "serviceable=" + serviceable + ", " : "") +
                (pincode != null ? "pincode=" + pincode + ", " : "") +
            "}";
    }

}
