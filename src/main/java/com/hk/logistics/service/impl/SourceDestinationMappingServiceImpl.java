package com.hk.logistics.service.impl;

import com.hk.logistics.service.SourceDestinationMappingService;
import com.hk.logistics.domain.SourceDestinationMapping;
import com.hk.logistics.repository.SourceDestinationMappingRepository;
import com.hk.logistics.repository.search.SourceDestinationMappingSearchRepository;
import com.hk.logistics.service.dto.SourceDestinationMappingDTO;
import com.hk.logistics.service.mapper.SourceDestinationMappingMapper;
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
 * Service Implementation for managing SourceDestinationMapping.
 */
@Service
@Transactional
public class SourceDestinationMappingServiceImpl implements SourceDestinationMappingService {

    private final Logger log = LoggerFactory.getLogger(SourceDestinationMappingServiceImpl.class);

    private final SourceDestinationMappingRepository sourceDestinationMappingRepository;

    private final SourceDestinationMappingMapper sourceDestinationMappingMapper;

    private final SourceDestinationMappingSearchRepository sourceDestinationMappingSearchRepository;

    public SourceDestinationMappingServiceImpl(SourceDestinationMappingRepository sourceDestinationMappingRepository, SourceDestinationMappingMapper sourceDestinationMappingMapper, SourceDestinationMappingSearchRepository sourceDestinationMappingSearchRepository) {
        this.sourceDestinationMappingRepository = sourceDestinationMappingRepository;
        this.sourceDestinationMappingMapper = sourceDestinationMappingMapper;
        this.sourceDestinationMappingSearchRepository = sourceDestinationMappingSearchRepository;
    }

    /**
     * Save a sourceDestinationMapping.
     *
     * @param sourceDestinationMappingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SourceDestinationMappingDTO save(SourceDestinationMappingDTO sourceDestinationMappingDTO) {
        log.debug("Request to save SourceDestinationMapping : {}", sourceDestinationMappingDTO);
        SourceDestinationMapping sourceDestinationMapping = sourceDestinationMappingMapper.toEntity(sourceDestinationMappingDTO);
        sourceDestinationMapping = sourceDestinationMappingRepository.save(sourceDestinationMapping);
        SourceDestinationMappingDTO result = sourceDestinationMappingMapper.toDto(sourceDestinationMapping);
        sourceDestinationMappingSearchRepository.save(sourceDestinationMapping);
        return result;
    }

    /**
     * Get all the sourceDestinationMappings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SourceDestinationMappingDTO> findAll() {
        log.debug("Request to get all SourceDestinationMappings");
        return sourceDestinationMappingRepository.findAll().stream()
            .map(sourceDestinationMappingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sourceDestinationMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SourceDestinationMappingDTO> findOne(Long id) {
        log.debug("Request to get SourceDestinationMapping : {}", id);
        return sourceDestinationMappingRepository.findById(id)
            .map(sourceDestinationMappingMapper::toDto);
    }

    /**
     * Delete the sourceDestinationMapping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SourceDestinationMapping : {}", id);
        sourceDestinationMappingRepository.deleteById(id);
        sourceDestinationMappingSearchRepository.deleteById(id);
    }

    /**
     * Search for the sourceDestinationMapping corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SourceDestinationMappingDTO> search(String query) {
        log.debug("Request to search SourceDestinationMappings for query {}", query);
        return StreamSupport
            .stream(sourceDestinationMappingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(sourceDestinationMappingMapper::toDto)
            .collect(Collectors.toList());
    }
}
