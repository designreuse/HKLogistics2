package com.hk.logistics.service;

import com.hk.logistics.service.dto.ChannelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Channel.
 */
public interface ChannelService {

    /**
     * Save a channel.
     *
     * @param channelDTO the entity to save
     * @return the persisted entity
     */
    ChannelDTO save(ChannelDTO channelDTO);

    /**
     * Get all the channels.
     *
     * @return the list of entities
     */
    List<ChannelDTO> findAll();


    /**
     * Get the "id" channel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ChannelDTO> findOne(Long id);

    /**
     * Delete the "id" channel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the channel corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ChannelDTO> search(String query);
}
