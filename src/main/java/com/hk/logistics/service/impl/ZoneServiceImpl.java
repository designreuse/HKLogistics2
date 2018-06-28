package com.hk.logistics.service.impl;

import com.hk.logistics.service.ZoneService;
import com.hk.logistics.domain.Zone;
import com.hk.logistics.repository.ZoneRepository;
import com.hk.logistics.repository.search.ZoneSearchRepository;
import com.hk.logistics.service.dto.ZoneDTO;
import com.hk.logistics.service.mapper.ZoneMapper;
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
 * Service Implementation for managing Zone.
 */
@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {

    private final Logger log = LoggerFactory.getLogger(ZoneServiceImpl.class);

    private final ZoneRepository zoneRepository;

    private final ZoneMapper zoneMapper;

    private final ZoneSearchRepository zoneSearchRepository;

    public ZoneServiceImpl(ZoneRepository zoneRepository, ZoneMapper zoneMapper, ZoneSearchRepository zoneSearchRepository) {
        this.zoneRepository = zoneRepository;
        this.zoneMapper = zoneMapper;
        this.zoneSearchRepository = zoneSearchRepository;
    }

    /**
     * Save a zone.
     *
     * @param zoneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ZoneDTO save(ZoneDTO zoneDTO) {
        log.debug("Request to save Zone : {}", zoneDTO);
        Zone zone = zoneMapper.toEntity(zoneDTO);
        zone = zoneRepository.save(zone);
        ZoneDTO result = zoneMapper.toDto(zone);
        zoneSearchRepository.save(zone);
        return result;
    }

    /**
     * Get all the zones.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ZoneDTO> findAll() {
        log.debug("Request to get all Zones");
        return zoneRepository.findAll().stream()
            .map(zoneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one zone by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ZoneDTO> findOne(Long id) {
        log.debug("Request to get Zone : {}", id);
        return zoneRepository.findById(id)
            .map(zoneMapper::toDto);
    }

    /**
     * Delete the zone by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Zone : {}", id);
        zoneRepository.deleteById(id);
        zoneSearchRepository.deleteById(id);
    }

    /**
     * Search for the zone corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ZoneDTO> search(String query) {
        log.debug("Request to search Zones for query {}", query);
        return StreamSupport
            .stream(zoneSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(zoneMapper::toDto)
            .collect(Collectors.toList());
    }
}
