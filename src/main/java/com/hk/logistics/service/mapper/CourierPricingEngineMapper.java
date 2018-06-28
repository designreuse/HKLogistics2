package com.hk.logistics.service.mapper;

import com.hk.logistics.domain.*;
import com.hk.logistics.service.dto.CourierPricingEngineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CourierPricingEngine and its DTO CourierPricingEngineDTO.
 */
@Mapper(componentModel = "spring", uses = {CourierMapper.class, RegionTypeMapper.class})
public interface CourierPricingEngineMapper extends EntityMapper<CourierPricingEngineDTO, CourierPricingEngine> {

    @Mapping(source = "courier.id", target = "courierId")
    @Mapping(source = "courier.name", target = "courierName")
    @Mapping(source = "regionType.id", target = "regionTypeId")
    @Mapping(source = "regionType.name", target = "regionTypeName")
    CourierPricingEngineDTO toDto(CourierPricingEngine courierPricingEngine);

    @Mapping(source = "courierId", target = "courier")
    @Mapping(source = "regionTypeId", target = "regionType")
    CourierPricingEngine toEntity(CourierPricingEngineDTO courierPricingEngineDTO);

    default CourierPricingEngine fromId(Long id) {
        if (id == null) {
            return null;
        }
        CourierPricingEngine courierPricingEngine = new CourierPricingEngine();
        courierPricingEngine.setId(id);
        return courierPricingEngine;
    }
}
