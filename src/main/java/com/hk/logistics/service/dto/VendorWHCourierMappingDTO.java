package com.hk.logistics.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VendorWHCourierMapping entity.
 */
public class VendorWHCourierMappingDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean active;

    private String vendor;

    private Long warehouse;

    private Long courierChannelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Long getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Long warehouse) {
        this.warehouse = warehouse;
    }

    public Long getCourierChannelId() {
        return courierChannelId;
    }

    public void setCourierChannelId(Long courierChannelId) {
        this.courierChannelId = courierChannelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VendorWHCourierMappingDTO vendorWHCourierMappingDTO = (VendorWHCourierMappingDTO) o;
        if (vendorWHCourierMappingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vendorWHCourierMappingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VendorWHCourierMappingDTO{" +
            "id=" + getId() +
            ", active='" + isActive() + "'" +
            ", vendor='" + getVendor() + "'" +
            ", warehouse=" + getWarehouse() +
            ", courierChannel=" + getCourierChannelId() +
            "}";
    }
}
