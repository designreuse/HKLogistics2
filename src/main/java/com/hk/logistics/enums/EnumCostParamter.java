package com.hk.logistics.enums;

public enum EnumCostParamter {

	BASE_WEIGHT("baseWeight"),
	BASE_COST("baseCost");
	
	private String name;

	private EnumCostParamter(String name) {
		this.name = name;
	}
	
	public String getName() {
        return name;
    }
	
}
