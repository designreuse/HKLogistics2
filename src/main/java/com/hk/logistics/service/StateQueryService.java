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

import com.hk.logistics.domain.State;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.StateRepository;
import com.hk.logistics.repository.search.StateSearchRepository;
import com.hk.logistics.service.dto.StateCriteria;

import com.hk.logistics.service.dto.StateDTO;
import com.hk.logistics.service.mapper.StateMapper;

/**
 * Service for executing complex queries for State entities in the database.
 * The main input is a {@link StateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StateDTO} or a {@link Page} of {@link StateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StateQueryService extends QueryService<State> {

    private final Logger log = LoggerFactory.getLogger(StateQueryService.class);

    private final StateRepository stateRepository;

    private final StateMapper stateMapper;

    private final StateSearchRepository stateSearchRepository;

    public StateQueryService(StateRepository stateRepository, StateMapper stateMapper, StateSearchRepository stateSearchRepository) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
        this.stateSearchRepository = stateSearchRepository;
    }

    /**
     * Return a {@link List} of {@link StateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StateDTO> findByCriteria(StateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<State> specification = createSpecification(criteria);
        return stateMapper.toDto(stateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StateDTO> findByCriteria(StateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<State> specification = createSpecification(criteria);
        return stateRepository.findAll(specification, page)
            .map(stateMapper::toDto);
    }

    /**
     * Function to convert StateCriteria to a {@link Specification}
     */
    private Specification<State> createSpecification(StateCriteria criteria) {
        Specification<State> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), State_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), State_.name));
            }
            if (criteria.getIdentifier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdentifier(), State_.identifier));
            }
            if (criteria.getUnionTerritory() != null) {
                specification = specification.and(buildSpecification(criteria.getUnionTerritory(), State_.unionTerritory));
            }
        }
        return specification;
    }

}
