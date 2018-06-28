package com.hk.logistics.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PincodeRegionZone entity.
 */
public class PincodeRegionZoneDTO implements Serializable {

    private Long id;

    private Long regionTypeId;

    private String regionTypeName;

    private Long courierGroupId;

    private String courierGroupName;

    private Long sourceDestinationMappingId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegionTypeId() {
        return regionTypeId;
    }

    public void setRegionTypeId(Long regionTypeId) {
        this.regionTypeId = regionTypeId;
    }

    public String getRegionTypeName() {
        return regionTypeName;
    }

    public void setRegionTypeName(String regionTypeName) {
        this.regionTypeName = regionTypeName;
    }

    public Long getCourierGroupId() {
        return courierGroupId;
    }

    public void setCourierGroupId(Long courierGroupId) {
        this.courierGroupId = courierGroupId;
    }

    public String getCourierGroupName() {
        return courierGroupName;
    }

    public void setCourierGroupName(String courierGroupName) {
        this.courierGroupName = courierGroupName;
    }

    public Long getSourceDestinationMappingId() {
        return sourceDestinationMappingId;
    }

    public void setSourceDestinationMappingId(Long sourceDestinationMappingId) {
        this.sourceDestinationMappingId = sourceDestinationMappingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PincodeRegionZoneDTO pincodeRegionZoneDTO = (PincodeRegionZoneDTO) o;
        if (pincodeRegionZoneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pincodeRegionZoneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PincodeRegionZoneDTO{" +
            "id=" + getId() +
            ", regionType=" + getRegionTypeId() +
            ", regionType='" + getRegionTypeName() + "'" +
            ", courierGroup=" + getCourierGroupId() +
            ", courierGroup='" + getCourierGroupName() + "'" +
            ", sourceDestinationMapping=" + getSourceDestinationMappingId() +
            "}";
    }
}
