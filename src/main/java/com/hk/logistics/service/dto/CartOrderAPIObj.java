package com.hk.logistics.service.dto;



import java.util.ArrayList;
import java.util.List;

public class CartOrderAPIObj extends JSONObject {

    private String pincode;
    private List<StoreVariantAPIObj> svObjList;
    private boolean codAllowed = true;
    //private boolean multipleRTOs = false;
    private boolean courierAvailable = true;

    public CartOrderAPIObj() {
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public List<StoreVariantAPIObj> getSvObjList() {
        return svObjList;
    }

    public void setSvObjList(List<StoreVariantAPIObj> svObjList) {
        this.svObjList = svObjList;
    }

    public boolean isCodAllowed() {
        return codAllowed;
    }

    public void setCodAllowed(boolean codAllowed) {
        this.codAllowed = codAllowed;
    }

    /*public boolean isMultipleRTOs() {
        return multipleRTOs;
    }

    public void setMultipleRTOs(boolean multipleRTOs) {
        this.multipleRTOs = multipleRTOs;
    }*/

    public boolean isCourierAvailable() {
        return courierAvailable;
    }

    public void setCourierAvailable(boolean courierAvailable) {
        this.courierAvailable = courierAvailable;
    }

    @Override
    public List<String> getKeys() {
        List<String> keyList = new ArrayList<String>(20);

        keyList.add(DtoJsonConstants.PINCODE);
        keyList.add(DtoJsonConstants.VARIANT_SERVICEABILITY_DETAILS);
        keyList.add(DtoJsonConstants.COD_ALLOWED);
        //keyList.add(DtoJsonConstants.MULTIPLE_RTOS);
        keyList.add(DtoJsonConstants.COURIER_AVAILIABLE);
        return keyList;
    }

    @Override
    public List<Object> getValues() {
        List<Object> valueList = new ArrayList<Object>(20);

        valueList.add(this.pincode);
        valueList.add(this.svObjList);
        valueList.add(this.codAllowed);
        //valueList.add(this.multipleRTOs);
        valueList.add(this.courierAvailable);
        return valueList;
    }
}