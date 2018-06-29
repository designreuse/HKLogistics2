package com.hk.logistics.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CourierPricingEngine entity.
 */
public class CourierPricingEngineDTO implements Serializable {

    private Long id;

    @NotNull
    private Double firstBaseWt;

    @NotNull
    private Double firstBaseCost;

    private Double secondBaseWt;

    private Double secondBaseCost;

    private Double thirdBaseWt;

    private Double thirdBaseCost;

    @NotNull
    private Double additionalWt;

    @NotNull
    private Double additionalCost;

    private Double fuelSurcharge;

    private Double minCodCharges;

    private Double codCutoffAmount;

    private Double variableCodCharges;

    private LocalDate validUpto;

    private String costParameters;

    private Long courierId;

    private String courierName;

    private Long regionTypeId;

    private String regionTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFirstBaseWt() {
        return firstBaseWt;
    }

    public void setFirstBaseWt(Double firstBaseWt) {
        this.firstBaseWt = firstBaseWt;
    }

    public Double getFirstBaseCost() {
        return firstBaseCost;
    }

    public void setFirstBaseCost(Double firstBaseCost) {
        this.firstBaseCost = firstBaseCost;
    }

    public Double getSecondBaseWt() {
        return secondBaseWt;
    }

    public void setSecondBaseWt(Double secondBaseWt) {
        this.secondBaseWt = secondBaseWt;
    }

    public Double getSecondBaseCost() {
        return secondBaseCost;
    }

    public void setSecondBaseCost(Double secondBaseCost) {
        this.secondBaseCost = secondBaseCost;
    }

    public Double getThirdBaseWt() {
        return thirdBaseWt;
    }

    public void setThirdBaseWt(Double thirdBaseWt) {
        this.thirdBaseWt = thirdBaseWt;
    }

    public Double getThirdBaseCost() {
        return thirdBaseCost;
    }

    public void setThirdBaseCost(Double thirdBaseCost) {
        this.thirdBaseCost = thirdBaseCost;
    }

    public Double getAdditionalWt() {
        return additionalWt;
    }

    public void setAdditionalWt(Double additionalWt) {
        this.additionalWt = additionalWt;
    }

    public Double getAdditionalCost() {
        return additionalCost;
    }

    public void setAdditionalCost(Double additionalCost) {
        this.additionalCost = additionalCost;
    }

    public Double getFuelSurcharge() {
        return fuelSurcharge;
    }

    public void setFuelSurcharge(Double fuelSurcharge) {
        this.fuelSurcharge = fuelSurcharge;
    }

    public Double getMinCodCharges() {
        return minCodCharges;
    }

    public void setMinCodCharges(Double minCodCharges) {
        this.minCodCharges = minCodCharges;
    }

    public Double getCodCutoffAmount() {
        return codCutoffAmount;
    }

    public void setCodCutoffAmount(Double codCutoffAmount) {
        this.codCutoffAmount = codCutoffAmount;
    }

    public Double getVariableCodCharges() {
        return variableCodCharges;
    }

    public void setVariableCodCharges(Double variableCodCharges) {
        this.variableCodCharges = variableCodCharges;
    }

    public LocalDate getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(LocalDate validUpto) {
        this.validUpto = validUpto;
    }

    public String getCostParameters() {
        return costParameters;
    }

    public void setCostParameters(String costParameters) {
        this.costParameters = costParameters;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourierPricingEngineDTO courierPricingEngineDTO = (CourierPricingEngineDTO) o;
        if (courierPricingEngineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courierPricingEngineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourierPricingEngineDTO{" +
            "id=" + getId() +
            ", firstBaseWt=" + getFirstBaseWt() +
            ", firstBaseCost=" + getFirstBaseCost() +
            ", secondBaseWt=" + getSecondBaseWt() +
            ", secondBaseCost=" + getSecondBaseCost() +
            ", thirdBaseWt=" + getThirdBaseWt() +
            ", thirdBaseCost=" + getThirdBaseCost() +
            ", additionalWt=" + getAdditionalWt() +
            ", additionalCost=" + getAdditionalCost() +
            ", fuelSurcharge=" + getFuelSurcharge() +
            ", minCodCharges=" + getMinCodCharges() +
            ", codCutoffAmount=" + getCodCutoffAmount() +
            ", variableCodCharges=" + getVariableCodCharges() +
            ", validUpto='" + getValidUpto() + "'" +
            ", costParameters='" + getCostParameters() + "'" +
            ", courier=" + getCourierId() +
            ", courier='" + getCourierName() + "'" +
            ", regionType=" + getRegionTypeId() +
            ", regionType='" + getRegionTypeName() + "'" +
            "}";
    }
}
