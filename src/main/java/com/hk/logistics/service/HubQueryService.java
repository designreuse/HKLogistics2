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

import com.hk.logistics.domain.Hub;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.HubRepository;
import com.hk.logistics.repository.search.HubSearchRepository;
import com.hk.logistics.service.dto.HubCriteria;

import com.hk.logistics.service.dto.HubDTO;
import com.hk.logistics.service.mapper.HubMapper;

/**
 * Service for executing complex queries for Hub entities in the database.
 * The main input is a {@link HubCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HubDTO} or a {@link Page} of {@link HubDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HubQueryService extends QueryService<Hub> {

    private final Logger log = LoggerFactory.getLogger(HubQueryService.class);

    private final HubRepository hubRepository;

    private final HubMapper hubMapper;

    private final HubSearchRepository hubSearchRepository;

    public HubQueryService(HubRepository hubRepository, HubMapper hubMapper, HubSearchRepository hubSearchRepository) {
        this.hubRepository = hubRepository;
        this.hubMapper = hubMapper;
        this.hubSearchRepository = hubSearchRepository;
    }

    /**
     * Return a {@link List} of {@link HubDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HubDTO> findByCriteria(HubCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Hub> specification = createSpecification(criteria);
        return hubMapper.toDto(hubRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HubDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HubDTO> findByCriteria(HubCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Hub> specification = createSpecification(criteria);
        return hubRepository.findAll(specification, page)
            .map(hubMapper::toDto);
    }

    /**
     * Function to convert HubCriteria to a {@link Specification}
     */
    private Specification<Hub> createSpecification(HubCriteria criteria) {
        Specification<Hub> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Hub_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Hub_.name));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Hub_.address));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), Hub_.country));
            }
            if (criteria.getPinCodeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPinCodeId(), Hub_.pinCodes, Pincode_.id));
            }
        }
        return specification;
    }

}
