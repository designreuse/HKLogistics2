package com.hk.logistics.service.mapper;

import com.hk.logistics.domain.*;
import com.hk.logistics.service.dto.AwbDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Awb and its DTO AwbDTO.
 */
@Mapper(componentModel = "spring", uses = {ChannelMapper.class, VendorWHCourierMappingMapper.class, AwbStatusMapper.class})
public interface AwbMapper extends EntityMapper<AwbDTO, Awb> {

    @Mapping(source = "channel.id", target = "channelId")
    @Mapping(source = "channel.name", target = "channelName")
    @Mapping(source = "vendorWHCourierMapping.id", target = "vendorWHCourierMappingId")
    @Mapping(source = "awbStatus.id", target = "awbStatusId")
    @Mapping(source = "awbStatus.status", target = "awbStatusStatus")
    AwbDTO toDto(Awb awb);

    @Mapping(source = "channelId", target = "channel")
    @Mapping(source = "vendorWHCourierMappingId", target = "vendorWHCourierMapping")
    @Mapping(source = "awbStatusId", target = "awbStatus")
    Awb toEntity(AwbDTO awbDTO);

    default Awb fromId(Long id) {
        if (id == null) {
            return null;
        }
        Awb awb = new Awb();
        awb.setId(id);
        return awb;
    }
}
