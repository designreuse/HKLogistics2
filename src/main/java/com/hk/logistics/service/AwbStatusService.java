package com.hk.logistics.service;

import com.hk.logistics.service.dto.AwbStatusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AwbStatus.
 */
public interface AwbStatusService {

    /**
     * Save a awbStatus.
     *
     * @param awbStatusDTO the entity to save
     * @return the persisted entity
     */
    AwbStatusDTO save(AwbStatusDTO awbStatusDTO);

    /**
     * Get all the awbStatuses.
     *
     * @return the list of entities
     */
    List<AwbStatusDTO> findAll();


    /**
     * Get the "id" awbStatus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AwbStatusDTO> findOne(Long id);

    /**
     * Delete the "id" awbStatus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the awbStatus corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<AwbStatusDTO> search(String query);
}
