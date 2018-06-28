package com.hk.logistics.service;

import com.hk.logistics.service.dto.CourierChannelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CourierChannel.
 */
public interface CourierChannelService {

    /**
     * Save a courierChannel.
     *
     * @param courierChannelDTO the entity to save
     * @return the persisted entity
     */
    CourierChannelDTO save(CourierChannelDTO courierChannelDTO);

    /**
     * Get all the courierChannels.
     *
     * @return the list of entities
     */
    List<CourierChannelDTO> findAll();


    /**
     * Get the "id" courierChannel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CourierChannelDTO> findOne(Long id);

    /**
     * Delete the "id" courierChannel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the courierChannel corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CourierChannelDTO> search(String query);
}
