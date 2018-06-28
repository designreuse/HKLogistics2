package com.hk.logistics.service;

import com.hk.logistics.service.dto.CourierGroupDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CourierGroup.
 */
public interface CourierGroupService {

    /**
     * Save a courierGroup.
     *
     * @param courierGroupDTO the entity to save
     * @return the persisted entity
     */
    CourierGroupDTO save(CourierGroupDTO courierGroupDTO);

    /**
     * Get all the courierGroups.
     *
     * @return the list of entities
     */
    List<CourierGroupDTO> findAll();


    /**
     * Get the "id" courierGroup.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CourierGroupDTO> findOne(Long id);

    /**
     * Delete the "id" courierGroup.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the courierGroup corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CourierGroupDTO> search(String query);
}
