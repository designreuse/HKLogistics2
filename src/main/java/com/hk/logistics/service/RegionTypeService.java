package com.hk.logistics.service;

import com.hk.logistics.service.dto.RegionTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing RegionType.
 */
public interface RegionTypeService {

    /**
     * Save a regionType.
     *
     * @param regionTypeDTO the entity to save
     * @return the persisted entity
     */
    RegionTypeDTO save(RegionTypeDTO regionTypeDTO);

    /**
     * Get all the regionTypes.
     *
     * @return the list of entities
     */
    List<RegionTypeDTO> findAll();


    /**
     * Get the "id" regionType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RegionTypeDTO> findOne(Long id);

    /**
     * Delete the "id" regionType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the regionType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<RegionTypeDTO> search(String query);
}
