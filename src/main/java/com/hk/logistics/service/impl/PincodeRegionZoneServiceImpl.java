package com.hk.logistics.service.impl;

import com.hk.logistics.service.PincodeRegionZoneService;
import com.hk.logistics.domain.PincodeRegionZone;
import com.hk.logistics.repository.PincodeRegionZoneRepository;
import com.hk.logistics.repository.search.PincodeRegionZoneSearchRepository;
import com.hk.logistics.service.dto.PincodeRegionZoneDTO;
import com.hk.logistics.service.mapper.PincodeRegionZoneMapper;
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
 * Service Implementation for managing PincodeRegionZone.
 */
@Service
@Transactional
public class PincodeRegionZoneServiceImpl implements PincodeRegionZoneService {

    private final Logger log = LoggerFactory.getLogger(PincodeRegionZoneServiceImpl.class);

    private final PincodeRegionZoneRepository pincodeRegionZoneRepository;

    private final PincodeRegionZoneMapper pincodeRegionZoneMapper;

    private final PincodeRegionZoneSearchRepository pincodeRegionZoneSearchRepository;

    public PincodeRegionZoneServiceImpl(PincodeRegionZoneRepository pincodeRegionZoneRepository, PincodeRegionZoneMapper pincodeRegionZoneMapper, PincodeRegionZoneSearchRepository pincodeRegionZoneSearchRepository) {
        this.pincodeRegionZoneRepository = pincodeRegionZoneRepository;
        this.pincodeRegionZoneMapper = pincodeRegionZoneMapper;
        this.pincodeRegionZoneSearchRepository = pincodeRegionZoneSearchRepository;
    }

    /**
     * Save a pincodeRegionZone.
     *
     * @param pincodeRegionZoneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PincodeRegionZoneDTO save(PincodeRegionZoneDTO pincodeRegionZoneDTO) {
        log.debug("Request to save PincodeRegionZone : {}", pincodeRegionZoneDTO);
        PincodeRegionZone pincodeRegionZone = pincodeRegionZoneMapper.toEntity(pincodeRegionZoneDTO);
        pincodeRegionZone = pincodeRegionZoneRepository.save(pincodeRegionZone);
        PincodeRegionZoneDTO result = pincodeRegionZoneMapper.toDto(pincodeRegionZone);
        pincodeRegionZoneSearchRepository.save(pincodeRegionZone);
        return result;
    }

    /**
     * Get all the pincodeRegionZones.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PincodeRegionZoneDTO> findAll() {
        log.debug("Request to get all PincodeRegionZones");
        return pincodeRegionZoneRepository.findAll().stream()
            .map(pincodeRegionZoneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pincodeRegionZone by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PincodeRegionZoneDTO> findOne(Long id) {
        log.debug("Request to get PincodeRegionZone : {}", id);
        return pincodeRegionZoneRepository.findById(id)
            .map(pincodeRegionZoneMapper::toDto);
    }

    /**
     * Delete the pincodeRegionZone by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PincodeRegionZone : {}", id);
        pincodeRegionZoneRepository.deleteById(id);
        pincodeRegionZoneSearchRepository.deleteById(id);
    }

    /**
     * Search for the pincodeRegionZone corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PincodeRegionZoneDTO> search(String query) {
        log.debug("Request to search PincodeRegionZones for query {}", query);
        return StreamSupport
            .stream(pincodeRegionZoneSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(pincodeRegionZoneMapper::toDto)
            .collect(Collectors.toList());
    }
}
