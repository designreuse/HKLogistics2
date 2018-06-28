package com.hk.logistics.service.mapper;

import com.hk.logistics.domain.*;
import com.hk.logistics.service.dto.ProductVariantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductVariant and its DTO ProductVariantDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductVariantMapper extends EntityMapper<ProductVariantDTO, ProductVariant> {



    default ProductVariant fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductVariant productVariant = new ProductVariant();
        productVariant.setId(id);
        return productVariant;
    }
}
