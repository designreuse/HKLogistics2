package com.hk.logistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CourierChannel.
 */
@Entity
@Table(name = "courier_channel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "courierchannel")
public class CourierChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "courierChannel")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VendorWHCourierMapping> vendorWHCourierMappings = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("courierChannels")
    private Channel channel;

    @ManyToOne
    @JsonIgnoreProperties("courierChannels")
    private Courier courier;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<VendorWHCourierMapping> getVendorWHCourierMappings() {
        return vendorWHCourierMappings;
    }

    public CourierChannel vendorWHCourierMappings(Set<VendorWHCourierMapping> vendorWHCourierMappings) {
        this.vendorWHCourierMappings = vendorWHCourierMappings;
        return this;
    }

    public CourierChannel addVendorWHCourierMapping(VendorWHCourierMapping vendorWHCourierMapping) {
        this.vendorWHCourierMappings.add(vendorWHCourierMapping);
        vendorWHCourierMapping.setCourierChannel(this);
        return this;
    }

    public CourierChannel removeVendorWHCourierMapping(VendorWHCourierMapping vendorWHCourierMapping) {
        this.vendorWHCourierMappings.remove(vendorWHCourierMapping);
        vendorWHCourierMapping.setCourierChannel(null);
        return this;
    }

    public void setVendorWHCourierMappings(Set<VendorWHCourierMapping> vendorWHCourierMappings) {
        this.vendorWHCourierMappings = vendorWHCourierMappings;
    }

    public Channel getChannel() {
        return channel;
    }

    public CourierChannel channel(Channel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Courier getCourier() {
        return courier;
    }

    public CourierChannel courier(Courier courier) {
        this.courier = courier;
        return this;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
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
        CourierChannel courierChannel = (CourierChannel) o;
        if (courierChannel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courierChannel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourierChannel{" +
            "id=" + getId() +
            "}";
    }
}
