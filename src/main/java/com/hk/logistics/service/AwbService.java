package com.hk.logistics.service;

import com.hk.logistics.domain.*;
import com.hk.logistics.service.dto.*;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Awb.
 */
public interface AwbService {

    /**
     * Save a awb.
     *
     * @param awbDTO the entity to save
     * @return the persisted entity
     */
    AwbDTO save(AwbDTO awbDTO);

    /**
     * Get all the awbs.
     *
     * @return the list of entities
     */
    List<AwbDTO> findAll();


    /**
     * Get the "id" awb.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AwbDTO> findOne(Long id);

    /**
     * Delete the "id" awb.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the awb corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<AwbDTO> search(String query);
}
