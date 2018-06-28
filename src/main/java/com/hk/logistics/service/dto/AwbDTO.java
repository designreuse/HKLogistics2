package com.hk.logistics.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Awb entity.
 */
public class AwbDTO implements Serializable {

    private Long id;

    @NotNull
    private String awbNumber;

    @NotNull
    private String awbBarCode;

    @NotNull
    private Boolean cod;

    @NotNull
    private LocalDate createDate;

    private String returnAwbNumber;

    private String returnAwbBarCode;

    @NotNull
    private Boolean isBrightAwb;

    private Long channelId;

    private String channelName;

    private Long vendorWHCourierMappingId;

    private Long awbStatusId;

    private String awbStatusStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAwbNumber() {
        return awbNumber;
    }

    public void setAwbNumber(String awbNumber) {
        this.awbNumber = awbNumber;
    }

    public String getAwbBarCode() {
        return awbBarCode;
    }

    public void setAwbBarCode(String awbBarCode) {
        this.awbBarCode = awbBarCode;
    }

    public Boolean isCod() {
        return cod;
    }

    public void setCod(Boolean cod) {
        this.cod = cod;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getReturnAwbNumber() {
        return returnAwbNumber;
    }

    public void setReturnAwbNumber(String returnAwbNumber) {
        this.returnAwbNumber = returnAwbNumber;
    }

    public String getReturnAwbBarCode() {
        return returnAwbBarCode;
    }

    public void setReturnAwbBarCode(String returnAwbBarCode) {
        this.returnAwbBarCode = returnAwbBarCode;
    }

    public Boolean isIsBrightAwb() {
        return isBrightAwb;
    }

    public void setIsBrightAwb(Boolean isBrightAwb) {
        this.isBrightAwb = isBrightAwb;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Long getVendorWHCourierMappingId() {
        return vendorWHCourierMappingId;
    }

    public void setVendorWHCourierMappingId(Long vendorWHCourierMappingId) {
        this.vendorWHCourierMappingId = vendorWHCourierMappingId;
    }

    public Long getAwbStatusId() {
        return awbStatusId;
    }

    public void setAwbStatusId(Long awbStatusId) {
        this.awbStatusId = awbStatusId;
    }

    public String getAwbStatusStatus() {
        return awbStatusStatus;
    }

    public void setAwbStatusStatus(String awbStatusStatus) {
        this.awbStatusStatus = awbStatusStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AwbDTO awbDTO = (AwbDTO) o;
        if (awbDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), awbDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AwbDTO{" +
            "id=" + getId() +
            ", awbNumber='" + getAwbNumber() + "'" +
            ", awbBarCode='" + getAwbBarCode() + "'" +
            ", cod='" + isCod() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", returnAwbNumber='" + getReturnAwbNumber() + "'" +
            ", returnAwbBarCode='" + getReturnAwbBarCode() + "'" +
            ", isBrightAwb='" + isIsBrightAwb() + "'" +
            ", channel=" + getChannelId() +
            ", channel='" + getChannelName() + "'" +
            ", vendorWHCourierMapping=" + getVendorWHCourierMappingId() +
            ", awbStatus=" + getAwbStatusId() +
            ", awbStatus='" + getAwbStatusStatus() + "'" +
            "}";
    }
}
