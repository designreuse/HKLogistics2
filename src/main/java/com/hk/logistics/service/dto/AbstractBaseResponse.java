package com.hk.logistics.service.dto;

import java.util.List;


public class AbstractBaseResponse extends AbstractGenericResponse {

    protected Long storeId;


    protected AbstractBaseResponse(Long storeId) {
        this.storeId = storeId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }


    @Override
    protected List<String> getKeys() {
        List<String> keyList = super.getKeys();

        keyList.add(DtoJsonConstants.STORE_ID);
        return keyList;
    }

    @Override
    protected List<Object> getValues() {
        List<Object> valueList = super.getValues();

        valueList.add(this.storeId);
        return valueList;
    }
}