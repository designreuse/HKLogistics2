package com.hk.logistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hk.logistics.util.AwbExcelUtil;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Pincode.
 */
@Entity
@Table(name = "pincode")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pincode")
public class Pincode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "pincode", nullable = false)
    private String pincode;

    @Column(name = "region")
    private String region;

    @Column(name = "locality")
    private String locality;

    @Column(name = "last_mile_cost")
    private Double lastMileCost;

    @Column(name = "tier")
    private String tier;

    @ManyToOne
    @JsonIgnoreProperties("")
    private City city;

    @ManyToOne
    @JsonIgnoreProperties("")
    private State state;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Zone zone;

    @ManyToOne
    @JsonIgnoreProperties("pinCodes")
    private Hub hub;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPincode() {
        return pincode;
    }

    public Pincode pincode(String pincode) {
        this.pincode = pincode;
        return this;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getRegion() {
        return region;
    }

    public Pincode region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocality() {
        return locality;
    }

    public Pincode locality(String locality) {
        this.locality = locality;
        return this;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Double getLastMileCost() {
        return lastMileCost;
    }

    public Pincode lastMileCost(Double lastMileCost) {
        this.lastMileCost = lastMileCost;
        return this;
    }

    public void setLastMileCost(Double lastMileCost) {
        this.lastMileCost = lastMileCost;
    }

    public String getTier() {
        return tier;
    }

    public Pincode tier(String tier) {
        this.tier = tier;
        return this;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public City getCity() {
        return city;
    }

    public Pincode city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public Pincode state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Zone getZone() {
        return zone;
    }

    public Pincode zone(Zone zone) {
        this.zone = zone;
        return this;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Hub getHub() {
        return hub;
    }

    public Pincode hub(Hub hub) {
        this.hub = hub;
        return this;
    }

    public void setHub(Hub hub) {
        this.hub = hub;
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
        Pincode pincode = (Pincode) o;
        if (pincode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pincode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pincode{" +
            "id=" + getId() +
            ", pincode='" + getPincode() + "'" +
            ", region='" + getRegion() + "'" +
            ", locality='" + getLocality() + "'" +
            ", lastMileCost=" + getLastMileCost() +
            ", tier='" + getTier() + "'" +
            "}";
    }
}
