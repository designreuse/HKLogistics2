package com.hk.logistics.service;

import com.hk.logistics.service.dto.HubDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Hub.
 */
public interface HubService {

    /**
     * Save a hub.
     *
     * @param hubDTO the entity to save
     * @return the persisted entity
     */
    HubDTO save(HubDTO hubDTO);

    /**
     * Get all the hubs.
     *
     * @return the list of entities
     */
    List<HubDTO> findAll();


    /**
     * Get the "id" hub.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HubDTO> findOne(Long id);

    /**
     * Delete the "id" hub.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hub corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<HubDTO> search(String query);
}
