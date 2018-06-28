package com.hk.logistics.service.mapper;

import com.hk.logistics.domain.*;
import com.hk.logistics.service.dto.CourierGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CourierGroup and its DTO CourierGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CourierGroupMapper extends EntityMapper<CourierGroupDTO, CourierGroup> {


    @Mapping(target = "couriers", ignore = true)
    CourierGroup toEntity(CourierGroupDTO courierGroupDTO);

    default CourierGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        CourierGroup courierGroup = new CourierGroup();
        courierGroup.setId(id);
        return courierGroup;
    }
}
