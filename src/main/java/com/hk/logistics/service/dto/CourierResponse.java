package com.hk.logistics.service.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class CourierResponse extends AbstractBaseResponse {

    public CourierResponse(Long storeId){
        super(storeId);
    }

    @JsonProperty(DtoJsonConstants.COURIER_LIST)
    private List<String> courierName;

    public List<String> getCourierName() {
        return courierName;
    }

    public void setCourierName(List<String> courierName) {
        this.courierName = courierName;
    }
}
