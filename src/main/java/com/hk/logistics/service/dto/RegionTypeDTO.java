package com.hk.logistics.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RegionType entity.
 */
public class RegionTypeDTO implements Serializable {

    private Long id;

    private String name;

    private Long priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegionTypeDTO regionTypeDTO = (RegionTypeDTO) o;
        if (regionTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regionTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegionTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", priority=" + getPriority() +
            "}";
    }
}
