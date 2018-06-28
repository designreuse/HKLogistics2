package com.hk.logistics.service.impl;

import com.hk.logistics.service.HubService;
import com.hk.logistics.domain.Hub;
import com.hk.logistics.repository.HubRepository;
import com.hk.logistics.repository.search.HubSearchRepository;
import com.hk.logistics.service.dto.HubDTO;
import com.hk.logistics.service.mapper.HubMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Hub.
 */
@Service
@Transactional
public class HubServiceImpl implements HubService {

    private final Logger log = LoggerFactory.getLogger(HubServiceImpl.class);

    private final HubRepository hubRepository;

    private final HubMapper hubMapper;

    private final HubSearchRepository hubSearchRepository;

    public HubServiceImpl(HubRepository hubRepository, HubMapper hubMapper, HubSearchRepository hubSearchRepository) {
        this.hubRepository = hubRepository;
        this.hubMapper = hubMapper;
        this.hubSearchRepository = hubSearchRepository;
    }

    /**
     * Save a hub.
     *
     * @param hubDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HubDTO save(HubDTO hubDTO) {
        log.debug("Request to save Hub : {}", hubDTO);
        Hub hub = hubMapper.toEntity(hubDTO);
        hub = hubRepository.save(hub);
        HubDTO result = hubMapper.toDto(hub);
        hubSearchRepository.save(hub);
        return result;
    }

    /**
     * Get all the hubs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HubDTO> findAll() {
        log.debug("Request to get all Hubs");
        return hubRepository.findAll().stream()
            .map(hubMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one hub by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HubDTO> findOne(Long id) {
        log.debug("Request to get Hub : {}", id);
        return hubRepository.findById(id)
            .map(hubMapper::toDto);
    }

    /**
     * Delete the hub by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hub : {}", id);
        hubRepository.deleteById(id);
        hubSearchRepository.deleteById(id);
    }

    /**
     * Search for the hub corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HubDTO> search(String query) {
        log.debug("Request to search Hubs for query {}", query);
        return StreamSupport
            .stream(hubSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(hubMapper::toDto)
            .collect(Collectors.toList());
    }
}
