package com.hk.logistics.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductVariant.
 */
@Entity
@Table(name = "product_variant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productvariant")
public class ProductVariant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "variant_id", nullable = false)
    private String variantId;

    @Column(name = "serviceable")
    private Boolean serviceable;

    @Column(name = "pincode")
    private String pincode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVariantId() {
        return variantId;
    }

    public ProductVariant variantId(String variantId) {
        this.variantId = variantId;
        return this;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public Boolean isServiceable() {
        return serviceable;
    }

    public ProductVariant serviceable(Boolean serviceable) {
        this.serviceable = serviceable;
        return this;
    }

    public void setServiceable(Boolean serviceable) {
        this.serviceable = serviceable;
    }

    public String getPincode() {
        return pincode;
    }

    public ProductVariant pincode(String pincode) {
        this.pincode = pincode;
        return this;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductVariant productVariant = (ProductVariant) o;
        if (productVariant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productVariant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductVariant{" +
            "id=" + getId() +
            ", variantId='" + getVariantId() + "'" +
            ", serviceable='" + isServiceable() + "'" +
            ", pincode='" + getPincode() + "'" +
            "}";
    }
}
