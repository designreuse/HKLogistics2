package com.hk.logistics.service;

import com.hk.logistics.service.dto.ZoneDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Zone.
 */
public interface ZoneService {

    /**
     * Save a zone.
     *
     * @param zoneDTO the entity to save
     * @return the persisted entity
     */
    ZoneDTO save(ZoneDTO zoneDTO);

    /**
     * Get all the zones.
     *
     * @return the list of entities
     */
    List<ZoneDTO> findAll();


    /**
     * Get the "id" zone.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ZoneDTO> findOne(Long id);

    /**
     * Delete the "id" zone.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the zone corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ZoneDTO> search(String query);
}
