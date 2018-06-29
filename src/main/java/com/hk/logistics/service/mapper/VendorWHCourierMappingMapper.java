package com.hk.logistics.service.mapper;

import com.hk.logistics.domain.*;
import com.hk.logistics.service.dto.VendorWHCourierMappingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VendorWHCourierMapping and its DTO VendorWHCourierMappingDTO.
 */
@Mapper(componentModel = "spring", uses = {CourierMapper.class})
public interface VendorWHCourierMappingMapper extends EntityMapper<VendorWHCourierMappingDTO, VendorWHCourierMapping> {

    @Mapping(source = "courier.id", target = "courierId")
    @Mapping(source = "courier.name", target = "courierName")
    VendorWHCourierMappingDTO toDto(VendorWHCourierMapping vendorWHCourierMapping);

    @Mapping(source = "courierId", target = "courier")
    VendorWHCourierMapping toEntity(VendorWHCourierMappingDTO vendorWHCourierMappingDTO);

    default VendorWHCourierMapping fromId(Long id) {
        if (id == null) {
            return null;
        }
        VendorWHCourierMapping vendorWHCourierMapping = new VendorWHCourierMapping();
        vendorWHCourierMapping.setId(id);
        return vendorWHCourierMapping;
    }
}
