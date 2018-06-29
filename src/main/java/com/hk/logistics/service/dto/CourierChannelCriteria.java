package com.hk.logistics.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the CourierChannel entity. This class is used in CourierChannelResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /courier-channels?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourierChannelCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter channelId;

    private LongFilter courierId;

    public CourierChannelCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getChannelId() {
        return channelId;
    }

    public void setChannelId(LongFilter channelId) {
        this.channelId = channelId;
    }

    public LongFilter getCourierId() {
        return courierId;
    }

    public void setCourierId(LongFilter courierId) {
        this.courierId = courierId;
    }

    @Override
    public String toString() {
        return "CourierChannelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (channelId != null ? "channelId=" + channelId + ", " : "") +
                (courierId != null ? "courierId=" + courierId + ", " : "") +
            "}";
    }

}
