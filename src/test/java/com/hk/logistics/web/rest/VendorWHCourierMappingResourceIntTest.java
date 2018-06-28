package com.hk.logistics.web.rest;

import com.hk.logistics.HkLogisticsApp;

import com.hk.logistics.domain.VendorWHCourierMapping;
import com.hk.logistics.domain.CourierChannel;
import com.hk.logistics.repository.VendorWHCourierMappingRepository;
import com.hk.logistics.repository.search.VendorWHCourierMappingSearchRepository;
import com.hk.logistics.service.VendorWHCourierMappingService;
import com.hk.logistics.service.dto.VendorWHCourierMappingDTO;
import com.hk.logistics.service.mapper.VendorWHCourierMappingMapper;
import com.hk.logistics.web.rest.errors.ExceptionTranslator;
import com.hk.logistics.service.dto.VendorWHCourierMappingCriteria;
import com.hk.logistics.service.VendorWHCourierMappingQueryService;

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
 * Test class for the VendorWHCourierMappingResource REST controller.
 *
 * @see VendorWHCourierMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HkLogisticsApp.class)
public class VendorWHCourierMappingResourceIntTest {

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_VENDOR = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR = "BBBBBBBBBB";

    private static final Long DEFAULT_WAREHOUSE = 1L;
    private static final Long UPDATED_WAREHOUSE = 2L;

    @Autowired
    private VendorWHCourierMappingRepository vendorWHCourierMappingRepository;


    @Autowired
    private VendorWHCourierMappingMapper vendorWHCourierMappingMapper;
    

    @Autowired
    private VendorWHCourierMappingService vendorWHCourierMappingService;

    /**
     * This repository is mocked in the com.hk.logistics.repository.search test package.
     *
     * @see com.hk.logistics.repository.search.VendorWHCourierMappingSearchRepositoryMockConfiguration
     */
    @Autowired
    private VendorWHCourierMappingSearchRepository mockVendorWHCourierMappingSearchRepository;

    @Autowired
    private VendorWHCourierMappingQueryService vendorWHCourierMappingQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVendorWHCourierMappingMockMvc;

    private VendorWHCourierMapping vendorWHCourierMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VendorWHCourierMappingResource vendorWHCourierMappingResource = new VendorWHCourierMappingResource(vendorWHCourierMappingService, vendorWHCourierMappingQueryService);
        this.restVendorWHCourierMappingMockMvc = MockMvcBuilders.standaloneSetup(vendorWHCourierMappingResource)
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
    public static VendorWHCourierMapping createEntity(EntityManager em) {
        VendorWHCourierMapping vendorWHCourierMapping = new VendorWHCourierMapping()
            .active(DEFAULT_ACTIVE)
            .vendor(DEFAULT_VENDOR)
            .warehouse(DEFAULT_WAREHOUSE);
        return vendorWHCourierMapping;
    }

