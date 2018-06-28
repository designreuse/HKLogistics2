package com.hk.logistics.service.impl;

import com.hk.logistics.service.CourierChannelService;
import com.hk.logistics.domain.CourierChannel;
import com.hk.logistics.repository.CourierChannelRepository;
import com.hk.logistics.repository.search.CourierChannelSearchRepository;
import com.hk.logistics.service.dto.CourierChannelDTO;
import com.hk.logistics.service.mapper.CourierChannelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CourierChannel.
 */
@Service
@Transactional
public class CourierChannelServiceImpl implements CourierChannelService {

    private final Logger log = LoggerFactory.getLogger(CourierChannelServiceImpl.class);

    private final CourierChannelRepository courierChannelRepository;

    private final CourierChannelMapper courierChannelMapper;

    private final CourierChannelSearchRepository courierChannelSearchRepository;

    public CourierChannelServiceImpl(CourierChannelRepository courierChannelRepository, CourierChannelMapper courierChannelMapper, CourierChannelSearchRepository courierChannelSearchRepository) {
        this.courierChannelRepository = courierChannelRepository;
        this.courierChannelMapper = courierChannelMapper;
        this.courierChannelSearchRepository = courierChannelSearchRepository;
    }

    /**
     * Save a courierChannel.
     *
     * @param courierChannelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CourierChannelDTO save(CourierChannelDTO courierChannelDTO) {
        log.debug("Request to save CourierChannel : {}", courierChannelDTO);
        CourierChannel courierChannel = courierChannelMapper.toEntity(courierChannelDTO);
        courierChannel = courierChannelRepository.save(courierChannel);
        CourierChannelDTO result = courierChannelMapper.toDto(courierChannel);
        courierChannelSearchRepository.save(courierChannel);
        return result;
    }

    /**
     * Get all the courierChannels.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CourierChannelDTO> findAll() {
        log.debug("Request to get all CourierChannels");
        return courierChannelRepository.findAll().stream()
            .map(courierChannelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one courierChannel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CourierChannelDTO> findOne(Long id) {
        log.debug("Request to get CourierChannel : {}", id);
        return courierChannelRepository.findById(id)
            .map(courierChannelMapper::toDto);
    }

    /**
     * Delete the courierChannel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourierChannel : {}", id);
        courierChannelRepository.deleteById(id);
        courierChannelSearchRepository.deleteById(id);
    }

    /**
     * Search for the courierChannel corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CourierChannelDTO> search(String query) {
        log.debug("Request to search CourierChannels for query {}", query);
        return StreamSupport
            .stream(courierChannelSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(courierChannelMapper::toDto)
            .collect(Collectors.toList());
    }
}
