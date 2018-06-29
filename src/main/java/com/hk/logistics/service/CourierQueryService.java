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

import com.hk.logistics.domain.Courier;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.CourierRepository;
import com.hk.logistics.repository.search.CourierSearchRepository;
import com.hk.logistics.service.dto.CourierCriteria;

import com.hk.logistics.service.dto.CourierDTO;
import com.hk.logistics.service.mapper.CourierMapper;

/**
 * Service for executing complex queries for Courier entities in the database.
 * The main input is a {@link CourierCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourierDTO} or a {@link Page} of {@link CourierDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourierQueryService extends QueryService<Courier> {

    private final Logger log = LoggerFactory.getLogger(CourierQueryService.class);

    private final CourierRepository courierRepository;

    private final CourierMapper courierMapper;

    private final CourierSearchRepository courierSearchRepository;

    public CourierQueryService(CourierRepository courierRepository, CourierMapper courierMapper, CourierSearchRepository courierSearchRepository) {
        this.courierRepository = courierRepository;
        this.courierMapper = courierMapper;
        this.courierSearchRepository = courierSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CourierDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourierDTO> findByCriteria(CourierCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Courier> specification = createSpecification(criteria);
        return courierMapper.toDto(courierRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourierDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourierDTO> findByCriteria(CourierCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Courier> specification = createSpecification(criteria);
        return courierRepository.findAll(specification, page)
            .map(courierMapper::toDto);
    }

    /**
     * Function to convert CourierCriteria to a {@link Specification}
     */
    private Specification<Courier> createSpecification(CourierCriteria criteria) {
        Specification<Courier> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Courier_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Courier_.name));
            }
            if (criteria.getShortCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortCode(), Courier_.shortCode));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Courier_.active));
            }
            if (criteria.getParentCourierId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParentCourierId(), Courier_.parentCourierId));
            }
            if (criteria.getHkShipping() != null) {
                specification = specification.and(buildSpecification(criteria.getHkShipping(), Courier_.hkShipping));
            }
            if (criteria.getVendorShipping() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorShipping(), Courier_.vendorShipping));
            }
            if (criteria.getReversePickup() != null) {
                specification = specification.and(buildSpecification(criteria.getReversePickup(), Courier_.reversePickup));
            }
            if (criteria.getVendorWHCourierMappingId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getVendorWHCourierMappingId(), Courier_.vendorWHCourierMappings, VendorWHCourierMapping_.id));
            }
            if (criteria.getCourierChannelId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCourierChannelId(), Courier_.courierChannels, CourierChannel_.id));
            }
            if (criteria.getCourierGroupId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCourierGroupId(), Courier_.courierGroup, CourierGroup_.id));
            }
        }
        return specification;
    }

}
