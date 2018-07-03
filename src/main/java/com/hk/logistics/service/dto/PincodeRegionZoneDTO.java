package com.hk.logistics.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.poiji.annotation.ExcelCellName;

/**
 * A DTO for the PincodeRegionZone entity.
 */
public class PincodeRegionZoneDTO implements Serializable {

    private Long id;

    private Long regionTypeId;

    @ExcelCellName("REGION_TYPE")
    private String regionTypeName;

    private Long courierGroupId;

    @ExcelCellName("COURIER_GROUP")
    private String courierGroupName;

    private Long sourceDestinationMappingId;
    
    @ExcelCellName("SOURCE_PINCODE")
    private String sourcePincode;
    
    @ExcelCellName("DESTINATION_PINCODE")
    private String destinationPincode;

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

    public String getSourcePincode() {
		return sourcePincode;
	}

	public void setSourcePincode(String sourcePincode) {
		this.sourcePincode = sourcePincode;
	}

	public String getDestinationPincode() {
		return destinationPincode;
	}

	public void setDestinationPincode(String destinationPincode) {
		this.destinationPincode = destinationPincode;
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
