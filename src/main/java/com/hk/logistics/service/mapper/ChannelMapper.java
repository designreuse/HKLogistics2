package com.hk.logistics.service.mapper;

import com.hk.logistics.domain.*;
import com.hk.logistics.service.dto.ChannelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Channel and its DTO ChannelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChannelMapper extends EntityMapper<ChannelDTO, Channel> {


    @Mapping(target = "courierChannels", ignore = true)
    Channel toEntity(ChannelDTO channelDTO);

    default Channel fromId(Long id) {
        if (id == null) {
            return null;
        }
        Channel channel = new Channel();
        channel.setId(id);
        return channel;
    }
}
