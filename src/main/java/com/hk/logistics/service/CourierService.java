package com.hk.logistics.service;

import com.hk.logistics.service.dto.CourierDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Courier.
 */
public interface CourierService {

    /**
     * Save a courier.
     *
     * @param courierDTO the entity to save
     * @return the persisted entity
     */
    CourierDTO save(CourierDTO courierDTO);

    /**
     * Get all the couriers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CourierDTO> findAll(Pageable pageable);


    /**
     * Get the "id" courier.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CourierDTO> findOne(Long id);

    /**
     * Delete the "id" courier.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the courier corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CourierDTO> search(String query, Pageable pageable);
}
