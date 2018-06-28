package com.hk.logistics.constants;

import java.util.Arrays;
import java.util.List;

import com.hk.logistics.domain.AwbStatus;


public enum EnumAwbStatus {

    Unused(10L, "Unused"),
    Attach(20L, "Attach"),
    Used(40L, "Used");

    private Long id;
    private String status;

    EnumAwbStatus(Long id, String status) {
        this.id = id;
        this.status = status;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AwbStatus getAsAwbStatus() {
        AwbStatus awbStatus = new AwbStatus();
        awbStatus.setId(this.id);
        awbStatus.setStatus(this.status);
        return awbStatus;
    }

	public static List<AwbStatus> getAllStatusExceptUnused(){
		return Arrays.asList(Attach.getAsAwbStatus(),Used.getAsAwbStatus());
	}


	public static List<AwbStatus> getAllStatusExceptUsed(){
		return Arrays.asList(Attach.getAsAwbStatus(),Unused.getAsAwbStatus());
	}
}
