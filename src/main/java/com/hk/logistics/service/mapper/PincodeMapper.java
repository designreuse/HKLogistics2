package com.hk.logistics.service.mapper;

import com.hk.logistics.domain.*;
import com.hk.logistics.service.dto.PincodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pincode and its DTO PincodeDTO.
 */
@Mapper(componentModel = "spring", uses = {CityMapper.class, StateMapper.class, ZoneMapper.class, HubMapper.class})
public interface PincodeMapper extends EntityMapper<PincodeDTO, Pincode> {

    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "state.id", target = "stateId")
    @Mapping(source = "state.name", target = "stateName")
    @Mapping(source = "zone.id", target = "zoneId")
    @Mapping(source = "zone.name", target = "zoneName")
    @Mapping(source = "hub.id", target = "hubId")
    @Mapping(source = "hub.name", target = "hubName")
    PincodeDTO toDto(Pincode pincode);

    @Mapping(source = "cityId", target = "city")
    @Mapping(source = "stateId", target = "state")
    @Mapping(source = "zoneId", target = "zone")
    @Mapping(source = "hubId", target = "hub")
    Pincode toEntity(PincodeDTO pincodeDTO);

    default Pincode fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pincode pincode = new Pincode();
        pincode.setId(id);
        return pincode;
    }
}
