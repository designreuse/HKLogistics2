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

import com.hk.logistics.domain.CourierPricingEngine;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.CourierPricingEngineRepository;
import com.hk.logistics.repository.search.CourierPricingEngineSearchRepository;
import com.hk.logistics.service.dto.CourierPricingEngineCriteria;

import com.hk.logistics.service.dto.CourierPricingEngineDTO;
import com.hk.logistics.service.mapper.CourierPricingEngineMapper;

/**
 * Service for executing complex queries for CourierPricingEngine entities in the database.
 * The main input is a {@link CourierPricingEngineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourierPricingEngineDTO} or a {@link Page} of {@link CourierPricingEngineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourierPricingEngineQueryService extends QueryService<CourierPricingEngine> {

    private final Logger log = LoggerFactory.getLogger(CourierPricingEngineQueryService.class);

    private final CourierPricingEngineRepository courierPricingEngineRepository;

    private final CourierPricingEngineMapper courierPricingEngineMapper;

    private final CourierPricingEngineSearchRepository courierPricingEngineSearchRepository;

    public CourierPricingEngineQueryService(CourierPricingEngineRepository courierPricingEngineRepository, CourierPricingEngineMapper courierPricingEngineMapper, CourierPricingEngineSearchRepository courierPricingEngineSearchRepository) {
        this.courierPricingEngineRepository = courierPricingEngineRepository;
        this.courierPricingEngineMapper = courierPricingEngineMapper;
        this.courierPricingEngineSearchRepository = courierPricingEngineSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CourierPricingEngineDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourierPricingEngineDTO> findByCriteria(CourierPricingEngineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourierPricingEngine> specification = createSpecification(criteria);
        return courierPricingEngineMapper.toDto(courierPricingEngineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourierPricingEngineDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourierPricingEngineDTO> findByCriteria(CourierPricingEngineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourierPricingEngine> specification = createSpecification(criteria);
        return courierPricingEngineRepository.findAll(specification, page)
            .map(courierPricingEngineMapper::toDto);
    }

    /**
     * Function to convert CourierPricingEngineCriteria to a {@link Specification}
     */
    private Specification<CourierPricingEngine> createSpecification(CourierPricingEngineCriteria criteria) {
        Specification<CourierPricingEngine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CourierPricingEngine_.id));
            }
            if (criteria.getFirstBaseWt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstBaseWt(), CourierPricingEngine_.firstBaseWt));
            }
            if (criteria.getFirstBaseCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstBaseCost(), CourierPricingEngine_.firstBaseCost));
            }
            if (criteria.getSecondBaseWt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSecondBaseWt(), CourierPricingEngine_.secondBaseWt));
            }
            if (criteria.getSecondBaseCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSecondBaseCost(), CourierPricingEngine_.secondBaseCost));
            }
            if (criteria.getThirdBaseWt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThirdBaseWt(), CourierPricingEngine_.thirdBaseWt));
            }
            if (criteria.getThirdBaseCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThirdBaseCost(), CourierPricingEngine_.thirdBaseCost));
            }
            if (criteria.getAdditionalWt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdditionalWt(), CourierPricingEngine_.additionalWt));
            }
            if (criteria.getAdditionalCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdditionalCost(), CourierPricingEngine_.additionalCost));
            }
            if (criteria.getFuelSurcharge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFuelSurcharge(), CourierPricingEngine_.fuelSurcharge));
            }
            if (criteria.getMinCodCharges() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinCodCharges(), CourierPricingEngine_.minCodCharges));
            }
            if (criteria.getCodCutoffAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCodCutoffAmount(), CourierPricingEngine_.codCutoffAmount));
            }
            if (criteria.getVariableCodCharges() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVariableCodCharges(), CourierPricingEngine_.variableCodCharges));
            }
            if (criteria.getValidUpto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidUpto(), CourierPricingEngine_.validUpto));
            }
            if (criteria.getCostParameters() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCostParameters(), CourierPricingEngine_.costParameters));
            }
            if (criteria.getCourierId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCourierId(), CourierPricingEngine_.courier, Courier_.id));
            }
            if (criteria.getRegionTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getRegionTypeId(), CourierPricingEngine_.regionType, RegionType_.id));
            }
        }
        return specification;
    }

}
