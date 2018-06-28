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

import com.hk.logistics.domain.PincodeRegionZone;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.PincodeRegionZoneRepository;
import com.hk.logistics.repository.search.PincodeRegionZoneSearchRepository;
import com.hk.logistics.service.dto.PincodeRegionZoneCriteria;

import com.hk.logistics.service.dto.PincodeRegionZoneDTO;
import com.hk.logistics.service.mapper.PincodeRegionZoneMapper;

/**
 * Service for executing complex queries for PincodeRegionZone entities in the database.
 * The main input is a {@link PincodeRegionZoneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PincodeRegionZoneDTO} or a {@link Page} of {@link PincodeRegionZoneDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PincodeRegionZoneQueryService extends QueryService<PincodeRegionZone> {

    private final Logger log = LoggerFactory.getLogger(PincodeRegionZoneQueryService.class);

    private final PincodeRegionZoneRepository pincodeRegionZoneRepository;

    private final PincodeRegionZoneMapper pincodeRegionZoneMapper;

    private final PincodeRegionZoneSearchRepository pincodeRegionZoneSearchRepository;

    public PincodeRegionZoneQueryService(PincodeRegionZoneRepository pincodeRegionZoneRepository, PincodeRegionZoneMapper pincodeRegionZoneMapper, PincodeRegionZoneSearchRepository pincodeRegionZoneSearchRepository) {
        this.pincodeRegionZoneRepository = pincodeRegionZoneRepository;
        this.pincodeRegionZoneMapper = pincodeRegionZoneMapper;
        this.pincodeRegionZoneSearchRepository = pincodeRegionZoneSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PincodeRegionZoneDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PincodeRegionZoneDTO> findByCriteria(PincodeRegionZoneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PincodeRegionZone> specification = createSpecification(criteria);
        return pincodeRegionZoneMapper.toDto(pincodeRegionZoneRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PincodeRegionZoneDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PincodeRegionZoneDTO> findByCriteria(PincodeRegionZoneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PincodeRegionZone> specification = createSpecification(criteria);
        return pincodeRegionZoneRepository.findAll(specification, page)
            .map(pincodeRegionZoneMapper::toDto);
    }

    /**
     * Function to convert PincodeRegionZoneCriteria to a {@link Specification}
     */
    private Specification<PincodeRegionZone> createSpecification(PincodeRegionZoneCriteria criteria) {
        Specification<PincodeRegionZone> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PincodeRegionZone_.id));
            }
            if (criteria.getRegionTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getRegionTypeId(), PincodeRegionZone_.regionType, RegionType_.id));
            }
            if (criteria.getCourierGroupId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCourierGroupId(), PincodeRegionZone_.courierGroup, CourierGroup_.id));
            }
            if (criteria.getSourceDestinationMappingId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSourceDestinationMappingId(), PincodeRegionZone_.sourceDestinationMapping, SourceDestinationMapping_.id));
            }
        }
        return specification;
    }

}
