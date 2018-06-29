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

import com.hk.logistics.domain.Awb;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.AwbRepository;
import com.hk.logistics.repository.search.AwbSearchRepository;
import com.hk.logistics.service.dto.AwbCriteria;

import com.hk.logistics.service.dto.AwbDTO;
import com.hk.logistics.service.mapper.AwbMapper;

/**
 * Service for executing complex queries for Awb entities in the database.
 * The main input is a {@link AwbCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AwbDTO} or a {@link Page} of {@link AwbDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AwbQueryService extends QueryService<Awb> {

    private final Logger log = LoggerFactory.getLogger(AwbQueryService.class);

    private final AwbRepository awbRepository;

    private final AwbMapper awbMapper;

    private final AwbSearchRepository awbSearchRepository;

    public AwbQueryService(AwbRepository awbRepository, AwbMapper awbMapper, AwbSearchRepository awbSearchRepository) {
        this.awbRepository = awbRepository;
        this.awbMapper = awbMapper;
        this.awbSearchRepository = awbSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AwbDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AwbDTO> findByCriteria(AwbCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Awb> specification = createSpecification(criteria);
        return awbMapper.toDto(awbRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AwbDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AwbDTO> findByCriteria(AwbCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Awb> specification = createSpecification(criteria);
        return awbRepository.findAll(specification, page)
            .map(awbMapper::toDto);
    }

    /**
     * Function to convert AwbCriteria to a {@link Specification}
     */
    private Specification<Awb> createSpecification(AwbCriteria criteria) {
        Specification<Awb> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Awb_.id));
            }
            if (criteria.getAwbNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAwbNumber(), Awb_.awbNumber));
            }
            if (criteria.getAwbBarCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAwbBarCode(), Awb_.awbBarCode));
            }
            if (criteria.getCod() != null) {
                specification = specification.and(buildSpecification(criteria.getCod(), Awb_.cod));
            }
            if (criteria.getCreateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateDate(), Awb_.createDate));
            }
            if (criteria.getReturnAwbNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReturnAwbNumber(), Awb_.returnAwbNumber));
            }
            if (criteria.getReturnAwbBarCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReturnAwbBarCode(), Awb_.returnAwbBarCode));
            }
            if (criteria.getTrackingLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTrackingLink(), Awb_.trackingLink));
            }
            if (criteria.getChannelId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getChannelId(), Awb_.channel, Channel_.id));
            }
            if (criteria.getVendorWHCourierMappingId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getVendorWHCourierMappingId(), Awb_.vendorWHCourierMapping, VendorWHCourierMapping_.id));
            }
            if (criteria.getAwbStatusId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAwbStatusId(), Awb_.awbStatus, AwbStatus_.id));
            }
        }
        return specification;
    }

}
