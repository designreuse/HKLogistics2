package com.hk.logistics.service.mapper;

import com.hk.logistics.domain.*;
import com.hk.logistics.service.dto.HubDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Hub and its DTO HubDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HubMapper extends EntityMapper<HubDTO, Hub> {


    @Mapping(target = "pinCodes", ignore = true)
    Hub toEntity(HubDTO hubDTO);

    default Hub fromId(Long id) {
        if (id == null) {
            return null;
        }
        Hub hub = new Hub();
        hub.setId(id);
        return hub;
    }
}
