package com.hk.logistics.comparator;

import java.util.Map;

import com.hk.logistics.domain.Courier;

public class Comparator implements java.util.Comparator<Map.Entry<Courier,Long>>{

	@Override
	public int compare(Map.Entry<Courier, Long> o1, Map.Entry<Courier, Long> o2) {
		return o1.getValue().compareTo(o2.getValue());
	}

	
}
