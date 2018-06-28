package com.hk.logistics.service;

import com.hk.logistics.service.dto.ProductVariantDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ProductVariant.
 */
public interface ProductVariantService {

    /**
     * Save a productVariant.
     *
     * @param productVariantDTO the entity to save
     * @return the persisted entity
     */
    ProductVariantDTO save(ProductVariantDTO productVariantDTO);

    /**
     * Get all the productVariants.
     *
     * @return the list of entities
     */
    List<ProductVariantDTO> findAll();


    /**
     * Get the "id" productVariant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductVariantDTO> findOne(Long id);

    /**
     * Delete the "id" productVariant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the productVariant corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ProductVariantDTO> search(String query);
}
