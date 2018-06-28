package com.hk.logistics.web.rest;

import com.hk.logistics.HkLogisticsApp;

import com.hk.logistics.domain.ProductVariant;
import com.hk.logistics.repository.ProductVariantRepository;
import com.hk.logistics.repository.search.ProductVariantSearchRepository;
import com.hk.logistics.service.ProductVariantService;
import com.hk.logistics.service.dto.ProductVariantDTO;
import com.hk.logistics.service.mapper.ProductVariantMapper;
import com.hk.logistics.web.rest.errors.ExceptionTranslator;
import com.hk.logistics.service.dto.ProductVariantCriteria;
import com.hk.logistics.service.ProductVariantQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.hk.logistics.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductVariantResource REST controller.
 *
 * @see ProductVariantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HkLogisticsApp.class)
public class ProductVariantResourceIntTest {

    private static final String DEFAULT_VARIANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_VARIANT_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SERVICEABLE = false;
    private static final Boolean UPDATED_SERVICEABLE = true;

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    @Autowired
    private ProductVariantRepository productVariantRepository;


    @Autowired
    private ProductVariantMapper productVariantMapper;
    

    @Autowired
    private ProductVariantService productVariantService;

    /**
     * This repository is mocked in the com.hk.logistics.repository.search test package.
     *
     * @see com.hk.logistics.repository.search.ProductVariantSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductVariantSearchRepository mockProductVariantSearchRepository;

    @Autowired
    private ProductVariantQueryService productVariantQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductVariantMockMvc;

    private ProductVariant productVariant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductVariantResource productVariantResource = new ProductVariantResource(productVariantService, productVariantQueryService);
        this.restProductVariantMockMvc = MockMvcBuilders.standaloneSetup(productVariantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductVariant createEntity(EntityManager em) {
        ProductVariant productVariant = new ProductVariant()
            .variantId(DEFAULT_VARIANT_ID)
            .serviceable(DEFAULT_SERVICEABLE)
            .pincode(DEFAULT_PINCODE);
        return productVariant;
    }

    @Before
    public void initTest() {
        productVariant = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductVariant() throws Exception {
        int databaseSizeBeforeCreate = productVariantRepository.findAll().size();

        // Create the ProductVariant
        ProductVariantDTO productVariantDTO = productVariantMapper.toDto(productVariant);
        restProductVariantMockMvc.perform(post("/api/product-variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productVariantDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductVariant in the database
        List<ProductVariant> productVariantList = productVariantRepository.findAll();
        assertThat(productVariantList).hasSize(databaseSizeBeforeCreate + 1);
        ProductVariant testProductVariant = productVariantList.get(productVariantList.size() - 1);
        assertThat(testProductVariant.getVariantId()).isEqualTo(DEFAULT_VARIANT_ID);
        assertThat(testProductVariant.isServiceable()).isEqualTo(DEFAULT_SERVICEABLE);
        assertThat(testProductVariant.getPincode()).isEqualTo(DEFAULT_PINCODE);

        // Validate the ProductVariant in Elasticsearch
        verify(mockProductVariantSearchRepository, times(1)).save(testProductVariant);
    }

    @Test
    @Transactional
    public void createProductVariantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productVariantRepository.findAll().size();

        // Create the ProductVariant with an existing ID
        productVariant.setId(1L);
        ProductVariantDTO productVariantDTO = productVariantMapper.toDto(productVariant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductVariantMockMvc.perform(post("/api/product-variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productVariantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductVariant in the database
        List<ProductVariant> productVariantList = productVariantRepository.findAll();
        assertThat(productVariantList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProductVariant in Elasticsearch
        verify(mockProductVariantSearchRepository, times(0)).save(productVariant);
    }

    @Test
    @Transactional
    public void checkVariantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = productVariantRepository.findAll().size();
        // set the field null
        productVariant.setVariantId(null);

        // Create the ProductVariant, which fails.
        ProductVariantDTO productVariantDTO = productVariantMapper.toDto(productVariant);

        restProductVariantMockMvc.perform(post("/api/product-variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productVariantDTO)))
            .andExpect(status().isBadRequest());

        List<ProductVariant> productVariantList = productVariantRepository.findAll();
        assertThat(productVariantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductVariants() throws Exception {
        // Initialize the database
        productVariantRepository.saveAndFlush(productVariant);

        // Get all the productVariantList
        restProductVariantMockMvc.perform(get("/api/product-variants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productVariant.getId().intValue())))
            .andExpect(jsonPath("$.[*].variantId").value(hasItem(DEFAULT_VARIANT_ID.toString())))
            .andExpect(jsonPath("$.[*].serviceable").value(hasItem(DEFAULT_SERVICEABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.toString())));
    }
    

    @Test
    @Transactional
    public void getProductVariant() throws Exception {
        // Initialize the database
        productVariantRepository.saveAndFlush(productVariant);

        // Get the productVariant
        restProductVariantMockMvc.perform(get("/api/product-variants/{id}", productVariant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productVariant.getId().intValue()))
            .andExpect(jsonPath("$.variantId").value(DEFAULT_VARIANT_ID.toString()))
            .andExpect(jsonPath("$.serviceable").value(DEFAULT_SERVICEABLE.booleanValue()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE.toString()));
    }

    @Test
    @Transactional
    public void getAllProductVariantsByVariantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        productVariantRepository.saveAndFlush(productVariant);

        // Get all the productVariantList where variantId equals to DEFAULT_VARIANT_ID
        defaultProductVariantShouldBeFound("variantId.equals=" + DEFAULT_VARIANT_ID);

        // Get all the productVariantList where variantId equals to UPDATED_VARIANT_ID
        defaultProductVariantShouldNotBeFound("variantId.equals=" + UPDATED_VARIANT_ID);
    }

    @Test
    @Transactional
    public void getAllProductVariantsByVariantIdIsInShouldWork() throws Exception {
        // Initialize the database
        productVariantRepository.saveAndFlush(productVariant);

        // Get all the productVariantList where variantId in DEFAULT_VARIANT_ID or UPDATED_VARIANT_ID
        defaultProductVariantShouldBeFound("variantId.in=" + DEFAULT_VARIANT_ID + "," + UPDATED_VARIANT_ID);

        // Get all the productVariantList where variantId equals to UPDATED_VARIANT_ID
        defaultProductVariantShouldNotBeFound("variantId.in=" + UPDATED_VARIANT_ID);
    }

    @Test
    @Transactional
    public void getAllProductVariantsByVariantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        productVariantRepository.saveAndFlush(productVariant);

        // Get all the productVariantList where variantId is not null
        defaultProductVariantShouldBeFound("variantId.specified=true");

        // Get all the productVariantList where variantId is null
        defaultProductVariantShouldNotBeFound("variantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductVariantsByServiceableIsEqualToSomething() throws Exception {
        // Initialize the database
        productVariantRepository.saveAndFlush(productVariant);

        // Get all the productVariantList where serviceable equals to DEFAULT_SERVICEABLE
        defaultProductVariantShouldBeFound("serviceable.equals=" + DEFAULT_SERVICEABLE);

        // Get all the productVariantList where serviceable equals to UPDATED_SERVICEABLE
        defaultProductVariantShouldNotBeFound("serviceable.equals=" + UPDATED_SERVICEABLE);
    }

    @Test
    @Transactional
    public void getAllProductVariantsByServiceableIsInShouldWork() throws Exception {
        // Initialize the database
        productVariantRepository.saveAndFlush(productVariant);

        // Get all the productVariantList where serviceable in DEFAULT_SERVICEABLE or UPDATED_SERVICEABLE
        defaultProductVariantShouldBeFound("serviceable.in=" + DEFAULT_SERVICEABLE + "," + UPDATED_SERVICEABLE);

        // Get all the productVariantList where serviceable equals to UPDATED_SERVICEABLE
        defaultProductVariantShouldNotBeFound("serviceable.in=" + UPDATED_SERVICEABLE);
    }

    @Test
    @Transactional
    public void getAllProductVariantsByServiceableIsNullOrNotNull() throws Exception {
        // Initialize the database
        productVariantRepository.saveAndFlush(productVariant);

        // Get all the productVariantList where serviceable is not null
        defaultProductVariantShouldBeFound("serviceable.specified=true");

        // Get all the productVariantList where serviceable is null
        defaultProductVariantShouldNotBeFound("serviceable.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductVariantsByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        productVariantRepository.saveAndFlush(productVariant);

        // Get all the productVariantList where pincode equals to DEFAULT_PINCODE
        defaultProductVariantShouldBeFound("pincode.equals=" + DEFAULT_PINCODE);

        // Get all the productVariantList where pincode equals to UPDATED_PINCODE
        defaultProductVariantShouldNotBeFound("pincode.equals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    public void getAllProductVariantsByPincodeIsInShouldWork() throws Exception {
        // Initialize the database
        productVariantRepository.saveAndFlush(productVariant);

        // Get all the productVariantList where pincode in DEFAULT_PINCODE or UPDATED_PINCODE
        defaultProductVariantShouldBeFound("pincode.in=" + DEFAULT_PINCODE + "," + UPDATED_PINCODE);

        // Get all the productVariantList where pincode equals to UPDATED_PINCODE
        defaultProductVariantShouldNotBeFound("pincode.in=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    public void getAllProductVariantsByPincodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productVariantRepository.saveAndFlush(productVariant);

        // Get all the productVariantList where pincode is not null
        defaultProductVariantShouldBeFound("pincode.specified=true");

        // Get all the productVariantList where pincode is null
        defaultProductVariantShouldNotBeFound("pincode.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProductVariantShouldBeFound(String filter) throws Exception {
        restProductVariantMockMvc.perform(get("/api/product-variants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productVariant.getId().intValue())))
            .andExpect(jsonPath("$.[*].variantId").value(hasItem(DEFAULT_VARIANT_ID.toString())))
            .andExpect(jsonPath("$.[*].serviceable").value(hasItem(DEFAULT_SERVICEABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProductVariantShouldNotBeFound(String filter) throws Exception {
        restProductVariantMockMvc.perform(get("/api/product-variants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingProductVariant() throws Exception {
        // Get the productVariant
        restProductVariantMockMvc.perform(get("/api/product-variants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductVariant() throws Exception {
        // Initialize the database
        productVariantRepository.saveAndFlush(productVariant);

        int databaseSizeBeforeUpdate = productVariantRepository.findAll().size();

        // Update the productVariant
        ProductVariant updatedProductVariant = productVariantRepository.findById(productVariant.getId()).get();
        // Disconnect from session so that the updates on updatedProductVariant are not directly saved in db
        em.detach(updatedProductVariant);
        updatedProductVariant
            .variantId(UPDATED_VARIANT_ID)
            .serviceable(UPDATED_SERVICEABLE)
            .pincode(UPDATED_PINCODE);
        ProductVariantDTO productVariantDTO = productVariantMapper.toDto(updatedProductVariant);

        restProductVariantMockMvc.perform(put("/api/product-variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productVariantDTO)))
            .andExpect(status().isOk());

        // Validate the ProductVariant in the database
        List<ProductVariant> productVariantList = productVariantRepository.findAll();
        assertThat(productVariantList).hasSize(databaseSizeBeforeUpdate);
        ProductVariant testProductVariant = productVariantList.get(productVariantList.size() - 1);
        assertThat(testProductVariant.getVariantId()).isEqualTo(UPDATED_VARIANT_ID);
        assertThat(testProductVariant.isServiceable()).isEqualTo(UPDATED_SERVICEABLE);
        assertThat(testProductVariant.getPincode()).isEqualTo(UPDATED_PINCODE);

        // Validate the ProductVariant in Elasticsearch
        verify(mockProductVariantSearchRepository, times(1)).save(testProductVariant);
    }

    @Test
    @Transactional
    public void updateNonExistingProductVariant() throws Exception {
        int databaseSizeBeforeUpdate = productVariantRepository.findAll().size();

        // Create the ProductVariant
        ProductVariantDTO productVariantDTO = productVariantMapper.toDto(productVariant);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductVariantMockMvc.perform(put("/api/product-variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productVariantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductVariant in the database
        List<ProductVariant> productVariantList = productVariantRepository.findAll();
        assertThat(productVariantList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProductVariant in Elasticsearch
        verify(mockProductVariantSearchRepository, times(0)).save(productVariant);
    }

    @Test
    @Transactional
    public void deleteProductVariant() throws Exception {
        // Initialize the database
        productVariantRepository.saveAndFlush(productVariant);

        int databaseSizeBeforeDelete = productVariantRepository.findAll().size();

        // Get the productVariant
        restProductVariantMockMvc.perform(delete("/api/product-variants/{id}", productVariant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductVariant> productVariantList = productVariantRepository.findAll();
        assertThat(productVariantList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProductVariant in Elasticsearch
        verify(mockProductVariantSearchRepository, times(1)).deleteById(productVariant.getId());
    }

    @Test
    @Transactional
    public void searchProductVariant() throws Exception {
        // Initialize the database
        productVariantRepository.saveAndFlush(productVariant);
        when(mockProductVariantSearchRepository.search(queryStringQuery("id:" + productVariant.getId())))
            .thenReturn(Collections.singletonList(productVariant));
        // Search the productVariant
        restProductVariantMockMvc.perform(get("/api/_search/product-variants?query=id:" + productVariant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productVariant.getId().intValue())))
            .andExpect(jsonPath("$.[*].variantId").value(hasItem(DEFAULT_VARIANT_ID.toString())))
            .andExpect(jsonPath("$.[*].serviceable").value(hasItem(DEFAULT_SERVICEABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductVariant.class);
        ProductVariant productVariant1 = new ProductVariant();
        productVariant1.setId(1L);
        ProductVariant productVariant2 = new ProductVariant();
        productVariant2.setId(productVariant1.getId());
        assertThat(productVariant1).isEqualTo(productVariant2);
        productVariant2.setId(2L);
        assertThat(productVariant1).isNotEqualTo(productVariant2);
        productVariant1.setId(null);
        assertThat(productVariant1).isNotEqualTo(productVariant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductVariantDTO.class);
        ProductVariantDTO productVariantDTO1 = new ProductVariantDTO();
        productVariantDTO1.setId(1L);
        ProductVariantDTO productVariantDTO2 = new ProductVariantDTO();
        assertThat(productVariantDTO1).isNotEqualTo(productVariantDTO2);
        productVariantDTO2.setId(productVariantDTO1.getId());
        assertThat(productVariantDTO1).isEqualTo(productVariantDTO2);
        productVariantDTO2.setId(2L);
        assertThat(productVariantDTO1).isNotEqualTo(productVariantDTO2);
        productVariantDTO1.setId(null);
        assertThat(productVariantDTO1).isNotEqualTo(productVariantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productVariantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productVariantMapper.fromId(null)).isNull();
    }
}
