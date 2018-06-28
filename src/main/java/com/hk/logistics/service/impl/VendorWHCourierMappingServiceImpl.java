package com.hk.logistics.service.impl;

import com.hk.logistics.service.VendorWHCourierMappingService;
import com.hk.logistics.domain.VendorWHCourierMapping;
import com.hk.logistics.repository.VendorWHCourierMappingRepository;
import com.hk.logistics.repository.search.VendorWHCourierMappingSearchRepository;
import com.hk.logistics.service.dto.VendorWHCourierMappingDTO;
import com.hk.logistics.service.mapper.VendorWHCourierMappingMapper;
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
 * Service Implementation for managing VendorWHCourierMapping.
 */
@Service
@Transactional
public class VendorWHCourierMappingServiceImpl implements VendorWHCourierMappingService {

    private final Logger log = LoggerFactory.getLogger(VendorWHCourierMappingServiceImpl.class);

    private final VendorWHCourierMappingRepository vendorWHCourierMappingRepository;

    private final VendorWHCourierMappingMapper vendorWHCourierMappingMapper;

    private final VendorWHCourierMappingSearchRepository vendorWHCourierMappingSearchRepository;

    public VendorWHCourierMappingServiceImpl(VendorWHCourierMappingRepository vendorWHCourierMappingRepository, VendorWHCourierMappingMapper vendorWHCourierMappingMapper, VendorWHCourierMappingSearchRepository vendorWHCourierMappingSearchRepository) {
        this.vendorWHCourierMappingRepository = vendorWHCourierMappingRepository;
        this.vendorWHCourierMappingMapper = vendorWHCourierMappingMapper;
        this.vendorWHCourierMappingSearchRepository = vendorWHCourierMappingSearchRepository;
    }

    /**
     * Save a vendorWHCourierMapping.
     *
     * @param vendorWHCourierMappingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VendorWHCourierMappingDTO save(VendorWHCourierMappingDTO vendorWHCourierMappingDTO) {
        log.debug("Request to save VendorWHCourierMapping : {}", vendorWHCourierMappingDTO);
        VendorWHCourierMapping vendorWHCourierMapping = vendorWHCourierMappingMapper.toEntity(vendorWHCourierMappingDTO);
        vendorWHCourierMapping = vendorWHCourierMappingRepository.save(vendorWHCourierMapping);
        VendorWHCourierMappingDTO result = vendorWHCourierMappingMapper.toDto(vendorWHCourierMapping);
        vendorWHCourierMappingSearchRepository.save(vendorWHCourierMapping);
        return result;
    }

    /**
     * Get all the vendorWHCourierMappings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<VendorWHCourierMappingDTO> findAll() {
        log.debug("Request to get all VendorWHCourierMappings");
        return vendorWHCourierMappingRepository.findAll().stream()
            .map(vendorWHCourierMappingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vendorWHCourierMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VendorWHCourierMappingDTO> findOne(Long id) {
        log.debug("Request to get VendorWHCourierMapping : {}", id);
        return vendorWHCourierMappingRepository.findById(id)
            .map(vendorWHCourierMappingMapper::toDto);
    }

    /**
     * Delete the vendorWHCourierMapping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VendorWHCourierMapping : {}", id);
        vendorWHCourierMappingRepository.deleteById(id);
        vendorWHCourierMappingSearchRepository.deleteById(id);
    }

    /**
     * Search for the vendorWHCourierMapping corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<VendorWHCourierMappingDTO> search(String query) {
        log.debug("Request to search VendorWHCourierMappings for query {}", query);
        return StreamSupport
            .stream(vendorWHCourierMappingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(vendorWHCourierMappingMapper::toDto)
            .collect(Collectors.toList());
    }
}
