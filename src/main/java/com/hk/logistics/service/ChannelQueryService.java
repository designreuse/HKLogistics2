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

import com.hk.logistics.domain.Channel;
import com.hk.logistics.domain.*; // for static metamodels
import com.hk.logistics.repository.ChannelRepository;
import com.hk.logistics.repository.search.ChannelSearchRepository;
import com.hk.logistics.service.dto.ChannelCriteria;

import com.hk.logistics.service.dto.ChannelDTO;
import com.hk.logistics.service.mapper.ChannelMapper;

/**
 * Service for executing complex queries for Channel entities in the database.
 * The main input is a {@link ChannelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChannelDTO} or a {@link Page} of {@link ChannelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChannelQueryService extends QueryService<Channel> {

    private final Logger log = LoggerFactory.getLogger(ChannelQueryService.class);

    private final ChannelRepository channelRepository;

    private final ChannelMapper channelMapper;

    private final ChannelSearchRepository channelSearchRepository;

    public ChannelQueryService(ChannelRepository channelRepository, ChannelMapper channelMapper, ChannelSearchRepository channelSearchRepository) {
        this.channelRepository = channelRepository;
        this.channelMapper = channelMapper;
        this.channelSearchRepository = channelSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ChannelDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChannelDTO> findByCriteria(ChannelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Channel> specification = createSpecification(criteria);
        return channelMapper.toDto(channelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ChannelDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChannelDTO> findByCriteria(ChannelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Channel> specification = createSpecification(criteria);
        return channelRepository.findAll(specification, page)
            .map(channelMapper::toDto);
    }

    /**
     * Function to convert ChannelCriteria to a {@link Specification}
     */
    private Specification<Channel> createSpecification(ChannelCriteria criteria) {
        Specification<Channel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Channel_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Channel_.name));
            }
            if (criteria.getStore() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStore(), Channel_.store));
            }
            if (criteria.getCourierChannelId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCourierChannelId(), Channel_.courierChannels, CourierChannel_.id));
            }
        }
        return specification;
    }

}
