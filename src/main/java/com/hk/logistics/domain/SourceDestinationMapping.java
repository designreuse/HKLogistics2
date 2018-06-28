package com.hk.logistics.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SourceDestinationMapping.
 */
@Entity
@Table(name = "source_destination_mapping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sourcedestinationmapping")
public class SourceDestinationMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "source_pincode", nullable = false)
    private String sourcePincode;

    @NotNull
    @Column(name = "destination_pincode", nullable = false)
    private String destinationPincode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourcePincode() {
        return sourcePincode;
    }

    public SourceDestinationMapping sourcePincode(String sourcePincode) {
        this.sourcePincode = sourcePincode;
        return this;
    }

    public void setSourcePincode(String sourcePincode) {
        this.sourcePincode = sourcePincode;
    }

    public String getDestinationPincode() {
        return destinationPincode;
    }

    public SourceDestinationMapping destinationPincode(String destinationPincode) {
        this.destinationPincode = destinationPincode;
        return this;
    }

    public void setDestinationPincode(String destinationPincode) {
        this.destinationPincode = destinationPincode;
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
        SourceDestinationMapping sourceDestinationMapping = (SourceDestinationMapping) o;
        if (sourceDestinationMapping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sourceDestinationMapping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SourceDestinationMapping{" +
            "id=" + getId() +
            ", sourcePincode='" + getSourcePincode() + "'" +
            ", destinationPincode='" + getDestinationPincode() + "'" +
            "}";
    }
}
