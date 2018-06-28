package com.hk.logistics.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the CourierPricingEngine entity. This class is used in CourierPricingEngineResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /courier-pricing-engines?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourierPricingEngineCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private DoubleFilter firstBaseWt;

    private DoubleFilter firstBaseCost;

    private DoubleFilter secondBaseWt;

    private DoubleFilter secondBaseCost;

    private DoubleFilter additionalWt;

    private DoubleFilter additionalCost;

    private DoubleFilter fuelSurcharge;

    private DoubleFilter minCodCharges;

    private DoubleFilter codCutoffAmount;

    private DoubleFilter variableCodCharges;

    private LocalDateFilter validUpto;

    private StringFilter costParameters;

    private LongFilter courierId;

    private LongFilter regionTypeId;

    public CourierPricingEngineCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getFirstBaseWt() {
        return firstBaseWt;
    }

    public void setFirstBaseWt(DoubleFilter firstBaseWt) {
        this.firstBaseWt = firstBaseWt;
    }

    public DoubleFilter getFirstBaseCost() {
        return firstBaseCost;
    }

    public void setFirstBaseCost(DoubleFilter firstBaseCost) {
        this.firstBaseCost = firstBaseCost;
    }

    public DoubleFilter getSecondBaseWt() {
        return secondBaseWt;
    }

    public void setSecondBaseWt(DoubleFilter secondBaseWt) {
        this.secondBaseWt = secondBaseWt;
    }

    public DoubleFilter getSecondBaseCost() {
        return secondBaseCost;
    }

    public void setSecondBaseCost(DoubleFilter secondBaseCost) {
        this.secondBaseCost = secondBaseCost;
    }

    public DoubleFilter getAdditionalWt() {
        return additionalWt;
    }

    public void setAdditionalWt(DoubleFilter additionalWt) {
        this.additionalWt = additionalWt;
    }

    public DoubleFilter getAdditionalCost() {
        return additionalCost;
    }

    public void setAdditionalCost(DoubleFilter additionalCost) {
        this.additionalCost = additionalCost;
    }

    public DoubleFilter getFuelSurcharge() {
        return fuelSurcharge;
    }

    public void setFuelSurcharge(DoubleFilter fuelSurcharge) {
        this.fuelSurcharge = fuelSurcharge;
    }

    public DoubleFilter getMinCodCharges() {
        return minCodCharges;
    }

    public void setMinCodCharges(DoubleFilter minCodCharges) {
        this.minCodCharges = minCodCharges;
    }

    public DoubleFilter getCodCutoffAmount() {
        return codCutoffAmount;
    }

    public void setCodCutoffAmount(DoubleFilter codCutoffAmount) {
        this.codCutoffAmount = codCutoffAmount;
    }

    public DoubleFilter getVariableCodCharges() {
        return variableCodCharges;
    }

    public void setVariableCodCharges(DoubleFilter variableCodCharges) {
        this.variableCodCharges = variableCodCharges;
    }

    public LocalDateFilter getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(LocalDateFilter validUpto) {
        this.validUpto = validUpto;
    }

    public StringFilter getCostParameters() {
        return costParameters;
    }

    public void setCostParameters(StringFilter costParameters) {
        this.costParameters = costParameters;
    }

    public LongFilter getCourierId() {
        return courierId;
    }

    public void setCourierId(LongFilter courierId) {
        this.courierId = courierId;
    }

    public LongFilter getRegionTypeId() {
        return regionTypeId;
    }

    public void setRegionTypeId(LongFilter regionTypeId) {
        this.regionTypeId = regionTypeId;
    }

    @Override
    public String toString() {
        return "CourierPricingEngineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (firstBaseWt != null ? "firstBaseWt=" + firstBaseWt + ", " : "") +
                (firstBaseCost != null ? "firstBaseCost=" + firstBaseCost + ", " : "") +
                (secondBaseWt != null ? "secondBaseWt=" + secondBaseWt + ", " : "") +
                (secondBaseCost != null ? "secondBaseCost=" + secondBaseCost + ", " : "") +
                (additionalWt != null ? "additionalWt=" + additionalWt + ", " : "") +
                (additionalCost != null ? "additionalCost=" + additionalCost + ", " : "") +
                (fuelSurcharge != null ? "fuelSurcharge=" + fuelSurcharge + ", " : "") +
                (minCodCharges != null ? "minCodCharges=" + minCodCharges + ", " : "") +
                (codCutoffAmount != null ? "codCutoffAmount=" + codCutoffAmount + ", " : "") +
                (variableCodCharges != null ? "variableCodCharges=" + variableCodCharges + ", " : "") +
                (validUpto != null ? "validUpto=" + validUpto + ", " : "") +
                (costParameters != null ? "costParameters=" + costParameters + ", " : "") +
                (courierId != null ? "courierId=" + courierId + ", " : "") +
                (regionTypeId != null ? "regionTypeId=" + regionTypeId + ", " : "") +
            "}";
    }

}
