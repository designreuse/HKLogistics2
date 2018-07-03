package com.hk.logistics.service;

import com.hk.logistics.service.dto.WarehouseDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WarehouseService{

	public static volatile Map<Long, WarehouseDTO> warehouseMap=new HashMap<>();
	
	public WarehouseService(){
		WarehouseDTO warehouseDTO=new WarehouseDTO();
		warehouseDTO.setFulfilmentCenterCode("GCFA-AB");
		warehouseDTO.setId(701L);
		warehouseDTO.setPincode("122002");
		warehouseMap.put(701L, warehouseDTO);
		WarehouseDTO warehouseDTO1=new WarehouseDTO();
		warehouseDTO1.setFulfilmentCenterCode("GCFA-AB");
		warehouseDTO.setId(4033L);
		warehouseDTO1.setPincode("1223333");
		warehouseMap.put(4033L, warehouseDTO1);
	}

	public Long getWarehouseCodeByFulfillmentCentreCode(String fulfillmentCentreCode){
		Map<Long, WarehouseDTO> warehouseMapLocal=new HashMap<>();
		warehouseMapLocal.putAll(warehouseMap);
		for(Map.Entry<Long, WarehouseDTO> key:warehouseMapLocal.entrySet()){
			WarehouseDTO warehouseDTO=key.getValue();
			if(warehouseDTO.getFulfilmentCenterCode().contains(fulfillmentCentreCode)){
				return key.getKey();
			}
		}
		return null;
	}

	public WarehouseDTO getWarehouseDTOByFulfillmentCentreCode(String fulfillmentCentreCode){
		Map<Long, WarehouseDTO> warehouseMapLocal=new HashMap<>();
		warehouseMapLocal.putAll(warehouseMap);
		for(Map.Entry<Long, WarehouseDTO> key:warehouseMapLocal.entrySet()){
			WarehouseDTO warehouseDTO=key.getValue();
			if(warehouseDTO.getFulfilmentCenterCode().contains(fulfillmentCentreCode)){
				return key.getValue();
			}
		}
		return null;
	}

	public String getPincodeByWarehouse(Long warehouseId){
		WarehouseDTO warehouseDTO=warehouseMap.get(warehouseId);
		return warehouseDTO.getPincode();

	}
}
