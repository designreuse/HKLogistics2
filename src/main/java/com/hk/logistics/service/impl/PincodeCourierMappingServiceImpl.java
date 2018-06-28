package com.hk.logistics.service.impl;

import com.hk.logistics.service.PincodeCourierMappingService;
import com.hk.logistics.domain.PincodeCourierMapping;
import com.hk.logistics.repository.PincodeCourierMappingRepository;
import com.hk.logistics.repository.search.PincodeCourierMappingSearchRepository;
import com.hk.logistics.service.dto.PincodeCourierMappingDTO;
import com.hk.logistics.service.mapper.PincodeCourierMappingMapper;
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
 * Service Implementation for managing PincodeCourierMapping.
 */
@Service
@Transactional
public class PincodeCourierMappingServiceImpl implements PincodeCourierMappingService {

    private final Logger log = LoggerFactory.getLogger(PincodeCourierMappingServiceImpl.class);

    private final PincodeCourierMappingRepository pincodeCourierMappingRepository;

    private final PincodeCourierMappingMapper pincodeCourierMappingMapper;

    private final PincodeCourierMappingSearchRepository pincodeCourierMappingSearchRepository;

    public PincodeCourierMappingServiceImpl(PincodeCourierMappingRepository pincodeCourierMappingRepository, PincodeCourierMappingMapper pincodeCourierMappingMapper, PincodeCourierMappingSearchRepository pincodeCourierMappingSearchRepository) {
        this.pincodeCourierMappingRepository = pincodeCourierMappingRepository;
        this.pincodeCourierMappingMapper = pincodeCourierMappingMapper;
        this.pincodeCourierMappingSearchRepository = pincodeCourierMappingSearchRepository;
    }

    /**
     * Save a pincodeCourierMapping.
     *
     * @param pincodeCourierMappingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PincodeCourierMappingDTO save(PincodeCourierMappingDTO pincodeCourierMappingDTO) {
        log.debug("Request to save PincodeCourierMapping : {}", pincodeCourierMappingDTO);
        PincodeCourierMapping pincodeCourierMapping = pincodeCourierMappingMapper.toEntity(pincodeCourierMappingDTO);
        pincodeCourierMapping = pincodeCourierMappingRepository.save(pincodeCourierMapping);
        PincodeCourierMappingDTO result = pincodeCourierMappingMapper.toDto(pincodeCourierMapping);
        pincodeCourierMappingSearchRepository.save(pincodeCourierMapping);
        return result;
    }

    /**
     * Get all the pincodeCourierMappings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PincodeCourierMappingDTO> findAll() {
        log.debug("Request to get all PincodeCourierMappings");
        return pincodeCourierMappingRepository.findAll().stream()
            .map(pincodeCourierMappingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pincodeCourierMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PincodeCourierMappingDTO> findOne(Long id) {
        log.debug("Request to get PincodeCourierMapping : {}", id);
        return pincodeCourierMappingRepository.findById(id)
            .map(pincodeCourierMappingMapper::toDto);
    }

    /**
     * Delete the pincodeCourierMapping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PincodeCourierMapping : {}", id);
        pincodeCourierMappingRepository.deleteById(id);
        pincodeCourierMappingSearchRepository.deleteById(id);
    }

    /**
     * Search for the pincodeCourierMapping corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PincodeCourierMappingDTO> search(String query) {
        log.debug("Request to search PincodeCourierMappings for query {}", query);
        return StreamSupport
            .stream(pincodeCourierMappingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(pincodeCourierMappingMapper::toDto)
            .collect(Collectors.toList());
    }
}
