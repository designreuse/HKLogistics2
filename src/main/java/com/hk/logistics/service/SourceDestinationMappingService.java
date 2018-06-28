package com.hk.logistics.service;

import com.hk.logistics.service.dto.SourceDestinationMappingDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SourceDestinationMapping.
 */
public interface SourceDestinationMappingService {

    /**
     * Save a sourceDestinationMapping.
     *
     * @param sourceDestinationMappingDTO the entity to save
     * @return the persisted entity
     */
    SourceDestinationMappingDTO save(SourceDestinationMappingDTO sourceDestinationMappingDTO);

    /**
     * Get all the sourceDestinationMappings.
     *
     * @return the list of entities
     */
    List<SourceDestinationMappingDTO> findAll();


    /**
     * Get the "id" sourceDestinationMapping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SourceDestinationMappingDTO> findOne(Long id);

    /**
     * Delete the "id" sourceDestinationMapping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the sourceDestinationMapping corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<SourceDestinationMappingDTO> search(String query);
}
