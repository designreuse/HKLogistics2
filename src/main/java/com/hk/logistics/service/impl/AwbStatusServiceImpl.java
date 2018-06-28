package com.hk.logistics.service.impl;

import com.hk.logistics.service.AwbStatusService;
import com.hk.logistics.domain.AwbStatus;
import com.hk.logistics.repository.AwbStatusRepository;
import com.hk.logistics.repository.search.AwbStatusSearchRepository;
import com.hk.logistics.service.dto.AwbStatusDTO;
import com.hk.logistics.service.mapper.AwbStatusMapper;
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
 * Service Implementation for managing AwbStatus.
 */
@Service
@Transactional
public class AwbStatusServiceImpl implements AwbStatusService {

    private final Logger log = LoggerFactory.getLogger(AwbStatusServiceImpl.class);

    private final AwbStatusRepository awbStatusRepository;

    private final AwbStatusMapper awbStatusMapper;

    private final AwbStatusSearchRepository awbStatusSearchRepository;

    public AwbStatusServiceImpl(AwbStatusRepository awbStatusRepository, AwbStatusMapper awbStatusMapper, AwbStatusSearchRepository awbStatusSearchRepository) {
        this.awbStatusRepository = awbStatusRepository;
        this.awbStatusMapper = awbStatusMapper;
        this.awbStatusSearchRepository = awbStatusSearchRepository;
    }

    /**
     * Save a awbStatus.
     *
     * @param awbStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AwbStatusDTO save(AwbStatusDTO awbStatusDTO) {
        log.debug("Request to save AwbStatus : {}", awbStatusDTO);
        AwbStatus awbStatus = awbStatusMapper.toEntity(awbStatusDTO);
        awbStatus = awbStatusRepository.save(awbStatus);
        AwbStatusDTO result = awbStatusMapper.toDto(awbStatus);
        awbStatusSearchRepository.save(awbStatus);
        return result;
    }

    /**
     * Get all the awbStatuses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AwbStatusDTO> findAll() {
        log.debug("Request to get all AwbStatuses");
        return awbStatusRepository.findAll().stream()
            .map(awbStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one awbStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AwbStatusDTO> findOne(Long id) {
        log.debug("Request to get AwbStatus : {}", id);
        return awbStatusRepository.findById(id)
            .map(awbStatusMapper::toDto);
    }

    /**
     * Delete the awbStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AwbStatus : {}", id);
        awbStatusRepository.deleteById(id);
        awbStatusSearchRepository.deleteById(id);
    }

    /**
     * Search for the awbStatus corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AwbStatusDTO> search(String query) {
        log.debug("Request to search AwbStatuses for query {}", query);
        return StreamSupport
            .stream(awbStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(awbStatusMapper::toDto)
            .collect(Collectors.toList());
    }
}
