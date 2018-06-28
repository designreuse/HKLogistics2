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

import com.hk.logistics.domain.PincodeCourierMapping;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.PincodeCourierMappingRepository;
import com.hk.logistics.repository.search.PincodeCourierMappingSearchRepository;
import com.hk.logistics.service.dto.PincodeCourierMappingCriteria;

import com.hk.logistics.service.dto.PincodeCourierMappingDTO;
import com.hk.logistics.service.mapper.PincodeCourierMappingMapper;

/**
 * Service for executing complex queries for PincodeCourierMapping entities in the database.
 * The main input is a {@link PincodeCourierMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PincodeCourierMappingDTO} or a {@link Page} of {@link PincodeCourierMappingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PincodeCourierMappingQueryService extends QueryService<PincodeCourierMapping> {

    private final Logger log = LoggerFactory.getLogger(PincodeCourierMappingQueryService.class);

    private final PincodeCourierMappingRepository pincodeCourierMappingRepository;

    private final PincodeCourierMappingMapper pincodeCourierMappingMapper;

    private final PincodeCourierMappingSearchRepository pincodeCourierMappingSearchRepository;

    public PincodeCourierMappingQueryService(PincodeCourierMappingRepository pincodeCourierMappingRepository, PincodeCourierMappingMapper pincodeCourierMappingMapper, PincodeCourierMappingSearchRepository pincodeCourierMappingSearchRepository) {
        this.pincodeCourierMappingRepository = pincodeCourierMappingRepository;
        this.pincodeCourierMappingMapper = pincodeCourierMappingMapper;
        this.pincodeCourierMappingSearchRepository = pincodeCourierMappingSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PincodeCourierMappingDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PincodeCourierMappingDTO> findByCriteria(PincodeCourierMappingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PincodeCourierMapping> specification = createSpecification(criteria);
        return pincodeCourierMappingMapper.toDto(pincodeCourierMappingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PincodeCourierMappingDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PincodeCourierMappingDTO> findByCriteria(PincodeCourierMappingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PincodeCourierMapping> specification = createSpecification(criteria);
        return pincodeCourierMappingRepository.findAll(specification, page)
            .map(pincodeCourierMappingMapper::toDto);
    }

    /**
     * Function to convert PincodeCourierMappingCriteria to a {@link Specification}
     */
    private Specification<PincodeCourierMapping> createSpecification(PincodeCourierMappingCriteria criteria) {
        Specification<PincodeCourierMapping> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PincodeCourierMapping_.id));
            }
            if (criteria.getRoutingCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoutingCode(), PincodeCourierMapping_.routingCode));
            }
            if (criteria.getApplicableForCheapestCourier() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicableForCheapestCourier(), PincodeCourierMapping_.applicableForCheapestCourier));
            }
            if (criteria.getEstimatedDeliveryDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimatedDeliveryDays(), PincodeCourierMapping_.estimatedDeliveryDays));
            }
            if (criteria.getPickupAvailable() != null) {
                specification = specification.and(buildSpecification(criteria.getPickupAvailable(), PincodeCourierMapping_.pickupAvailable));
            }
            if (criteria.getPrepaidAir() != null) {
                specification = specification.and(buildSpecification(criteria.getPrepaidAir(), PincodeCourierMapping_.prepaidAir));
            }
            if (criteria.getPrepaidGround() != null) {
                specification = specification.and(buildSpecification(criteria.getPrepaidGround(), PincodeCourierMapping_.prepaidGround));
            }
            if (criteria.getCodAir() != null) {
                specification = specification.and(buildSpecification(criteria.getCodAir(), PincodeCourierMapping_.codAir));
            }
            if (criteria.getCodGround() != null) {
                specification = specification.and(buildSpecification(criteria.getCodGround(), PincodeCourierMapping_.codGround));
            }
            if (criteria.getReverseAir() != null) {
                specification = specification.and(buildSpecification(criteria.getReverseAir(), PincodeCourierMapping_.reverseAir));
            }
            if (criteria.getReverseGround() != null) {
                specification = specification.and(buildSpecification(criteria.getReverseGround(), PincodeCourierMapping_.reverseGround));
            }
            if (criteria.getCardOnDeliveryAir() != null) {
                specification = specification.and(buildSpecification(criteria.getCardOnDeliveryAir(), PincodeCourierMapping_.cardOnDeliveryAir));
            }
            if (criteria.getCardOnDeliveryGround() != null) {
                specification = specification.and(buildSpecification(criteria.getCardOnDeliveryGround(), PincodeCourierMapping_.cardOnDeliveryGround));
            }
            if (criteria.getDeliveryTypeOne() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryTypeOne(), PincodeCourierMapping_.deliveryTypeOne));
            }
            if (criteria.getDeliveryTypeTwo() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryTypeTwo(), PincodeCourierMapping_.deliveryTypeTwo));
            }
            if (criteria.getPincodeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPincodeId(), PincodeCourierMapping_.pincode, Pincode_.id));
            }
            if (criteria.getVendorWHCourierMappingId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getVendorWHCourierMappingId(), PincodeCourierMapping_.vendorWHCourierMapping, VendorWHCourierMapping_.id));
            }
            if (criteria.getSourceDestinationMappingId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSourceDestinationMappingId(), PincodeCourierMapping_.sourceDestinationMapping, SourceDestinationMapping_.id));
            }
        }
        return specification;
    }

}
