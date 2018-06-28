package com.hk.logistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CourierGroup.
 */
@Entity
@Table(name = "courier_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "couriergroup")
public class CourierGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "courierGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Courier> couriers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CourierGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Courier> getCouriers() {
        return couriers;
    }

    public CourierGroup couriers(Set<Courier> couriers) {
        this.couriers = couriers;
        return this;
    }

    public CourierGroup addCourier(Courier courier) {
        this.couriers.add(courier);
        courier.setCourierGroup(this);
        return this;
    }

    public CourierGroup removeCourier(Courier courier) {
        this.couriers.remove(courier);
        courier.setCourierGroup(null);
        return this;
    }

    public void setCouriers(Set<Courier> couriers) {
        this.couriers = couriers;
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
        CourierGroup courierGroup = (CourierGroup) o;
        if (courierGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courierGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourierGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
