package com.hk.logistics.service.impl;

import com.hk.logistics.service.ProductVariantService;
import com.hk.logistics.domain.ProductVariant;
import com.hk.logistics.repository.ProductVariantRepository;
import com.hk.logistics.repository.search.ProductVariantSearchRepository;
import com.hk.logistics.service.dto.ProductVariantDTO;
import com.hk.logistics.service.mapper.ProductVariantMapper;
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
 * Service Implementation for managing ProductVariant.
 */
@Service
@Transactional
public class ProductVariantServiceImpl implements ProductVariantService {

    private final Logger log = LoggerFactory.getLogger(ProductVariantServiceImpl.class);

    private final ProductVariantRepository productVariantRepository;

    private final ProductVariantMapper productVariantMapper;

    private final ProductVariantSearchRepository productVariantSearchRepository;

    public ProductVariantServiceImpl(ProductVariantRepository productVariantRepository, ProductVariantMapper productVariantMapper, ProductVariantSearchRepository productVariantSearchRepository) {
        this.productVariantRepository = productVariantRepository;
        this.productVariantMapper = productVariantMapper;
        this.productVariantSearchRepository = productVariantSearchRepository;
    }

    /**
     * Save a productVariant.
     *
     * @param productVariantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductVariantDTO save(ProductVariantDTO productVariantDTO) {
        log.debug("Request to save ProductVariant : {}", productVariantDTO);
        ProductVariant productVariant = productVariantMapper.toEntity(productVariantDTO);
        productVariant = productVariantRepository.save(productVariant);
        ProductVariantDTO result = productVariantMapper.toDto(productVariant);
        productVariantSearchRepository.save(productVariant);
        return result;
    }

    /**
     * Get all the productVariants.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductVariantDTO> findAll() {
        log.debug("Request to get all ProductVariants");
        return productVariantRepository.findAll().stream()
            .map(productVariantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productVariant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductVariantDTO> findOne(Long id) {
        log.debug("Request to get ProductVariant : {}", id);
        return productVariantRepository.findById(id)
            .map(productVariantMapper::toDto);
    }

    /**
     * Delete the productVariant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductVariant : {}", id);
        productVariantRepository.deleteById(id);
        productVariantSearchRepository.deleteById(id);
    }

    /**
     * Search for the productVariant corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductVariantDTO> search(String query) {
        log.debug("Request to search ProductVariants for query {}", query);
        return StreamSupport
            .stream(productVariantSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productVariantMapper::toDto)
            .collect(Collectors.toList());
    }
}
