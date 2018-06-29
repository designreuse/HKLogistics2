package com.hk.logistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A VendorWHCourierMapping.
 */
@Entity
@Table(name = "vendor_wh_courier_mapping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "vendorwhcouriermapping")
public class VendorWHCourierMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "warehouse")
    private Long warehouse;

    @ManyToOne
    @JsonIgnoreProperties("vendorWHCourierMappings")
    private Courier courier;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public VendorWHCourierMapping active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getVendor() {
        return vendor;
    }

    public VendorWHCourierMapping vendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Long getWarehouse() {
        return warehouse;
    }

    public VendorWHCourierMapping warehouse(Long warehouse) {
        this.warehouse = warehouse;
        return this;
    }

    public void setWarehouse(Long warehouse) {
        this.warehouse = warehouse;
    }

    public Courier getCourier() {
        return courier;
    }

    public VendorWHCourierMapping courier(Courier courier) {
        this.courier = courier;
        return this;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
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
        VendorWHCourierMapping vendorWHCourierMapping = (VendorWHCourierMapping) o;
        if (vendorWHCourierMapping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vendorWHCourierMapping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VendorWHCourierMapping{" +
            "id=" + getId() +
            ", active='" + isActive() + "'" +
            ", vendor='" + getVendor() + "'" +
            ", warehouse=" + getWarehouse() +
            "}";
    }
}
