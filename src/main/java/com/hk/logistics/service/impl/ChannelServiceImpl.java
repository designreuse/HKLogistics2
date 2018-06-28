package com.hk.logistics.service.impl;

import com.hk.logistics.service.ChannelService;
import com.hk.logistics.domain.Channel;
import com.hk.logistics.repository.ChannelRepository;
import com.hk.logistics.repository.search.ChannelSearchRepository;
import com.hk.logistics.service.dto.ChannelDTO;
import com.hk.logistics.service.mapper.ChannelMapper;
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
 * Service Implementation for managing Channel.
 */
@Service
@Transactional
public class ChannelServiceImpl implements ChannelService {

    private final Logger log = LoggerFactory.getLogger(ChannelServiceImpl.class);

    private final ChannelRepository channelRepository;

    private final ChannelMapper channelMapper;

    private final ChannelSearchRepository channelSearchRepository;

    public ChannelServiceImpl(ChannelRepository channelRepository, ChannelMapper channelMapper, ChannelSearchRepository channelSearchRepository) {
        this.channelRepository = channelRepository;
        this.channelMapper = channelMapper;
        this.channelSearchRepository = channelSearchRepository;
    }

    /**
     * Save a channel.
     *
     * @param channelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ChannelDTO save(ChannelDTO channelDTO) {
        log.debug("Request to save Channel : {}", channelDTO);
        Channel channel = channelMapper.toEntity(channelDTO);
        channel = channelRepository.save(channel);
        ChannelDTO result = channelMapper.toDto(channel);
        channelSearchRepository.save(channel);
        return result;
    }

    /**
     * Get all the channels.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ChannelDTO> findAll() {
        log.debug("Request to get all Channels");
        return channelRepository.findAll().stream()
            .map(channelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one channel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ChannelDTO> findOne(Long id) {
        log.debug("Request to get Channel : {}", id);
        return channelRepository.findById(id)
            .map(channelMapper::toDto);
    }

    /**
     * Delete the channel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Channel : {}", id);
        channelRepository.deleteById(id);
        channelSearchRepository.deleteById(id);
    }

    /**
     * Search for the channel corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ChannelDTO> search(String query) {
        log.debug("Request to search Channels for query {}", query);
        return StreamSupport
            .stream(channelSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(channelMapper::toDto)
            .collect(Collectors.toList());
    }
}
