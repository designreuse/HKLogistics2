package com.hk.logistics.service;

import com.hk.logistics.service.dto.VendorWHCourierMappingDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing VendorWHCourierMapping.
 */
public interface VendorWHCourierMappingService {

    /**
     * Save a vendorWHCourierMapping.
     *
     * @param vendorWHCourierMappingDTO the entity to save
     * @return the persisted entity
     */
    VendorWHCourierMappingDTO save(VendorWHCourierMappingDTO vendorWHCourierMappingDTO);

    /**
     * Get all the vendorWHCourierMappings.
     *
     * @return the list of entities
     */
    List<VendorWHCourierMappingDTO> findAll();


    /**
     * Get the "id" vendorWHCourierMapping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VendorWHCourierMappingDTO> findOne(Long id);

    /**
     * Delete the "id" vendorWHCourierMapping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the vendorWHCourierMapping corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<VendorWHCourierMappingDTO> search(String query);
}
