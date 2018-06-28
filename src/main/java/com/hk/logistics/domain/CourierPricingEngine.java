package com.hk.logistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CourierPricingEngine.
 */
@Entity
@Table(name = "courier_pricing_engine")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "courierpricingengine")
public class CourierPricingEngine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_base_wt", nullable = false)
    private Double firstBaseWt;

    @NotNull
    @Column(name = "first_base_cost", nullable = false)
    private Double firstBaseCost;

    @Column(name = "second_base_wt")
    private Double secondBaseWt;

    @Column(name = "second_base_cost")
    private Double secondBaseCost;

    @NotNull
    @Column(name = "additional_wt", nullable = false)
    private Double additionalWt;

    @NotNull
    @Column(name = "additional_cost", nullable = false)
    private Double additionalCost;

    @Column(name = "fuel_surcharge")
    private Double fuelSurcharge;

    @Column(name = "min_cod_charges")
    private Double minCodCharges;

    @Column(name = "cod_cutoff_amount")
    private Double codCutoffAmount;

    @Column(name = "variable_cod_charges")
    private Double variableCodCharges;

    @Column(name = "valid_upto")
    private LocalDate validUpto;

    @Column(name = "cost_parameters")
    private String costParameters;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Courier courier;

    @ManyToOne
    @JsonIgnoreProperties("")
    private RegionType regionType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFirstBaseWt() {
        return firstBaseWt;
    }

    public CourierPricingEngine firstBaseWt(Double firstBaseWt) {
        this.firstBaseWt = firstBaseWt;
        return this;
    }

    public void setFirstBaseWt(Double firstBaseWt) {
        this.firstBaseWt = firstBaseWt;
    }

    public Double getFirstBaseCost() {
        return firstBaseCost;
    }

    public CourierPricingEngine firstBaseCost(Double firstBaseCost) {
        this.firstBaseCost = firstBaseCost;
        return this;
    }

    public void setFirstBaseCost(Double firstBaseCost) {
        this.firstBaseCost = firstBaseCost;
    }

    public Double getSecondBaseWt() {
        return secondBaseWt;
    }

    public CourierPricingEngine secondBaseWt(Double secondBaseWt) {
        this.secondBaseWt = secondBaseWt;
        return this;
    }

    public void setSecondBaseWt(Double secondBaseWt) {
        this.secondBaseWt = secondBaseWt;
    }

    public Double getSecondBaseCost() {
        return secondBaseCost;
    }

    public CourierPricingEngine secondBaseCost(Double secondBaseCost) {
        this.secondBaseCost = secondBaseCost;
        return this;
    }

    public void setSecondBaseCost(Double secondBaseCost) {
        this.secondBaseCost = secondBaseCost;
    }

    public Double getAdditionalWt() {
        return additionalWt;
    }

    public CourierPricingEngine additionalWt(Double additionalWt) {
        this.additionalWt = additionalWt;
        return this;
    }

    public void setAdditionalWt(Double additionalWt) {
        this.additionalWt = additionalWt;
    }

    public Double getAdditionalCost() {
        return additionalCost;
    }

    public CourierPricingEngine additionalCost(Double additionalCost) {
        this.additionalCost = additionalCost;
        return this;
    }

    public void setAdditionalCost(Double additionalCost) {
        this.additionalCost = additionalCost;
    }

    public Double getFuelSurcharge() {
        return fuelSurcharge;
    }

    public CourierPricingEngine fuelSurcharge(Double fuelSurcharge) {
        this.fuelSurcharge = fuelSurcharge;
        return this;
    }

    public void setFuelSurcharge(Double fuelSurcharge) {
        this.fuelSurcharge = fuelSurcharge;
    }

    public Double getMinCodCharges() {
        return minCodCharges;
    }

    public CourierPricingEngine minCodCharges(Double minCodCharges) {
        this.minCodCharges = minCodCharges;
        return this;
    }

    public void setMinCodCharges(Double minCodCharges) {
        this.minCodCharges = minCodCharges;
    }

    public Double getCodCutoffAmount() {
        return codCutoffAmount;
    }

    public CourierPricingEngine codCutoffAmount(Double codCutoffAmount) {
        this.codCutoffAmount = codCutoffAmount;
        return this;
    }

    public void setCodCutoffAmount(Double codCutoffAmount) {
        this.codCutoffAmount = codCutoffAmount;
    }

    public Double getVariableCodCharges() {
        return variableCodCharges;
    }

    public CourierPricingEngine variableCodCharges(Double variableCodCharges) {
        this.variableCodCharges = variableCodCharges;
        return this;
    }

    public void setVariableCodCharges(Double variableCodCharges) {
        this.variableCodCharges = variableCodCharges;
    }

    public LocalDate getValidUpto() {
        return validUpto;
    }

    public CourierPricingEngine validUpto(LocalDate validUpto) {
        this.validUpto = validUpto;
        return this;
    }

    public void setValidUpto(LocalDate validUpto) {
        this.validUpto = validUpto;
    }

    public String getCostParameters() {
        return costParameters;
    }

    public CourierPricingEngine costParameters(String costParameters) {
        this.costParameters = costParameters;
        return this;
    }

    public void setCostParameters(String costParameters) {
        this.costParameters = costParameters;
    }

    public Courier getCourier() {
        return courier;
    }

    public CourierPricingEngine courier(Courier courier) {
        this.courier = courier;
        return this;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public RegionType getRegionType() {
        return regionType;
    }

    public CourierPricingEngine regionType(RegionType regionType) {
        this.regionType = regionType;
        return this;
    }

    public void setRegionType(RegionType regionType) {
        this.regionType = regionType;
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
        CourierPricingEngine courierPricingEngine = (CourierPricingEngine) o;
        if (courierPricingEngine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courierPricingEngine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourierPricingEngine{" +
            "id=" + getId() +
            ", firstBaseWt=" + getFirstBaseWt() +
            ", firstBaseCost=" + getFirstBaseCost() +
            ", secondBaseWt=" + getSecondBaseWt() +
            ", secondBaseCost=" + getSecondBaseCost() +
            ", additionalWt=" + getAdditionalWt() +
            ", additionalCost=" + getAdditionalCost() +
            ", fuelSurcharge=" + getFuelSurcharge() +
            ", minCodCharges=" + getMinCodCharges() +
            ", codCutoffAmount=" + getCodCutoffAmount() +
            ", variableCodCharges=" + getVariableCodCharges() +
            ", validUpto='" + getValidUpto() + "'" +
            ", costParameters='" + getCostParameters() + "'" +
            "}";
    }
}
