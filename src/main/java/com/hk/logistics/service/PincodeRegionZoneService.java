package com.hk.logistics.service;

import com.hk.logistics.service.dto.PincodeRegionZoneDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PincodeRegionZone.
 */
public interface PincodeRegionZoneService {

    /**
     * Save a pincodeRegionZone.
     *
     * @param pincodeRegionZoneDTO the entity to save
     * @return the persisted entity
     */
    PincodeRegionZoneDTO save(PincodeRegionZoneDTO pincodeRegionZoneDTO);

    /**
     * Get all the pincodeRegionZones.
     *
     * @return the list of entities
     */
    List<PincodeRegionZoneDTO> findAll();


    /**
     * Get the "id" pincodeRegionZone.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PincodeRegionZoneDTO> findOne(Long id);

    /**
     * Delete the "id" pincodeRegionZone.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pincodeRegionZone corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PincodeRegionZoneDTO> search(String query);
}
