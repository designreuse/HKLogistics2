package com.hk.logistics.enums;


import com.hk.logistics.domain.ShipmentServiceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumShipmentServiceType {

	Prepaid_Air(1L, "prepaidAir"),
    Prepaid_Ground(2L, "prepaidGround"),
    Cod_Air(3L, "codAir"),
    Cod_Ground(4L, "codGround"),
    ReversePickup_Air(5L,"reverseAir"),
    ReversePickup_Ground(6L,"reverseGround"),
    CardOnDelivery_Air(7L, "cardOnDeliveryAir"),
    CardOnDelivery_Ground(8L, "cardOnDeliveryGround");

    private String name;
    private Long id;

    EnumShipmentServiceType(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public static EnumShipmentServiceType getShipmentTypeFromId(Long id) {
        for (EnumShipmentServiceType shipmentType : values()) {
            if (shipmentType.getId().equals(id)) return shipmentType;
        }
        return null;
    }

    public static List<EnumShipmentServiceType> getCodEnumShipmentServiceTypes() {
        return Arrays.asList(Cod_Air,Cod_Ground);
    }

    public static List<ShipmentServiceType> getCodhipmentServiceTypes() {
        return Arrays.asList(Cod_Air.asShipmentServiceType(),Cod_Ground.asShipmentServiceType());
    }

    public static List<EnumShipmentServiceType> getPrepaidEnumShipmentServiceTypes() {
        return Arrays.asList(Prepaid_Air, Prepaid_Ground);
    }

    public static List<ShipmentServiceType> getPrepaidhipmentServiceTypes() {
        return Arrays.asList(Prepaid_Air.asShipmentServiceType(), Prepaid_Ground.asShipmentServiceType());
    }

    public static List<EnumShipmentServiceType> getGroundEnumShipmentServiceTypes() {
        return Arrays.asList(Prepaid_Ground,Cod_Ground, CardOnDelivery_Ground);
    }

    public static List<ShipmentServiceType> getGroundShipmentServiceTypes() {
        return Arrays.asList(Prepaid_Ground.asShipmentServiceType(),Cod_Ground.asShipmentServiceType(), CardOnDelivery_Ground.asShipmentServiceType());
    }

    public static List<EnumShipmentServiceType> getAirEnumShipmentServiceTypes() {
        return Arrays.asList(Cod_Air,Prepaid_Air, CardOnDelivery_Air);
    }

    public static List<ShipmentServiceType> getAirShipmentServiceTypes() {
        return Arrays.asList(Cod_Air.asShipmentServiceType(),Prepaid_Air.asShipmentServiceType(), CardOnDelivery_Air.asShipmentServiceType());
    }

    public static List<EnumShipmentServiceType> getAllEnumShipmentServiceType() {
        return Arrays.asList(Cod_Air,Cod_Ground,Prepaid_Air,Prepaid_Ground, CardOnDelivery_Air, CardOnDelivery_Ground);
    }

    public static List<ShipmentServiceType> getAllShipmentServiceType() {
        return Arrays.asList(Cod_Air.asShipmentServiceType(),Cod_Ground.asShipmentServiceType(),Prepaid_Air.asShipmentServiceType(),Prepaid_Ground.asShipmentServiceType(),
        		CardOnDelivery_Air.asShipmentServiceType(),  CardOnDelivery_Ground.asShipmentServiceType());
    }

    public ShipmentServiceType asShipmentServiceType() {
        ShipmentServiceType shipmentServiceType = new ShipmentServiceType();
        shipmentServiceType.setId(id);
        shipmentServiceType.setName(name);
        return shipmentServiceType;
    }

    public static List<Long> getShipmentServiceTypesIds(List<EnumShipmentServiceType> enumShipmentServiceTypes) {
        List<Long> shipmentServiceTypeIds = new ArrayList<Long>();
        for (EnumShipmentServiceType enumShipmentServiceType : enumShipmentServiceTypes) {
            shipmentServiceTypeIds.add(enumShipmentServiceType.getId());
        }
        return shipmentServiceTypeIds;
    }
}
