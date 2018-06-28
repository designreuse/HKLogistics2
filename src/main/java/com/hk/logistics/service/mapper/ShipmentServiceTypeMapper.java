package com.hk.logistics.service.mapper;

import com.hk.logistics.domain.*;
import com.hk.logistics.service.dto.ShipmentServiceTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShipmentServiceType and its DTO ShipmentServiceTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShipmentServiceTypeMapper extends EntityMapper<ShipmentServiceTypeDTO, ShipmentServiceType> {



    default ShipmentServiceType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShipmentServiceType shipmentServiceType = new ShipmentServiceType();
        shipmentServiceType.setId(id);
        return shipmentServiceType;
    }
}
