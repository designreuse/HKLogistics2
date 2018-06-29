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
 * A PincodeCourierMapping.
 */
@Entity
@Table(name = "pincode_courier_mapping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pincodecouriermapping")
public class PincodeCourierMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "routing_code")
    private String routingCode;

    @NotNull
    @Column(name = "applicable_for_cheapest_courier", nullable = false)
    private Boolean applicableForCheapestCourier;

    @Column(name = "estimated_delivery_days")
    private Double estimatedDeliveryDays;

    @NotNull
    @Column(name = "pickup_available", nullable = false)
    private Boolean pickupAvailable;

    @NotNull
    @Column(name = "prepaid_air", nullable = false)
    private Boolean prepaidAir;

    @NotNull
    @Column(name = "prepaid_ground", nullable = false)
    private Boolean prepaidGround;

    @NotNull
    @Column(name = "cod_air", nullable = false)
    private Boolean codAir;

    @NotNull
    @Column(name = "cod_ground", nullable = false)
    private Boolean codGround;

    @NotNull
    @Column(name = "reverse_air", nullable = false)
    private Boolean reverseAir;

    @NotNull
    @Column(name = "reverse_ground", nullable = false)
    private Boolean reverseGround;

    @NotNull
    @Column(name = "card_on_delivery_air", nullable = false)
    private Boolean cardOnDeliveryAir;

    @NotNull
    @Column(name = "card_on_delivery_ground", nullable = false)
    private Boolean cardOnDeliveryGround;

    @Column(name = "delivery_type_one")
    private Boolean deliveryTypeOne;

    @Column(name = "delivery_type_two")
    private Boolean deliveryTypeTwo;

    @ManyToOne
    @JsonIgnoreProperties("")
    private VendorWHCourierMapping vendorWHCourierMapping;

    @ManyToOne
    @JsonIgnoreProperties("")
    private SourceDestinationMapping sourceDestinationMapping;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoutingCode() {
        return routingCode;
    }

    public PincodeCourierMapping routingCode(String routingCode) {
        this.routingCode = routingCode;
        return this;
    }

    public void setRoutingCode(String routingCode) {
        this.routingCode = routingCode;
    }

    public Boolean isApplicableForCheapestCourier() {
        return applicableForCheapestCourier;
    }

    public PincodeCourierMapping applicableForCheapestCourier(Boolean applicableForCheapestCourier) {
        this.applicableForCheapestCourier = applicableForCheapestCourier;
        return this;
    }

    public void setApplicableForCheapestCourier(Boolean applicableForCheapestCourier) {
        this.applicableForCheapestCourier = applicableForCheapestCourier;
    }

    public Double getEstimatedDeliveryDays() {
        return estimatedDeliveryDays;
    }

    public PincodeCourierMapping estimatedDeliveryDays(Double estimatedDeliveryDays) {
        this.estimatedDeliveryDays = estimatedDeliveryDays;
        return this;
    }

    public void setEstimatedDeliveryDays(Double estimatedDeliveryDays) {
        this.estimatedDeliveryDays = estimatedDeliveryDays;
    }

    public Boolean isPickupAvailable() {
        return pickupAvailable;
    }

    public PincodeCourierMapping pickupAvailable(Boolean pickupAvailable) {
        this.pickupAvailable = pickupAvailable;
        return this;
    }

    public void setPickupAvailable(Boolean pickupAvailable) {
        this.pickupAvailable = pickupAvailable;
    }

    public Boolean isPrepaidAir() {
        return prepaidAir;
    }

    public PincodeCourierMapping prepaidAir(Boolean prepaidAir) {
        this.prepaidAir = prepaidAir;
        return this;
    }

    public void setPrepaidAir(Boolean prepaidAir) {
        this.prepaidAir = prepaidAir;
    }

    public Boolean isPrepaidGround() {
        return prepaidGround;
    }

    public PincodeCourierMapping prepaidGround(Boolean prepaidGround) {
        this.prepaidGround = prepaidGround;
        return this;
    }

    public void setPrepaidGround(Boolean prepaidGround) {
        this.prepaidGround = prepaidGround;
    }

    public Boolean isCodAir() {
        return codAir;
    }

    public PincodeCourierMapping codAir(Boolean codAir) {
        this.codAir = codAir;
        return this;
    }

    public void setCodAir(Boolean codAir) {
        this.codAir = codAir;
    }

    public Boolean isCodGround() {
        return codGround;
    }

    public PincodeCourierMapping codGround(Boolean codGround) {
        this.codGround = codGround;
        return this;
    }

    public void setCodGround(Boolean codGround) {
        this.codGround = codGround;
    }

    public Boolean isReverseAir() {
        return reverseAir;
    }

    public PincodeCourierMapping reverseAir(Boolean reverseAir) {
        this.reverseAir = reverseAir;
        return this;
    }

    public void setReverseAir(Boolean reverseAir) {
        this.reverseAir = reverseAir;
    }

    public Boolean isReverseGround() {
        return reverseGround;
    }

    public PincodeCourierMapping reverseGround(Boolean reverseGround) {
        this.reverseGround = reverseGround;
        return this;
    }

    public void setReverseGround(Boolean reverseGround) {
        this.reverseGround = reverseGround;
    }

    public Boolean isCardOnDeliveryAir() {
        return cardOnDeliveryAir;
    }

    public PincodeCourierMapping cardOnDeliveryAir(Boolean cardOnDeliveryAir) {
        this.cardOnDeliveryAir = cardOnDeliveryAir;
        return this;
    }

    public void setCardOnDeliveryAir(Boolean cardOnDeliveryAir) {
        this.cardOnDeliveryAir = cardOnDeliveryAir;
    }

    public Boolean isCardOnDeliveryGround() {
        return cardOnDeliveryGround;
    }

    public PincodeCourierMapping cardOnDeliveryGround(Boolean cardOnDeliveryGround) {
        this.cardOnDeliveryGround = cardOnDeliveryGround;
        return this;
    }

    public void setCardOnDeliveryGround(Boolean cardOnDeliveryGround) {
        this.cardOnDeliveryGround = cardOnDeliveryGround;
    }

    public Boolean isDeliveryTypeOne() {
        return deliveryTypeOne;
    }

    public PincodeCourierMapping deliveryTypeOne(Boolean deliveryTypeOne) {
        this.deliveryTypeOne = deliveryTypeOne;
        return this;
    }

    public void setDeliveryTypeOne(Boolean deliveryTypeOne) {
        this.deliveryTypeOne = deliveryTypeOne;
    }

    public Boolean isDeliveryTypeTwo() {
        return deliveryTypeTwo;
    }

    public PincodeCourierMapping deliveryTypeTwo(Boolean deliveryTypeTwo) {
        this.deliveryTypeTwo = deliveryTypeTwo;
        return this;
    }

    public void setDeliveryTypeTwo(Boolean deliveryTypeTwo) {
        this.deliveryTypeTwo = deliveryTypeTwo;
    }

    public VendorWHCourierMapping getVendorWHCourierMapping() {
        return vendorWHCourierMapping;
    }

    public PincodeCourierMapping vendorWHCourierMapping(VendorWHCourierMapping vendorWHCourierMapping) {
        this.vendorWHCourierMapping = vendorWHCourierMapping;
        return this;
    }

    public void setVendorWHCourierMapping(VendorWHCourierMapping vendorWHCourierMapping) {
        this.vendorWHCourierMapping = vendorWHCourierMapping;
    }

    public SourceDestinationMapping getSourceDestinationMapping() {
        return sourceDestinationMapping;
    }

    public PincodeCourierMapping sourceDestinationMapping(SourceDestinationMapping sourceDestinationMapping) {
        this.sourceDestinationMapping = sourceDestinationMapping;
        return this;
    }

    public void setSourceDestinationMapping(SourceDestinationMapping sourceDestinationMapping) {
        this.sourceDestinationMapping = sourceDestinationMapping;
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
        PincodeCourierMapping pincodeCourierMapping = (PincodeCourierMapping) o;
        if (pincodeCourierMapping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pincodeCourierMapping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PincodeCourierMapping{" +
            "id=" + getId() +
            ", routingCode='" + getRoutingCode() + "'" +
            ", applicableForCheapestCourier='" + isApplicableForCheapestCourier() + "'" +
            ", estimatedDeliveryDays=" + getEstimatedDeliveryDays() +
            ", pickupAvailable='" + isPickupAvailable() + "'" +
            ", prepaidAir='" + isPrepaidAir() + "'" +
            ", prepaidGround='" + isPrepaidGround() + "'" +
            ", codAir='" + isCodAir() + "'" +
            ", codGround='" + isCodGround() + "'" +
            ", reverseAir='" + isReverseAir() + "'" +
            ", reverseGround='" + isReverseGround() + "'" +
            ", cardOnDeliveryAir='" + isCardOnDeliveryAir() + "'" +
            ", cardOnDeliveryGround='" + isCardOnDeliveryGround() + "'" +
            ", deliveryTypeOne='" + isDeliveryTypeOne() + "'" +
            ", deliveryTypeTwo='" + isDeliveryTypeTwo() + "'" +
            "}";
    }
}
