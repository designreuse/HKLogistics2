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

import com.hk.logistics.domain.CourierGroup;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.CourierGroupRepository;
import com.hk.logistics.repository.search.CourierGroupSearchRepository;
import com.hk.logistics.service.dto.CourierGroupCriteria;

import com.hk.logistics.service.dto.CourierGroupDTO;
import com.hk.logistics.service.mapper.CourierGroupMapper;

/**
 * Service for executing complex queries for CourierGroup entities in the database.
 * The main input is a {@link CourierGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourierGroupDTO} or a {@link Page} of {@link CourierGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourierGroupQueryService extends QueryService<CourierGroup> {

    private final Logger log = LoggerFactory.getLogger(CourierGroupQueryService.class);

    private final CourierGroupRepository courierGroupRepository;

    private final CourierGroupMapper courierGroupMapper;

    private final CourierGroupSearchRepository courierGroupSearchRepository;

    public CourierGroupQueryService(CourierGroupRepository courierGroupRepository, CourierGroupMapper courierGroupMapper, CourierGroupSearchRepository courierGroupSearchRepository) {
        this.courierGroupRepository = courierGroupRepository;
        this.courierGroupMapper = courierGroupMapper;
        this.courierGroupSearchRepository = courierGroupSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CourierGroupDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourierGroupDTO> findByCriteria(CourierGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourierGroup> specification = createSpecification(criteria);
        return courierGroupMapper.toDto(courierGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourierGroupDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourierGroupDTO> findByCriteria(CourierGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourierGroup> specification = createSpecification(criteria);
        return courierGroupRepository.findAll(specification, page)
            .map(courierGroupMapper::toDto);
    }

    /**
     * Function to convert CourierGroupCriteria to a {@link Specification}
     */
    private Specification<CourierGroup> createSpecification(CourierGroupCriteria criteria) {
        Specification<CourierGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CourierGroup_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CourierGroup_.name));
            }
            if (criteria.getCourierId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCourierId(), CourierGroup_.couriers, Courier_.id));
            }
        }
        return specification;
    }

}
