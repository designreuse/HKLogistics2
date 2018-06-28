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

import com.hk.logistics.domain.AwbStatus;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.AwbStatusRepository;
import com.hk.logistics.repository.search.AwbStatusSearchRepository;
import com.hk.logistics.service.dto.AwbStatusCriteria;

import com.hk.logistics.service.dto.AwbStatusDTO;
import com.hk.logistics.service.mapper.AwbStatusMapper;

/**
 * Service for executing complex queries for AwbStatus entities in the database.
 * The main input is a {@link AwbStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AwbStatusDTO} or a {@link Page} of {@link AwbStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AwbStatusQueryService extends QueryService<AwbStatus> {

    private final Logger log = LoggerFactory.getLogger(AwbStatusQueryService.class);

    private final AwbStatusRepository awbStatusRepository;

    private final AwbStatusMapper awbStatusMapper;

    private final AwbStatusSearchRepository awbStatusSearchRepository;

    public AwbStatusQueryService(AwbStatusRepository awbStatusRepository, AwbStatusMapper awbStatusMapper, AwbStatusSearchRepository awbStatusSearchRepository) {
        this.awbStatusRepository = awbStatusRepository;
        this.awbStatusMapper = awbStatusMapper;
        this.awbStatusSearchRepository = awbStatusSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AwbStatusDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AwbStatusDTO> findByCriteria(AwbStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AwbStatus> specification = createSpecification(criteria);
        return awbStatusMapper.toDto(awbStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AwbStatusDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AwbStatusDTO> findByCriteria(AwbStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AwbStatus> specification = createSpecification(criteria);
        return awbStatusRepository.findAll(specification, page)
            .map(awbStatusMapper::toDto);
    }

    /**
     * Function to convert AwbStatusCriteria to a {@link Specification}
     */
    private Specification<AwbStatus> createSpecification(AwbStatusCriteria criteria) {
        Specification<AwbStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AwbStatus_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), AwbStatus_.status));
            }
        }
        return specification;
    }

}
