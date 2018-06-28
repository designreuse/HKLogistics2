package com.hk.logistics.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CourierChannel entity.
 */
public class CourierChannelDTO implements Serializable {

    private Long id;

    private Long channelId;

    private String channelName;

    private Long courierId;

    private String courierName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourierChannelDTO courierChannelDTO = (CourierChannelDTO) o;
        if (courierChannelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courierChannelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourierChannelDTO{" +
            "id=" + getId() +
            ", channel=" + getChannelId() +
            ", channel='" + getChannelName() + "'" +
            ", courier=" + getCourierId() +
            ", courier='" + getCourierName() + "'" +
            "}";
    }
}
