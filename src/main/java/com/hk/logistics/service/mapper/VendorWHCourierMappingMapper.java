package com.hk.logistics.service.mapper;

import com.hk.logistics.domain.*;
import com.hk.logistics.service.dto.VendorWHCourierMappingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VendorWHCourierMapping and its DTO VendorWHCourierMappingDTO.
 */
@Mapper(componentModel = "spring", uses = {CourierChannelMapper.class})
public interface VendorWHCourierMappingMapper extends EntityMapper<VendorWHCourierMappingDTO, VendorWHCourierMapping> {

    @Mapping(source = "courierChannel.id", target = "courierChannelId")
    VendorWHCourierMappingDTO toDto(VendorWHCourierMapping vendorWHCourierMapping);

    @Mapping(source = "courierChannelId", target = "courierChannel")
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
