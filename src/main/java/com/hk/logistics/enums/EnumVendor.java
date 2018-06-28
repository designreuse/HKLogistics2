package com.hk.logistics.enums;

public enum EnumVendor {

	BLC("BLC");
	private String name;

	EnumVendor(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
