package com.hk.logistics.service.impl;

import com.hk.logistics.service.CourierService;
import com.hk.logistics.domain.Courier;
import com.hk.logistics.repository.CourierRepository;
import com.hk.logistics.repository.search.CourierSearchRepository;
import com.hk.logistics.service.dto.CourierDTO;
import com.hk.logistics.service.mapper.CourierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Courier.
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {

    private final Logger log = LoggerFactory.getLogger(CourierServiceImpl.class);

    private final CourierRepository courierRepository;

    private final CourierMapper courierMapper;

    private final CourierSearchRepository courierSearchRepository;

    public CourierServiceImpl(CourierRepository courierRepository, CourierMapper courierMapper, CourierSearchRepository courierSearchRepository) {
        this.courierRepository = courierRepository;
        this.courierMapper = courierMapper;
        this.courierSearchRepository = courierSearchRepository;
    }

    /**
     * Save a courier.
     *
     * @param courierDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CourierDTO save(CourierDTO courierDTO) {
        log.debug("Request to save Courier : {}", courierDTO);
        Courier courier = courierMapper.toEntity(courierDTO);
        courier = courierRepository.save(courier);
        CourierDTO result = courierMapper.toDto(courier);
        courierSearchRepository.save(courier);
        return result;
    }

    /**
     * Get all the couriers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CourierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Couriers");
        return courierRepository.findAll(pageable)
            .map(courierMapper::toDto);
    }


    /**
     * Get one courier by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CourierDTO> findOne(Long id) {
        log.debug("Request to get Courier : {}", id);
        return courierRepository.findById(id)
            .map(courierMapper::toDto);
    }

    /**
     * Delete the courier by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Courier : {}", id);
        courierRepository.deleteById(id);
        courierSearchRepository.deleteById(id);
    }

    /**
     * Search for the courier corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CourierDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Couriers for query {}", query);
        return courierSearchRepository.search(queryStringQuery(query), pageable)
            .map(courierMapper::toDto);
    }
    
    /**
     * Search for the courier corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */

	@Override
	@Transactional(readOnly = true)
	public Page<CourierDTO> searchByName(String query, Pageable pageable) {
        log.debug("Request to search for a page of Couriers for query {}", query);
        return courierSearchRepository.findByName(query, pageable)
            .map(courierMapper::toDto);
    }
}
