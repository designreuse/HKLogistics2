package com.hk.logistics.enums;

public enum EnumWarehouse {

	HK_AQUA_WH_4_ID(701L, "HK Aqua Warehosue 4.0");

	private Long id;
	private String name;
	
	EnumWarehouse(Long id,String name) {
		this.id=id;
		this.name = name;
	}
	
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
