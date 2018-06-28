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

import com.hk.logistics.domain.VendorWHCourierMapping;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.VendorWHCourierMappingRepository;
import com.hk.logistics.repository.search.VendorWHCourierMappingSearchRepository;
import com.hk.logistics.service.dto.VendorWHCourierMappingCriteria;

import com.hk.logistics.service.dto.VendorWHCourierMappingDTO;
import com.hk.logistics.service.mapper.VendorWHCourierMappingMapper;

/**
 * Service for executing complex queries for VendorWHCourierMapping entities in the database.
 * The main input is a {@link VendorWHCourierMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VendorWHCourierMappingDTO} or a {@link Page} of {@link VendorWHCourierMappingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VendorWHCourierMappingQueryService extends QueryService<VendorWHCourierMapping> {

    private final Logger log = LoggerFactory.getLogger(VendorWHCourierMappingQueryService.class);

    private final VendorWHCourierMappingRepository vendorWHCourierMappingRepository;

    private final VendorWHCourierMappingMapper vendorWHCourierMappingMapper;

    private final VendorWHCourierMappingSearchRepository vendorWHCourierMappingSearchRepository;

    public VendorWHCourierMappingQueryService(VendorWHCourierMappingRepository vendorWHCourierMappingRepository, VendorWHCourierMappingMapper vendorWHCourierMappingMapper, VendorWHCourierMappingSearchRepository vendorWHCourierMappingSearchRepository) {
        this.vendorWHCourierMappingRepository = vendorWHCourierMappingRepository;
        this.vendorWHCourierMappingMapper = vendorWHCourierMappingMapper;
        this.vendorWHCourierMappingSearchRepository = vendorWHCourierMappingSearchRepository;
    }

    /**
     * Return a {@link List} of {@link VendorWHCourierMappingDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VendorWHCourierMappingDTO> findByCriteria(VendorWHCourierMappingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VendorWHCourierMapping> specification = createSpecification(criteria);
        return vendorWHCourierMappingMapper.toDto(vendorWHCourierMappingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VendorWHCourierMappingDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VendorWHCourierMappingDTO> findByCriteria(VendorWHCourierMappingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VendorWHCourierMapping> specification = createSpecification(criteria);
        return vendorWHCourierMappingRepository.findAll(specification, page)
            .map(vendorWHCourierMappingMapper::toDto);
    }

    /**
     * Function to convert VendorWHCourierMappingCriteria to a {@link Specification}
     */
    private Specification<VendorWHCourierMapping> createSpecification(VendorWHCourierMappingCriteria criteria) {
        Specification<VendorWHCourierMapping> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), VendorWHCourierMapping_.id));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), VendorWHCourierMapping_.active));
            }
            if (criteria.getVendor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendor(), VendorWHCourierMapping_.vendor));
            }
            if (criteria.getWarehouse() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWarehouse(), VendorWHCourierMapping_.warehouse));
            }
            if (criteria.getCourierChannelId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCourierChannelId(), VendorWHCourierMapping_.courierChannel, CourierChannel_.id));
            }
        }
        return specification;
    }

}
