package com.hk.logistics.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProductVariant entity.
 */
public class ProductVariantDTO implements Serializable {

    private Long id;

    @NotNull
    private String variantId;

    private Boolean serviceable;

    private String pincode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public Boolean isServiceable() {
        return serviceable;
    }

    public void setServiceable(Boolean serviceable) {
        this.serviceable = serviceable;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductVariantDTO productVariantDTO = (ProductVariantDTO) o;
        if (productVariantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productVariantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductVariantDTO{" +
            "id=" + getId() +
            ", variantId='" + getVariantId() + "'" +
            ", serviceable='" + isServiceable() + "'" +
            ", pincode='" + getPincode() + "'" +
            "}";
    }
}
