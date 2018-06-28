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

import com.hk.logistics.domain.RegionType;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.RegionTypeRepository;
import com.hk.logistics.repository.search.RegionTypeSearchRepository;
import com.hk.logistics.service.dto.RegionTypeCriteria;

import com.hk.logistics.service.dto.RegionTypeDTO;
import com.hk.logistics.service.mapper.RegionTypeMapper;

/**
 * Service for executing complex queries for RegionType entities in the database.
 * The main input is a {@link RegionTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegionTypeDTO} or a {@link Page} of {@link RegionTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegionTypeQueryService extends QueryService<RegionType> {

    private final Logger log = LoggerFactory.getLogger(RegionTypeQueryService.class);

    private final RegionTypeRepository regionTypeRepository;

    private final RegionTypeMapper regionTypeMapper;

    private final RegionTypeSearchRepository regionTypeSearchRepository;

    public RegionTypeQueryService(RegionTypeRepository regionTypeRepository, RegionTypeMapper regionTypeMapper, RegionTypeSearchRepository regionTypeSearchRepository) {
        this.regionTypeRepository = regionTypeRepository;
        this.regionTypeMapper = regionTypeMapper;
        this.regionTypeSearchRepository = regionTypeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RegionTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegionTypeDTO> findByCriteria(RegionTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegionType> specification = createSpecification(criteria);
        return regionTypeMapper.toDto(regionTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RegionTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegionTypeDTO> findByCriteria(RegionTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RegionType> specification = createSpecification(criteria);
        return regionTypeRepository.findAll(specification, page)
            .map(regionTypeMapper::toDto);
    }

    /**
     * Function to convert RegionTypeCriteria to a {@link Specification}
     */
    private Specification<RegionType> createSpecification(RegionTypeCriteria criteria) {
        Specification<RegionType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RegionType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), RegionType_.name));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriority(), RegionType_.priority));
            }
        }
        return specification;
    }

}
