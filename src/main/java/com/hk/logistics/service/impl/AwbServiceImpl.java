package com.hk.logistics.service.impl;

import com.hk.logistics.constants.EnumAwbStatus;
import com.hk.logistics.domain.*;
import com.hk.logistics.enums.EnumChannel;
import com.hk.logistics.enums.EnumWarehouse;
import com.hk.logistics.repository.*;
import com.hk.logistics.service.*;
import com.hk.logistics.repository.search.AwbSearchRepository;
import com.hk.logistics.service.dto.*;
import com.hk.logistics.service.mapper.AwbMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Awb.
 */
@Service
@Transactional
public class AwbServiceImpl implements AwbService {

	private final Logger log = LoggerFactory.getLogger(AwbServiceImpl.class);

	private final AwbRepository awbRepository;

	private final AwbMapper awbMapper;

	private final AwbSearchRepository awbSearchRepository;

	

	public AwbServiceImpl(AwbRepository awbRepository, AwbMapper awbMapper, AwbSearchRepository awbSearchRepository) {
		this.awbRepository = awbRepository;
		this.awbMapper = awbMapper;
		this.awbSearchRepository = awbSearchRepository;
	}

	/**
	 * Save a awb.
	 *
	 * @param awbDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public AwbDTO save(AwbDTO awbDTO) {
		log.debug("Request to save Awb : {}", awbDTO);
		Awb awb = awbMapper.toEntity(awbDTO);
		awb = awbRepository.save(awb);
		AwbDTO result = awbMapper.toDto(awb);
		awbSearchRepository.save(awb);
		return result;
	}

	/**
	 * Get all the awbs.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AwbDTO> findAll() {
		log.debug("Request to get all Awbs");
		return awbRepository.findAll().stream()
				.map(awbMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}


	/**
	 * Get one awb by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<AwbDTO> findOne(Long id) {
		log.debug("Request to get Awb : {}", id);
		return awbRepository.findById(id)
				.map(awbMapper::toDto);
	}

	/**
	 * Delete the awb by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Awb : {}", id);
		awbRepository.deleteById(id);
		awbSearchRepository.deleteById(id);
	}

	/**
	 * Search for the awb corresponding to the query.
	 *
	 * @param query the query of the search
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AwbDTO> search(String query) {
		log.debug("Request to search Awbs for query {}", query);
		return StreamSupport
				.stream(awbSearchRepository.search(queryStringQuery(query)).spliterator(), false)
				.map(awbMapper::toDto)
				.collect(Collectors.toList());
	}



}
