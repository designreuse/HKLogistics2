package com.hk.logistics.service;

import com.hk.logistics.service.dto.ShipmentServiceTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ShipmentServiceType.
 */
public interface ShipmentServiceTypeService {

    /**
     * Save a shipmentServiceType.
     *
     * @param shipmentServiceTypeDTO the entity to save
     * @return the persisted entity
     */
    ShipmentServiceTypeDTO save(ShipmentServiceTypeDTO shipmentServiceTypeDTO);

    /**
     * Get all the shipmentServiceTypes.
     *
     * @return the list of entities
     */
    List<ShipmentServiceTypeDTO> findAll();


    /**
     * Get the "id" shipmentServiceType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShipmentServiceTypeDTO> findOne(Long id);

    /**
     * Delete the "id" shipmentServiceType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shipmentServiceType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ShipmentServiceTypeDTO> search(String query);
}