    @Before
    public void initTest() {
        vendorWHCourierMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createVendorWHCourierMapping() throws Exception {
        int databaseSizeBeforeCreate = vendorWHCourierMappingRepository.findAll().size();

        // Create the VendorWHCourierMapping
        VendorWHCourierMappingDTO vendorWHCourierMappingDTO = vendorWHCourierMappingMapper.toDto(vendorWHCourierMapping);
        restVendorWHCourierMappingMockMvc.perform(post("/api/vendor-wh-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorWHCourierMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the VendorWHCourierMapping in the database
        List<VendorWHCourierMapping> vendorWHCourierMappingList = vendorWHCourierMappingRepository.findAll();
        assertThat(vendorWHCourierMappingList).hasSize(databaseSizeBeforeCreate + 1);
        VendorWHCourierMapping testVendorWHCourierMapping = vendorWHCourierMappingList.get(vendorWHCourierMappingList.size() - 1);
        assertThat(testVendorWHCourierMapping.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testVendorWHCourierMapping.getVendor()).isEqualTo(DEFAULT_VENDOR);
        assertThat(testVendorWHCourierMapping.getWarehouse()).isEqualTo(DEFAULT_WAREHOUSE);

        // Validate the VendorWHCourierMapping in Elasticsearch
        verify(mockVendorWHCourierMappingSearchRepository, times(1)).save(testVendorWHCourierMapping);
    }

    @Test
    @Transactional
    public void createVendorWHCourierMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vendorWHCourierMappingRepository.findAll().size();

        // Create the VendorWHCourierMapping with an existing ID
        vendorWHCourierMapping.setId(1L);
        VendorWHCourierMappingDTO vendorWHCourierMappingDTO = vendorWHCourierMappingMapper.toDto(vendorWHCourierMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendorWHCourierMappingMockMvc.perform(post("/api/vendor-wh-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorWHCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VendorWHCourierMapping in the database
        List<VendorWHCourierMapping> vendorWHCourierMappingList = vendorWHCourierMappingRepository.findAll();
        assertThat(vendorWHCourierMappingList).hasSize(databaseSizeBeforeCreate);

        // Validate the VendorWHCourierMapping in Elasticsearch
        verify(mockVendorWHCourierMappingSearchRepository, times(0)).save(vendorWHCourierMapping);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorWHCourierMappingRepository.findAll().size();
        // set the field null
        vendorWHCourierMapping.setActive(null);

        // Create the VendorWHCourierMapping, which fails.
        VendorWHCourierMappingDTO vendorWHCourierMappingDTO = vendorWHCourierMappingMapper.toDto(vendorWHCourierMapping);

        restVendorWHCourierMappingMockMvc.perform(post("/api/vendor-wh-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorWHCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        List<VendorWHCourierMapping> vendorWHCourierMappingList = vendorWHCourierMappingRepository.findAll();
        assertThat(vendorWHCourierMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVendorWHCourierMappings() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        // Get all the vendorWHCourierMappingList
        restVendorWHCourierMappingMockMvc.perform(get("/api/vendor-wh-courier-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendorWHCourierMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].vendor").value(hasItem(DEFAULT_VENDOR.toString())))
            .andExpect(jsonPath("$.[*].warehouse").value(hasItem(DEFAULT_WAREHOUSE.intValue())));
    }
    

    @Test
    @Transactional
    public void getVendorWHCourierMapping() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        // Get the vendorWHCourierMapping
        restVendorWHCourierMappingMockMvc.perform(get("/api/vendor-wh-courier-mappings/{id}", vendorWHCourierMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vendorWHCourierMapping.getId().intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.vendor").value(DEFAULT_VENDOR.toString()))
            .andExpect(jsonPath("$.warehouse").value(DEFAULT_WAREHOUSE.intValue()));
    }

    @Test
    @Transactional
    public void getAllVendorWHCourierMappingsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        // Get all the vendorWHCourierMappingList where active equals to DEFAULT_ACTIVE
        defaultVendorWHCourierMappingShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the vendorWHCourierMappingList where active equals to UPDATED_ACTIVE
        defaultVendorWHCourierMappingShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllVendorWHCourierMappingsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        // Get all the vendorWHCourierMappingList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultVendorWHCourierMappingShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the vendorWHCourierMappingList where active equals to UPDATED_ACTIVE
        defaultVendorWHCourierMappingShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllVendorWHCourierMappingsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        // Get all the vendorWHCourierMappingList where active is not null
        defaultVendorWHCourierMappingShouldBeFound("active.specified=true");

        // Get all the vendorWHCourierMappingList where active is null
        defaultVendorWHCourierMappingShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendorWHCourierMappingsByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        // Get all the vendorWHCourierMappingList where vendor equals to DEFAULT_VENDOR
        defaultVendorWHCourierMappingShouldBeFound("vendor.equals=" + DEFAULT_VENDOR);

        // Get all the vendorWHCourierMappingList where vendor equals to UPDATED_VENDOR
        defaultVendorWHCourierMappingShouldNotBeFound("vendor.equals=" + UPDATED_VENDOR);
    }

    @Test
    @Transactional
    public void getAllVendorWHCourierMappingsByVendorIsInShouldWork() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        // Get all the vendorWHCourierMappingList where vendor in DEFAULT_VENDOR or UPDATED_VENDOR
        defaultVendorWHCourierMappingShouldBeFound("vendor.in=" + DEFAULT_VENDOR + "," + UPDATED_VENDOR);

        // Get all the vendorWHCourierMappingList where vendor equals to UPDATED_VENDOR
        defaultVendorWHCourierMappingShouldNotBeFound("vendor.in=" + UPDATED_VENDOR);
    }

    @Test
    @Transactional
    public void getAllVendorWHCourierMappingsByVendorIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        // Get all the vendorWHCourierMappingList where vendor is not null
        defaultVendorWHCourierMappingShouldBeFound("vendor.specified=true");

        // Get all the vendorWHCourierMappingList where vendor is null
        defaultVendorWHCourierMappingShouldNotBeFound("vendor.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendorWHCourierMappingsByWarehouseIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        // Get all the vendorWHCourierMappingList where warehouse equals to DEFAULT_WAREHOUSE
        defaultVendorWHCourierMappingShouldBeFound("warehouse.equals=" + DEFAULT_WAREHOUSE);

        // Get all the vendorWHCourierMappingList where warehouse equals to UPDATED_WAREHOUSE
        defaultVendorWHCourierMappingShouldNotBeFound("warehouse.equals=" + UPDATED_WAREHOUSE);
    }

    @Test
    @Transactional
    public void getAllVendorWHCourierMappingsByWarehouseIsInShouldWork() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        // Get all the vendorWHCourierMappingList where warehouse in DEFAULT_WAREHOUSE or UPDATED_WAREHOUSE
        defaultVendorWHCourierMappingShouldBeFound("warehouse.in=" + DEFAULT_WAREHOUSE + "," + UPDATED_WAREHOUSE);

        // Get all the vendorWHCourierMappingList where warehouse equals to UPDATED_WAREHOUSE
        defaultVendorWHCourierMappingShouldNotBeFound("warehouse.in=" + UPDATED_WAREHOUSE);
    }

    @Test
    @Transactional
    public void getAllVendorWHCourierMappingsByWarehouseIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        // Get all the vendorWHCourierMappingList where warehouse is not null
        defaultVendorWHCourierMappingShouldBeFound("warehouse.specified=true");

        // Get all the vendorWHCourierMappingList where warehouse is null
        defaultVendorWHCourierMappingShouldNotBeFound("warehouse.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendorWHCourierMappingsByWarehouseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        // Get all the vendorWHCourierMappingList where warehouse greater than or equals to DEFAULT_WAREHOUSE
        defaultVendorWHCourierMappingShouldBeFound("warehouse.greaterOrEqualThan=" + DEFAULT_WAREHOUSE);

        // Get all the vendorWHCourierMappingList where warehouse greater than or equals to UPDATED_WAREHOUSE
        defaultVendorWHCourierMappingShouldNotBeFound("warehouse.greaterOrEqualThan=" + UPDATED_WAREHOUSE);
    }

    @Test
    @Transactional
    public void getAllVendorWHCourierMappingsByWarehouseIsLessThanSomething() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        // Get all the vendorWHCourierMappingList where warehouse less than or equals to DEFAULT_WAREHOUSE
        defaultVendorWHCourierMappingShouldNotBeFound("warehouse.lessThan=" + DEFAULT_WAREHOUSE);

        // Get all the vendorWHCourierMappingList where warehouse less than or equals to UPDATED_WAREHOUSE
        defaultVendorWHCourierMappingShouldBeFound("warehouse.lessThan=" + UPDATED_WAREHOUSE);
    }


    @Test
    @Transactional
    public void getAllVendorWHCourierMappingsByCourierChannelIsEqualToSomething() throws Exception {
        // Initialize the database
        CourierChannel courierChannel = CourierChannelResourceIntTest.createEntity(em);
        em.persist(courierChannel);
        em.flush();
        vendorWHCourierMapping.setCourierChannel(courierChannel);
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);
        Long courierChannelId = courierChannel.getId();

        // Get all the vendorWHCourierMappingList where courierChannel equals to courierChannelId
        defaultVendorWHCourierMappingShouldBeFound("courierChannelId.equals=" + courierChannelId);

        // Get all the vendorWHCourierMappingList where courierChannel equals to courierChannelId + 1
        defaultVendorWHCourierMappingShouldNotBeFound("courierChannelId.equals=" + (courierChannelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultVendorWHCourierMappingShouldBeFound(String filter) throws Exception {
        restVendorWHCourierMappingMockMvc.perform(get("/api/vendor-wh-courier-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendorWHCourierMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].vendor").value(hasItem(DEFAULT_VENDOR.toString())))
            .andExpect(jsonPath("$.[*].warehouse").value(hasItem(DEFAULT_WAREHOUSE.intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultVendorWHCourierMappingShouldNotBeFound(String filter) throws Exception {
        restVendorWHCourierMappingMockMvc.perform(get("/api/vendor-wh-courier-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingVendorWHCourierMapping() throws Exception {
        // Get the vendorWHCourierMapping
        restVendorWHCourierMappingMockMvc.perform(get("/api/vendor-wh-courier-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVendorWHCourierMapping() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        int databaseSizeBeforeUpdate = vendorWHCourierMappingRepository.findAll().size();

        // Update the vendorWHCourierMapping
        VendorWHCourierMapping updatedVendorWHCourierMapping = vendorWHCourierMappingRepository.findById(vendorWHCourierMapping.getId()).get();
        // Disconnect from session so that the updates on updatedVendorWHCourierMapping are not directly saved in db
        em.detach(updatedVendorWHCourierMapping);
        updatedVendorWHCourierMapping
            .active(UPDATED_ACTIVE)
            .vendor(UPDATED_VENDOR)
            .warehouse(UPDATED_WAREHOUSE);
        VendorWHCourierMappingDTO vendorWHCourierMappingDTO = vendorWHCourierMappingMapper.toDto(updatedVendorWHCourierMapping);

        restVendorWHCourierMappingMockMvc.perform(put("/api/vendor-wh-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorWHCourierMappingDTO)))
            .andExpect(status().isOk());

        // Validate the VendorWHCourierMapping in the database
        List<VendorWHCourierMapping> vendorWHCourierMappingList = vendorWHCourierMappingRepository.findAll();
        assertThat(vendorWHCourierMappingList).hasSize(databaseSizeBeforeUpdate);
        VendorWHCourierMapping testVendorWHCourierMapping = vendorWHCourierMappingList.get(vendorWHCourierMappingList.size() - 1);
        assertThat(testVendorWHCourierMapping.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testVendorWHCourierMapping.getVendor()).isEqualTo(UPDATED_VENDOR);
        assertThat(testVendorWHCourierMapping.getWarehouse()).isEqualTo(UPDATED_WAREHOUSE);

        // Validate the VendorWHCourierMapping in Elasticsearch
        verify(mockVendorWHCourierMappingSearchRepository, times(1)).save(testVendorWHCourierMapping);
    }

    @Test
    @Transactional
    public void updateNonExistingVendorWHCourierMapping() throws Exception {
        int databaseSizeBeforeUpdate = vendorWHCourierMappingRepository.findAll().size();

        // Create the VendorWHCourierMapping
        VendorWHCourierMappingDTO vendorWHCourierMappingDTO = vendorWHCourierMappingMapper.toDto(vendorWHCourierMapping);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVendorWHCourierMappingMockMvc.perform(put("/api/vendor-wh-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorWHCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VendorWHCourierMapping in the database
        List<VendorWHCourierMapping> vendorWHCourierMappingList = vendorWHCourierMappingRepository.findAll();
        assertThat(vendorWHCourierMappingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the VendorWHCourierMapping in Elasticsearch
        verify(mockVendorWHCourierMappingSearchRepository, times(0)).save(vendorWHCourierMapping);
    }

    @Test
    @Transactional
    public void deleteVendorWHCourierMapping() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);

        int databaseSizeBeforeDelete = vendorWHCourierMappingRepository.findAll().size();

        // Get the vendorWHCourierMapping
        restVendorWHCourierMappingMockMvc.perform(delete("/api/vendor-wh-courier-mappings/{id}", vendorWHCourierMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VendorWHCourierMapping> vendorWHCourierMappingList = vendorWHCourierMappingRepository.findAll();
        assertThat(vendorWHCourierMappingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the VendorWHCourierMapping in Elasticsearch
        verify(mockVendorWHCourierMappingSearchRepository, times(1)).deleteById(vendorWHCourierMapping.getId());
    }

    @Test
    @Transactional
    public void searchVendorWHCourierMapping() throws Exception {
        // Initialize the database
        vendorWHCourierMappingRepository.saveAndFlush(vendorWHCourierMapping);
        when(mockVendorWHCourierMappingSearchRepository.search(queryStringQuery("id:" + vendorWHCourierMapping.getId())))
            .thenReturn(Collections.singletonList(vendorWHCourierMapping));
        // Search the vendorWHCourierMapping
        restVendorWHCourierMappingMockMvc.perform(get("/api/_search/vendor-wh-courier-mappings?query=id:" + vendorWHCourierMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendorWHCourierMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].vendor").value(hasItem(DEFAULT_VENDOR.toString())))
            .andExpect(jsonPath("$.[*].warehouse").value(hasItem(DEFAULT_WAREHOUSE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendorWHCourierMapping.class);
        VendorWHCourierMapping vendorWHCourierMapping1 = new VendorWHCourierMapping();
        vendorWHCourierMapping1.setId(1L);
        VendorWHCourierMapping vendorWHCourierMapping2 = new VendorWHCourierMapping();
        vendorWHCourierMapping2.setId(vendorWHCourierMapping1.getId());
        assertThat(vendorWHCourierMapping1).isEqualTo(vendorWHCourierMapping2);
        vendorWHCourierMapping2.setId(2L);
        assertThat(vendorWHCourierMapping1).isNotEqualTo(vendorWHCourierMapping2);
        vendorWHCourierMapping1.setId(null);
        assertThat(vendorWHCourierMapping1).isNotEqualTo(vendorWHCourierMapping2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendorWHCourierMappingDTO.class);
        VendorWHCourierMappingDTO vendorWHCourierMappingDTO1 = new VendorWHCourierMappingDTO();
        vendorWHCourierMappingDTO1.setId(1L);
        VendorWHCourierMappingDTO vendorWHCourierMappingDTO2 = new VendorWHCourierMappingDTO();
        assertThat(vendorWHCourierMappingDTO1).isNotEqualTo(vendorWHCourierMappingDTO2);
        vendorWHCourierMappingDTO2.setId(vendorWHCourierMappingDTO1.getId());
        assertThat(vendorWHCourierMappingDTO1).isEqualTo(vendorWHCourierMappingDTO2);
        vendorWHCourierMappingDTO2.setId(2L);
        assertThat(vendorWHCourierMappingDTO1).isNotEqualTo(vendorWHCourierMappingDTO2);
        vendorWHCourierMappingDTO1.setId(null);
        assertThat(vendorWHCourierMappingDTO1).isNotEqualTo(vendorWHCourierMappingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vendorWHCourierMappingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vendorWHCourierMappingMapper.fromId(null)).isNull();
    }
}
