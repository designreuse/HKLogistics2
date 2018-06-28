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

import com.hk.logistics.domain.Pincode;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.PincodeRepository;
import com.hk.logistics.repository.search.PincodeSearchRepository;
import com.hk.logistics.service.dto.PincodeCriteria;

import com.hk.logistics.service.dto.PincodeDTO;
import com.hk.logistics.service.mapper.PincodeMapper;

/**
 * Service for executing complex queries for Pincode entities in the database.
 * The main input is a {@link PincodeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PincodeDTO} or a {@link Page} of {@link PincodeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PincodeQueryService extends QueryService<Pincode> {

    private final Logger log = LoggerFactory.getLogger(PincodeQueryService.class);

    private final PincodeRepository pincodeRepository;

    private final PincodeMapper pincodeMapper;

    private final PincodeSearchRepository pincodeSearchRepository;

    public PincodeQueryService(PincodeRepository pincodeRepository, PincodeMapper pincodeMapper, PincodeSearchRepository pincodeSearchRepository) {
        this.pincodeRepository = pincodeRepository;
        this.pincodeMapper = pincodeMapper;
        this.pincodeSearchRepository = pincodeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PincodeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PincodeDTO> findByCriteria(PincodeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Pincode> specification = createSpecification(criteria);
        return pincodeMapper.toDto(pincodeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PincodeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PincodeDTO> findByCriteria(PincodeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pincode> specification = createSpecification(criteria);
        return pincodeRepository.findAll(specification, page)
            .map(pincodeMapper::toDto);
    }

    /**
     * Function to convert PincodeCriteria to a {@link Specification}
     */
    private Specification<Pincode> createSpecification(PincodeCriteria criteria) {
        Specification<Pincode> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Pincode_.id));
            }
            if (criteria.getPincode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPincode(), Pincode_.pincode));
            }
            if (criteria.getRegion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegion(), Pincode_.region));
            }
            if (criteria.getLocality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocality(), Pincode_.locality));
            }
            if (criteria.getLastMileCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastMileCost(), Pincode_.lastMileCost));
            }
            if (criteria.getTier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTier(), Pincode_.tier));
            }
            if (criteria.getCityId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCityId(), Pincode_.city, City_.id));
            }
            if (criteria.getStateId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStateId(), Pincode_.state, State_.id));
            }
            if (criteria.getZoneId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getZoneId(), Pincode_.zone, Zone_.id));
            }
            if (criteria.getHubId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getHubId(), Pincode_.hub, Hub_.id));
            }
        }
        return specification;
    }

}
