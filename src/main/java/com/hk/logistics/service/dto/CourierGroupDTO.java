package com.hk.logistics.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CourierGroup entity.
 */
public class CourierGroupDTO implements Serializable {

    private Long id;

    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourierGroupDTO courierGroupDTO = (CourierGroupDTO) o;
        if (courierGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courierGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourierGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
