package com.hk.logistics.service.impl;

import com.hk.logistics.service.CourierGroupService;
import com.hk.logistics.domain.CourierGroup;
import com.hk.logistics.repository.CourierGroupRepository;
import com.hk.logistics.repository.search.CourierGroupSearchRepository;
import com.hk.logistics.service.dto.CourierGroupDTO;
import com.hk.logistics.service.mapper.CourierGroupMapper;
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
 * Service Implementation for managing CourierGroup.
 */
@Service
@Transactional
public class CourierGroupServiceImpl implements CourierGroupService {

    private final Logger log = LoggerFactory.getLogger(CourierGroupServiceImpl.class);

    private final CourierGroupRepository courierGroupRepository;

    private final CourierGroupMapper courierGroupMapper;

    private final CourierGroupSearchRepository courierGroupSearchRepository;

    public CourierGroupServiceImpl(CourierGroupRepository courierGroupRepository, CourierGroupMapper courierGroupMapper, CourierGroupSearchRepository courierGroupSearchRepository) {
        this.courierGroupRepository = courierGroupRepository;
        this.courierGroupMapper = courierGroupMapper;
        this.courierGroupSearchRepository = courierGroupSearchRepository;
    }

    /**
     * Save a courierGroup.
     *
     * @param courierGroupDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CourierGroupDTO save(CourierGroupDTO courierGroupDTO) {
        log.debug("Request to save CourierGroup : {}", courierGroupDTO);
        CourierGroup courierGroup = courierGroupMapper.toEntity(courierGroupDTO);
        courierGroup = courierGroupRepository.save(courierGroup);
        CourierGroupDTO result = courierGroupMapper.toDto(courierGroup);
        courierGroupSearchRepository.save(courierGroup);
        return result;
    }

    /**
     * Get all the courierGroups.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CourierGroupDTO> findAll() {
        log.debug("Request to get all CourierGroups");
        return courierGroupRepository.findAll().stream()
            .map(courierGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one courierGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CourierGroupDTO> findOne(Long id) {
        log.debug("Request to get CourierGroup : {}", id);
        return courierGroupRepository.findById(id)
            .map(courierGroupMapper::toDto);
    }

    /**
     * Delete the courierGroup by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourierGroup : {}", id);
        courierGroupRepository.deleteById(id);
        courierGroupSearchRepository.deleteById(id);
    }

    /**
     * Search for the courierGroup corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CourierGroupDTO> search(String query) {
        log.debug("Request to search CourierGroups for query {}", query);
        return StreamSupport
            .stream(courierGroupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(courierGroupMapper::toDto)
            .collect(Collectors.toList());
    }
}
