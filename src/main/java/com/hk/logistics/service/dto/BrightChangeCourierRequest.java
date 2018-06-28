package com.hk.logistics.service.dto;

public class BrightChangeCourierRequest {
    private String warehouseFcCode;
    private Long courierId;
    private boolean cod;
    private String oldAwbNumberToPreserve;
    private boolean postShip;
    private String courierShortCode;
    private String store;
    private String channel;

    public String getWarehouseFcCode() {
        return warehouseFcCode;
    }

    public void setWarehouseFcCode(String warehouseFcCode) {
        this.warehouseFcCode = warehouseFcCode;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public boolean isCod() {
        return cod;
    }

    public void setCod(boolean cod) {
        this.cod = cod;
    }

    public String getOldAwbNumberToPreserve() {
        return oldAwbNumberToPreserve;
    }

    public void setOldAwbNumberToPreserve(String oldAwbNumberToPreserve) {
        this.oldAwbNumberToPreserve = oldAwbNumberToPreserve;
    }

    public boolean isPostShip() {
        return postShip;
    }

    public boolean getPostShip() {
        return postShip;
    }

    public void setPostShip(boolean postShip) {
        this.postShip = postShip;
    }

	public String getCourierShortCode() {
		return courierShortCode;
	}

	public void setCourierShortCode(String courierShortCode) {
		this.courierShortCode = courierShortCode;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
