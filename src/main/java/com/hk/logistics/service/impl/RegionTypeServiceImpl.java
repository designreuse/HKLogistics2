package com.hk.logistics.service.impl;

import com.hk.logistics.service.RegionTypeService;
import com.hk.logistics.domain.RegionType;
import com.hk.logistics.repository.RegionTypeRepository;
import com.hk.logistics.repository.search.RegionTypeSearchRepository;
import com.hk.logistics.service.dto.RegionTypeDTO;
import com.hk.logistics.service.mapper.RegionTypeMapper;
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
 * Service Implementation for managing RegionType.
 */
@Service
@Transactional
public class RegionTypeServiceImpl implements RegionTypeService {

    private final Logger log = LoggerFactory.getLogger(RegionTypeServiceImpl.class);

    private final RegionTypeRepository regionTypeRepository;

    private final RegionTypeMapper regionTypeMapper;

    private final RegionTypeSearchRepository regionTypeSearchRepository;

    public RegionTypeServiceImpl(RegionTypeRepository regionTypeRepository, RegionTypeMapper regionTypeMapper, RegionTypeSearchRepository regionTypeSearchRepository) {
        this.regionTypeRepository = regionTypeRepository;
        this.regionTypeMapper = regionTypeMapper;
        this.regionTypeSearchRepository = regionTypeSearchRepository;
    }

    /**
     * Save a regionType.
     *
     * @param regionTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RegionTypeDTO save(RegionTypeDTO regionTypeDTO) {
        log.debug("Request to save RegionType : {}", regionTypeDTO);
        RegionType regionType = regionTypeMapper.toEntity(regionTypeDTO);
        regionType = regionTypeRepository.save(regionType);
        RegionTypeDTO result = regionTypeMapper.toDto(regionType);
        regionTypeSearchRepository.save(regionType);
        return result;
    }

    /**
     * Get all the regionTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegionTypeDTO> findAll() {
        log.debug("Request to get all RegionTypes");
        return regionTypeRepository.findAll().stream()
            .map(regionTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one regionType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegionTypeDTO> findOne(Long id) {
        log.debug("Request to get RegionType : {}", id);
        return regionTypeRepository.findById(id)
            .map(regionTypeMapper::toDto);
    }

    /**
     * Delete the regionType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegionType : {}", id);
        regionTypeRepository.deleteById(id);
        regionTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the regionType corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegionTypeDTO> search(String query) {
        log.debug("Request to search RegionTypes for query {}", query);
        return StreamSupport
            .stream(regionTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(regionTypeMapper::toDto)
            .collect(Collectors.toList());
    }
}
