package com.hk.logistics.service.mapper;

import com.hk.logistics.domain.*;
import com.hk.logistics.service.dto.PincodeCourierMappingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PincodeCourierMapping and its DTO PincodeCourierMappingDTO.
 */
@Mapper(componentModel = "spring", uses = {PincodeMapper.class, VendorWHCourierMappingMapper.class, SourceDestinationMappingMapper.class})
public interface PincodeCourierMappingMapper extends EntityMapper<PincodeCourierMappingDTO, PincodeCourierMapping> {

    @Mapping(source = "pincode.id", target = "pincodeId")
    @Mapping(source = "vendorWHCourierMapping.id", target = "vendorWHCourierMappingId")
    @Mapping(source = "sourceDestinationMapping.id", target = "sourceDestinationMappingId")
    PincodeCourierMappingDTO toDto(PincodeCourierMapping pincodeCourierMapping);

    @Mapping(source = "pincodeId", target = "pincode")
    @Mapping(source = "vendorWHCourierMappingId", target = "vendorWHCourierMapping")
    @Mapping(source = "sourceDestinationMappingId", target = "sourceDestinationMapping")
    PincodeCourierMapping toEntity(PincodeCourierMappingDTO pincodeCourierMappingDTO);

    default PincodeCourierMapping fromId(Long id) {
        if (id == null) {
            return null;
        }
        PincodeCourierMapping pincodeCourierMapping = new PincodeCourierMapping();
        pincodeCourierMapping.setId(id);
        return pincodeCourierMapping;
    }
}
