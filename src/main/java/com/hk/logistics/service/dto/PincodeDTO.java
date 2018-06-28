package com.hk.logistics.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Pincode entity.
 */
public class PincodeDTO implements Serializable {

    private Long id;

    @NotNull
    private String pincode;

    private String region;

    private String locality;

    private Double lastMileCost;

    private String tier;

    private Long cityId;

    private String cityName;

    private Long stateId;

    private String stateName;

    private Long zoneId;

    private String zoneName;

    private Long hubId;

    private String hubName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Double getLastMileCost() {
        return lastMileCost;
    }

    public void setLastMileCost(Double lastMileCost) {
        this.lastMileCost = lastMileCost;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Long getHubId() {
        return hubId;
    }

    public void setHubId(Long hubId) {
        this.hubId = hubId;
    }

    public String getHubName() {
        return hubName;
    }

    public void setHubName(String hubName) {
        this.hubName = hubName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PincodeDTO pincodeDTO = (PincodeDTO) o;
        if (pincodeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pincodeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PincodeDTO{" +
            "id=" + getId() +
            ", pincode='" + getPincode() + "'" +
            ", region='" + getRegion() + "'" +
            ", locality='" + getLocality() + "'" +
            ", lastMileCost=" + getLastMileCost() +
            ", tier='" + getTier() + "'" +
            ", city=" + getCityId() +
            ", city='" + getCityName() + "'" +
            ", state=" + getStateId() +
            ", state='" + getStateName() + "'" +
            ", zone=" + getZoneId() +
            ", zone='" + getZoneName() + "'" +
            ", hub=" + getHubId() +
            ", hub='" + getHubName() + "'" +
            "}";
    }
}
