package com.hk.logistics.service;

import com.hk.logistics.service.dto.PincodeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Pincode.
 */
public interface PincodeService {

    /**
     * Save a pincode.
     *
     * @param pincodeDTO the entity to save
     * @return the persisted entity
     */
    PincodeDTO save(PincodeDTO pincodeDTO);

    /**
     * Get all the pincodes.
     *
     * @return the list of entities
     */
    List<PincodeDTO> findAll();


    /**
     * Get the "id" pincode.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PincodeDTO> findOne(Long id);

    /**
     * Delete the "id" pincode.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pincode corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PincodeDTO> search(String query);
}
