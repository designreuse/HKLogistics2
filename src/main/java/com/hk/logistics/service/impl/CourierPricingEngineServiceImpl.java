package com.hk.logistics.service.impl;

import com.hk.logistics.service.CourierPricingEngineService;
import com.hk.logistics.domain.CourierPricingEngine;
import com.hk.logistics.repository.CourierPricingEngineRepository;
import com.hk.logistics.repository.search.CourierPricingEngineSearchRepository;
import com.hk.logistics.service.dto.CourierPricingEngineDTO;
import com.hk.logistics.service.mapper.CourierPricingEngineMapper;
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
 * Service Implementation for managing CourierPricingEngine.
 */
@Service
@Transactional
public class CourierPricingEngineServiceImpl implements CourierPricingEngineService {

    private final Logger log = LoggerFactory.getLogger(CourierPricingEngineServiceImpl.class);

    private final CourierPricingEngineRepository courierPricingEngineRepository;

    private final CourierPricingEngineMapper courierPricingEngineMapper;

    private final CourierPricingEngineSearchRepository courierPricingEngineSearchRepository;

    public CourierPricingEngineServiceImpl(CourierPricingEngineRepository courierPricingEngineRepository, CourierPricingEngineMapper courierPricingEngineMapper, CourierPricingEngineSearchRepository courierPricingEngineSearchRepository) {
        this.courierPricingEngineRepository = courierPricingEngineRepository;
        this.courierPricingEngineMapper = courierPricingEngineMapper;
        this.courierPricingEngineSearchRepository = courierPricingEngineSearchRepository;
    }

    /**
     * Save a courierPricingEngine.
     *
     * @param courierPricingEngineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CourierPricingEngineDTO save(CourierPricingEngineDTO courierPricingEngineDTO) {
        log.debug("Request to save CourierPricingEngine : {}", courierPricingEngineDTO);
        CourierPricingEngine courierPricingEngine = courierPricingEngineMapper.toEntity(courierPricingEngineDTO);
        courierPricingEngine = courierPricingEngineRepository.save(courierPricingEngine);
        CourierPricingEngineDTO result = courierPricingEngineMapper.toDto(courierPricingEngine);
        courierPricingEngineSearchRepository.save(courierPricingEngine);
        return result;
    }

    /**
     * Get all the courierPricingEngines.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CourierPricingEngineDTO> findAll() {
        log.debug("Request to get all CourierPricingEngines");
        return courierPricingEngineRepository.findAll().stream()
            .map(courierPricingEngineMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one courierPricingEngine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CourierPricingEngineDTO> findOne(Long id) {
        log.debug("Request to get CourierPricingEngine : {}", id);
        return courierPricingEngineRepository.findById(id)
            .map(courierPricingEngineMapper::toDto);
    }

    /**
     * Delete the courierPricingEngine by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourierPricingEngine : {}", id);
        courierPricingEngineRepository.deleteById(id);
        courierPricingEngineSearchRepository.deleteById(id);
    }

    /**
     * Search for the courierPricingEngine corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CourierPricingEngineDTO> search(String query) {
        log.debug("Request to search CourierPricingEngines for query {}", query);
        return StreamSupport
            .stream(courierPricingEngineSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(courierPricingEngineMapper::toDto)
            .collect(Collectors.toList());
    }
}
