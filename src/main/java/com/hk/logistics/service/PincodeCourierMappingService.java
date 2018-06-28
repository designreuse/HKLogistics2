package com.hk.logistics.service;

import com.hk.logistics.service.dto.PincodeCourierMappingDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PincodeCourierMapping.
 */
public interface PincodeCourierMappingService {

    /**
     * Save a pincodeCourierMapping.
     *
     * @param pincodeCourierMappingDTO the entity to save
     * @return the persisted entity
     */
    PincodeCourierMappingDTO save(PincodeCourierMappingDTO pincodeCourierMappingDTO);

    /**
     * Get all the pincodeCourierMappings.
     *
     * @return the list of entities
     */
    List<PincodeCourierMappingDTO> findAll();


    /**
     * Get the "id" pincodeCourierMapping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PincodeCourierMappingDTO> findOne(Long id);

    /**
     * Delete the "id" pincodeCourierMapping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pincodeCourierMapping corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PincodeCourierMappingDTO> search(String query);
}
