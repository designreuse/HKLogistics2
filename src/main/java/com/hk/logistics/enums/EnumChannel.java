package com.hk.logistics.enums;

public enum EnumChannel {

	HK(1,"HK"),
	MP(2,"MP"),
	BRIGHT(3,"Bright");
	
	private String name;
    private int id;

    EnumChannel(int id, String name) {
        this.name = name;
        this.id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    
}
