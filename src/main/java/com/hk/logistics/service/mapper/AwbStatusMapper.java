package com.hk.logistics.service.mapper;

import com.hk.logistics.domain.*;
import com.hk.logistics.service.dto.AwbStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AwbStatus and its DTO AwbStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AwbStatusMapper extends EntityMapper<AwbStatusDTO, AwbStatus> {



    default AwbStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        AwbStatus awbStatus = new AwbStatus();
        awbStatus.setId(id);
        return awbStatus;
    }
}
