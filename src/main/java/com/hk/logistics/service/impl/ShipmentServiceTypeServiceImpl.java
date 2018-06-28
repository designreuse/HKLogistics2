package com.hk.logistics.service.impl;

import com.hk.logistics.service.ShipmentServiceTypeService;
import com.hk.logistics.domain.ShipmentServiceType;
import com.hk.logistics.repository.ShipmentServiceTypeRepository;
import com.hk.logistics.repository.search.ShipmentServiceTypeSearchRepository;
import com.hk.logistics.service.dto.ShipmentServiceTypeDTO;
import com.hk.logistics.service.mapper.ShipmentServiceTypeMapper;
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
 * Service Implementation for managing ShipmentServiceType.
 */
@Service
@Transactional
public class ShipmentServiceTypeServiceImpl implements ShipmentServiceTypeService {

    private final Logger log = LoggerFactory.getLogger(ShipmentServiceTypeServiceImpl.class);

    private final ShipmentServiceTypeRepository shipmentServiceTypeRepository;

    private final ShipmentServiceTypeMapper shipmentServiceTypeMapper;

    private final ShipmentServiceTypeSearchRepository shipmentServiceTypeSearchRepository;

    public ShipmentServiceTypeServiceImpl(ShipmentServiceTypeRepository shipmentServiceTypeRepository, ShipmentServiceTypeMapper shipmentServiceTypeMapper, ShipmentServiceTypeSearchRepository shipmentServiceTypeSearchRepository) {
        this.shipmentServiceTypeRepository = shipmentServiceTypeRepository;
        this.shipmentServiceTypeMapper = shipmentServiceTypeMapper;
        this.shipmentServiceTypeSearchRepository = shipmentServiceTypeSearchRepository;
    }

    /**
     * Save a shipmentServiceType.
     *
     * @param shipmentServiceTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentServiceTypeDTO save(ShipmentServiceTypeDTO shipmentServiceTypeDTO) {
        log.debug("Request to save ShipmentServiceType : {}", shipmentServiceTypeDTO);
        ShipmentServiceType shipmentServiceType = shipmentServiceTypeMapper.toEntity(shipmentServiceTypeDTO);
        shipmentServiceType = shipmentServiceTypeRepository.save(shipmentServiceType);
        ShipmentServiceTypeDTO result = shipmentServiceTypeMapper.toDto(shipmentServiceType);
        shipmentServiceTypeSearchRepository.save(shipmentServiceType);
        return result;
    }

    /**
     * Get all the shipmentServiceTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentServiceTypeDTO> findAll() {
        log.debug("Request to get all ShipmentServiceTypes");
        return shipmentServiceTypeRepository.findAll().stream()
            .map(shipmentServiceTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one shipmentServiceType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentServiceTypeDTO> findOne(Long id) {
        log.debug("Request to get ShipmentServiceType : {}", id);
        return shipmentServiceTypeRepository.findById(id)
            .map(shipmentServiceTypeMapper::toDto);
    }

    /**
     * Delete the shipmentServiceType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentServiceType : {}", id);
        shipmentServiceTypeRepository.deleteById(id);
        shipmentServiceTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the shipmentServiceType corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentServiceTypeDTO> search(String query) {
        log.debug("Request to search ShipmentServiceTypes for query {}", query);
        return StreamSupport
            .stream(shipmentServiceTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(shipmentServiceTypeMapper::toDto)
            .collect(Collectors.toList());
    }
}
