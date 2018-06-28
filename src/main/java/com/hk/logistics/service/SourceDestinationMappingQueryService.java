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

import com.hk.logistics.domain.SourceDestinationMapping;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.SourceDestinationMappingRepository;
import com.hk.logistics.repository.search.SourceDestinationMappingSearchRepository;
import com.hk.logistics.service.dto.SourceDestinationMappingCriteria;

import com.hk.logistics.service.dto.SourceDestinationMappingDTO;
import com.hk.logistics.service.mapper.SourceDestinationMappingMapper;

/**
 * Service for executing complex queries for SourceDestinationMapping entities in the database.
 * The main input is a {@link SourceDestinationMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SourceDestinationMappingDTO} or a {@link Page} of {@link SourceDestinationMappingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SourceDestinationMappingQueryService extends QueryService<SourceDestinationMapping> {

    private final Logger log = LoggerFactory.getLogger(SourceDestinationMappingQueryService.class);

    private final SourceDestinationMappingRepository sourceDestinationMappingRepository;

    private final SourceDestinationMappingMapper sourceDestinationMappingMapper;

    private final SourceDestinationMappingSearchRepository sourceDestinationMappingSearchRepository;

    public SourceDestinationMappingQueryService(SourceDestinationMappingRepository sourceDestinationMappingRepository, SourceDestinationMappingMapper sourceDestinationMappingMapper, SourceDestinationMappingSearchRepository sourceDestinationMappingSearchRepository) {
        this.sourceDestinationMappingRepository = sourceDestinationMappingRepository;
        this.sourceDestinationMappingMapper = sourceDestinationMappingMapper;
        this.sourceDestinationMappingSearchRepository = sourceDestinationMappingSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SourceDestinationMappingDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SourceDestinationMappingDTO> findByCriteria(SourceDestinationMappingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SourceDestinationMapping> specification = createSpecification(criteria);
        return sourceDestinationMappingMapper.toDto(sourceDestinationMappingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SourceDestinationMappingDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SourceDestinationMappingDTO> findByCriteria(SourceDestinationMappingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SourceDestinationMapping> specification = createSpecification(criteria);
        return sourceDestinationMappingRepository.findAll(specification, page)
            .map(sourceDestinationMappingMapper::toDto);
    }

    /**
     * Function to convert SourceDestinationMappingCriteria to a {@link Specification}
     */
    private Specification<SourceDestinationMapping> createSpecification(SourceDestinationMappingCriteria criteria) {
        Specification<SourceDestinationMapping> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SourceDestinationMapping_.id));
            }
            if (criteria.getSourcePincode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSourcePincode(), SourceDestinationMapping_.sourcePincode));
            }
            if (criteria.getDestinationPincode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDestinationPincode(), SourceDestinationMapping_.destinationPincode));
            }
        }
        return specification;
    }

}
