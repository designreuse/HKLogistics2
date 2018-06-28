package com.hk.logistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PincodeRegionZone.
 */
@Entity
@Table(name = "pincode_region_zone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pincoderegionzone")
public class PincodeRegionZone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("")
    private RegionType regionType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private CourierGroup courierGroup;

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

    public RegionType getRegionType() {
        return regionType;
    }

    public PincodeRegionZone regionType(RegionType regionType) {
        this.regionType = regionType;
        return this;
    }

    public void setRegionType(RegionType regionType) {
        this.regionType = regionType;
    }

    public CourierGroup getCourierGroup() {
        return courierGroup;
    }

    public PincodeRegionZone courierGroup(CourierGroup courierGroup) {
        this.courierGroup = courierGroup;
        return this;
    }

    public void setCourierGroup(CourierGroup courierGroup) {
        this.courierGroup = courierGroup;
    }

    public SourceDestinationMapping getSourceDestinationMapping() {
        return sourceDestinationMapping;
    }

    public PincodeRegionZone sourceDestinationMapping(SourceDestinationMapping sourceDestinationMapping) {
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
        PincodeRegionZone pincodeRegionZone = (PincodeRegionZone) o;
        if (pincodeRegionZone.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pincodeRegionZone.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PincodeRegionZone{" +
            "id=" + getId() +
            "}";
    }
}
