package com.hk.logistics.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SourceDestinationMapping entity.
 */
public class SourceDestinationMappingDTO implements Serializable {

    private Long id;

    @NotNull
    private String sourcePincode;

    @NotNull
    private String destinationPincode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        SourceDestinationMappingDTO sourceDestinationMappingDTO = (SourceDestinationMappingDTO) o;
        if (sourceDestinationMappingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sourceDestinationMappingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SourceDestinationMappingDTO{" +
            "id=" + getId() +
            ", sourcePincode='" + getSourcePincode() + "'" +
            ", destinationPincode='" + getDestinationPincode() + "'" +
            "}";
    }
}
