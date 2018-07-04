package com.hk.logistics.service.impl;

import com.hk.logistics.service.PincodeService;
import com.hk.logistics.domain.Awb;
import com.hk.logistics.domain.Pincode;
import com.hk.logistics.repository.PincodeRepository;
import com.hk.logistics.repository.search.PincodeSearchRepository;
import com.hk.logistics.service.dto.AwbDTO;
import com.hk.logistics.service.dto.PincodeDTO;
import com.hk.logistics.service.mapper.PincodeMapper;
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
 * Service Implementation for managing Pincode.
 */
@Service
@Transactional
public class PincodeServiceImpl implements PincodeService {

    private final Logger log = LoggerFactory.getLogger(PincodeServiceImpl.class);

    private final PincodeRepository pincodeRepository;

    private final PincodeMapper pincodeMapper;

    private final PincodeSearchRepository pincodeSearchRepository;

    public PincodeServiceImpl(PincodeRepository pincodeRepository, PincodeMapper pincodeMapper, PincodeSearchRepository pincodeSearchRepository) {
        this.pincodeRepository = pincodeRepository;
        this.pincodeMapper = pincodeMapper;
        this.pincodeSearchRepository = pincodeSearchRepository;
    }

    /**
     * Save a pincode.
     *
     * @param pincodeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PincodeDTO save(PincodeDTO pincodeDTO) {
        log.debug("Request to save Pincode : {}", pincodeDTO);
        Pincode pincode = pincodeMapper.toEntity(pincodeDTO);
        pincode = pincodeRepository.save(pincode);
        PincodeDTO result = pincodeMapper.toDto(pincode);
        pincodeSearchRepository.save(pincode);
        return result;
    }

    /**
     * Get all the pincodes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PincodeDTO> findAll() {
        log.debug("Request to get all Pincodes");
        return pincodeRepository.findAll().stream()
            .map(pincodeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pincode by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PincodeDTO> findOne(Long id) {
        log.debug("Request to get Pincode : {}", id);
        return pincodeRepository.findById(id)
            .map(pincodeMapper::toDto);
    }

    /**
     * Delete the pincode by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pincode : {}", id);
        pincodeRepository.deleteById(id);
        pincodeSearchRepository.deleteById(id);
    }

    /**
     * Search for the pincode corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PincodeDTO> search(String query) {
        log.debug("Request to search Pincodes for query {}", query);
        return StreamSupport
            .stream(pincodeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(pincodeMapper::toDto)
            .collect(Collectors.toList());
    }
    
    /**
     * Search for the pincode corresponding to the query.
     *
     * @param query the query of the searchByName
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PincodeDTO> searchByPincode(String query) {
        log.debug("Request to searchByName Pincodes for query {}", query);
        return StreamSupport
            .stream(pincodeSearchRepository.findByPincode(query).spliterator(), false)
            .map(pincodeMapper::toDto)
            .collect(Collectors.toList());
    }
    
    @Override
	@Transactional
	public List<PincodeDTO> upload(List<PincodeDTO> batch) {
		log.debug("Request to upload Pincode : {}", batch);
		List<Pincode> inList = batch.parallelStream().map(dto -> pincodeMapper.toEntity(dto))
				.collect(Collectors.toList());
		List<Pincode> outList = pincodeRepository.saveAll(inList);
		List<PincodeDTO> result = outList.parallelStream().map(pincode -> pincodeMapper.toDto(pincode))
				.collect(Collectors.toList());
		pincodeSearchRepository.saveAll(inList);
		return result;
	}
}
