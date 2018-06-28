package com.hk.logistics.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.hk.logistics.domain.ShipmentServiceType;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.ShipmentServiceTypeRepository;
import com.hk.logistics.repository.search.ShipmentServiceTypeSearchRepository;
import com.hk.logistics.service.dto.ShipmentServiceTypeCriteria;

import com.hk.logistics.service.dto.ShipmentServiceTypeDTO;
import com.hk.logistics.service.mapper.ShipmentServiceTypeMapper;

/**
 * Service for executing complex queries for ShipmentServiceType entities in the database.
 * The main input is a {@link ShipmentServiceTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ShipmentServiceTypeDTO} or a {@link Page} of {@link ShipmentServiceTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShipmentServiceTypeQueryService extends QueryService<ShipmentServiceType> {

    private final Logger log = LoggerFactory.getLogger(ShipmentServiceTypeQueryService.class);

    private final ShipmentServiceTypeRepository shipmentServiceTypeRepository;

    private final ShipmentServiceTypeMapper shipmentServiceTypeMapper;

    private final ShipmentServiceTypeSearchRepository shipmentServiceTypeSearchRepository;

    public ShipmentServiceTypeQueryService(ShipmentServiceTypeRepository shipmentServiceTypeRepository, ShipmentServiceTypeMapper shipmentServiceTypeMapper, ShipmentServiceTypeSearchRepository shipmentServiceTypeSearchRepository) {
        this.shipmentServiceTypeRepository = shipmentServiceTypeRepository;
        this.shipmentServiceTypeMapper = shipmentServiceTypeMapper;
        this.shipmentServiceTypeSearchRepository = shipmentServiceTypeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ShipmentServiceTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ShipmentServiceTypeDTO> findByCriteria(ShipmentServiceTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ShipmentServiceType> specification = createSpecification(criteria);
        return shipmentServiceTypeMapper.toDto(shipmentServiceTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ShipmentServiceTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShipmentServiceTypeDTO> findByCriteria(ShipmentServiceTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ShipmentServiceType> specification = createSpecification(criteria);
        return shipmentServiceTypeRepository.findAll(specification, page)
            .map(shipmentServiceTypeMapper::toDto);
    }

    /**
     * Function to convert ShipmentServiceTypeCriteria to a {@link Specification}
     */
    private Specification<ShipmentServiceType> createSpecification(ShipmentServiceTypeCriteria criteria) {
        Specification<ShipmentServiceType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ShipmentServiceType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ShipmentServiceType_.name));
            }
        }
        return specification;
    }

}
