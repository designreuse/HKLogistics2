package com.hk.logistics.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VendorService{

	public static volatile Map<String,String> vendorShortCodes=new HashMap<>();
	
	public VendorService(){
		vendorShortCodes.put("SHVH", "122002");
	}

}
