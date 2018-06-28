package com.hk.logistics.service;

import com.hk.logistics.service.dto.CourierPricingEngineDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CourierPricingEngine.
 */
public interface CourierPricingEngineService {

    /**
     * Save a courierPricingEngine.
     *
     * @param courierPricingEngineDTO the entity to save
     * @return the persisted entity
     */
    CourierPricingEngineDTO save(CourierPricingEngineDTO courierPricingEngineDTO);

    /**
     * Get all the courierPricingEngines.
     *
     * @return the list of entities
     */
    List<CourierPricingEngineDTO> findAll();


    /**
     * Get the "id" courierPricingEngine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CourierPricingEngineDTO> findOne(Long id);

    /**
     * Delete the "id" courierPricingEngine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the courierPricingEngine corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CourierPricingEngineDTO> search(String query);
}
