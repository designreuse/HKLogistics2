package com.hk.logistics.web.rest;

import com.hk.logistics.HkLogisticsApp;

import com.hk.logistics.domain.RegionType;
import com.hk.logistics.repository.RegionTypeRepository;
import com.hk.logistics.repository.search.RegionTypeSearchRepository;
import com.hk.logistics.service.RegionTypeService;
import com.hk.logistics.service.dto.RegionTypeDTO;
import com.hk.logistics.service.mapper.RegionTypeMapper;
import com.hk.logistics.web.rest.errors.ExceptionTranslator;
import com.hk.logistics.service.dto.RegionTypeCriteria;
import com.hk.logistics.service.RegionTypeQueryService;

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
 * Test class for the RegionTypeResource REST controller.
 *
 * @see RegionTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HkLogisticsApp.class)
public class RegionTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PRIORITY = 1L;
    private static final Long UPDATED_PRIORITY = 2L;

    @Autowired
    private RegionTypeRepository regionTypeRepository;


    @Autowired
    private RegionTypeMapper regionTypeMapper;
    

    @Autowired
    private RegionTypeService regionTypeService;

    /**
     * This repository is mocked in the com.hk.logistics.repository.search test package.
     *
     * @see com.hk.logistics.repository.search.RegionTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private RegionTypeSearchRepository mockRegionTypeSearchRepository;

    @Autowired
    private RegionTypeQueryService regionTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegionTypeMockMvc;

    private RegionType regionType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegionTypeResource regionTypeResource = new RegionTypeResource(regionTypeService, regionTypeQueryService);
        this.restRegionTypeMockMvc = MockMvcBuilders.standaloneSetup(regionTypeResource)
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
    public static RegionType createEntity(EntityManager em) {
        RegionType regionType = new RegionType()
            .name(DEFAULT_NAME)
            .priority(DEFAULT_PRIORITY);
        return regionType;
    }

    @Before
    public void initTest() {
        regionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegionType() throws Exception {
        int databaseSizeBeforeCreate = regionTypeRepository.findAll().size();

        // Create the RegionType
        RegionTypeDTO regionTypeDTO = regionTypeMapper.toDto(regionType);
        restRegionTypeMockMvc.perform(post("/api/region-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the RegionType in the database
        List<RegionType> regionTypeList = regionTypeRepository.findAll();
        assertThat(regionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RegionType testRegionType = regionTypeList.get(regionTypeList.size() - 1);
        assertThat(testRegionType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRegionType.getPriority()).isEqualTo(DEFAULT_PRIORITY);

        // Validate the RegionType in Elasticsearch
        verify(mockRegionTypeSearchRepository, times(1)).save(testRegionType);
    }

    @Test
    @Transactional
    public void createRegionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regionTypeRepository.findAll().size();

        // Create the RegionType with an existing ID
        regionType.setId(1L);
        RegionTypeDTO regionTypeDTO = regionTypeMapper.toDto(regionType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegionTypeMockMvc.perform(post("/api/region-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegionType in the database
        List<RegionType> regionTypeList = regionTypeRepository.findAll();
        assertThat(regionTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the RegionType in Elasticsearch
        verify(mockRegionTypeSearchRepository, times(0)).save(regionType);
    }

    @Test
    @Transactional
    public void getAllRegionTypes() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);

        // Get all the regionTypeList
        restRegionTypeMockMvc.perform(get("/api/region-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.intValue())));
    }
    

    @Test
    @Transactional
    public void getRegionType() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);

        // Get the regionType
        restRegionTypeMockMvc.perform(get("/api/region-types/{id}", regionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regionType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.intValue()));
    }

    @Test
    @Transactional
    public void getAllRegionTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);

        // Get all the regionTypeList where name equals to DEFAULT_NAME
        defaultRegionTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the regionTypeList where name equals to UPDATED_NAME
        defaultRegionTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);

        // Get all the regionTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRegionTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the regionTypeList where name equals to UPDATED_NAME
        defaultRegionTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);

        // Get all the regionTypeList where name is not null
        defaultRegionTypeShouldBeFound("name.specified=true");

        // Get all the regionTypeList where name is null
        defaultRegionTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegionTypesByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);

        // Get all the regionTypeList where priority equals to DEFAULT_PRIORITY
        defaultRegionTypeShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the regionTypeList where priority equals to UPDATED_PRIORITY
        defaultRegionTypeShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllRegionTypesByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);

        // Get all the regionTypeList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultRegionTypeShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the regionTypeList where priority equals to UPDATED_PRIORITY
        defaultRegionTypeShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllRegionTypesByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);

        // Get all the regionTypeList where priority is not null
        defaultRegionTypeShouldBeFound("priority.specified=true");

        // Get all the regionTypeList where priority is null
        defaultRegionTypeShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegionTypesByPriorityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);

        // Get all the regionTypeList where priority greater than or equals to DEFAULT_PRIORITY
        defaultRegionTypeShouldBeFound("priority.greaterOrEqualThan=" + DEFAULT_PRIORITY);

        // Get all the regionTypeList where priority greater than or equals to UPDATED_PRIORITY
        defaultRegionTypeShouldNotBeFound("priority.greaterOrEqualThan=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllRegionTypesByPriorityIsLessThanSomething() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);

        // Get all the regionTypeList where priority less than or equals to DEFAULT_PRIORITY
        defaultRegionTypeShouldNotBeFound("priority.lessThan=" + DEFAULT_PRIORITY);

        // Get all the regionTypeList where priority less than or equals to UPDATED_PRIORITY
        defaultRegionTypeShouldBeFound("priority.lessThan=" + UPDATED_PRIORITY);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRegionTypeShouldBeFound(String filter) throws Exception {
        restRegionTypeMockMvc.perform(get("/api/region-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRegionTypeShouldNotBeFound(String filter) throws Exception {
        restRegionTypeMockMvc.perform(get("/api/region-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingRegionType() throws Exception {
        // Get the regionType
        restRegionTypeMockMvc.perform(get("/api/region-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegionType() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);

        int databaseSizeBeforeUpdate = regionTypeRepository.findAll().size();

        // Update the regionType
        RegionType updatedRegionType = regionTypeRepository.findById(regionType.getId()).get();
        // Disconnect from session so that the updates on updatedRegionType are not directly saved in db
        em.detach(updatedRegionType);
        updatedRegionType
            .name(UPDATED_NAME)
            .priority(UPDATED_PRIORITY);
        RegionTypeDTO regionTypeDTO = regionTypeMapper.toDto(updatedRegionType);

        restRegionTypeMockMvc.perform(put("/api/region-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionTypeDTO)))
            .andExpect(status().isOk());

        // Validate the RegionType in the database
        List<RegionType> regionTypeList = regionTypeRepository.findAll();
        assertThat(regionTypeList).hasSize(databaseSizeBeforeUpdate);
        RegionType testRegionType = regionTypeList.get(regionTypeList.size() - 1);
        assertThat(testRegionType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRegionType.getPriority()).isEqualTo(UPDATED_PRIORITY);

        // Validate the RegionType in Elasticsearch
        verify(mockRegionTypeSearchRepository, times(1)).save(testRegionType);
    }

    @Test
    @Transactional
    public void updateNonExistingRegionType() throws Exception {
        int databaseSizeBeforeUpdate = regionTypeRepository.findAll().size();

        // Create the RegionType
        RegionTypeDTO regionTypeDTO = regionTypeMapper.toDto(regionType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegionTypeMockMvc.perform(put("/api/region-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegionType in the database
        List<RegionType> regionTypeList = regionTypeRepository.findAll();
        assertThat(regionTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RegionType in Elasticsearch
        verify(mockRegionTypeSearchRepository, times(0)).save(regionType);
    }

    @Test
    @Transactional
    public void deleteRegionType() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);

        int databaseSizeBeforeDelete = regionTypeRepository.findAll().size();

        // Get the regionType
        restRegionTypeMockMvc.perform(delete("/api/region-types/{id}", regionType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RegionType> regionTypeList = regionTypeRepository.findAll();
        assertThat(regionTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RegionType in Elasticsearch
        verify(mockRegionTypeSearchRepository, times(1)).deleteById(regionType.getId());
    }

    @Test
    @Transactional
    public void searchRegionType() throws Exception {
        // Initialize the database
        regionTypeRepository.saveAndFlush(regionType);
        when(mockRegionTypeSearchRepository.search(queryStringQuery("id:" + regionType.getId())))
            .thenReturn(Collections.singletonList(regionType));
        // Search the regionType
        restRegionTypeMockMvc.perform(get("/api/_search/region-types?query=id:" + regionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegionType.class);
        RegionType regionType1 = new RegionType();
        regionType1.setId(1L);
        RegionType regionType2 = new RegionType();
        regionType2.setId(regionType1.getId());
        assertThat(regionType1).isEqualTo(regionType2);
        regionType2.setId(2L);
        assertThat(regionType1).isNotEqualTo(regionType2);
        regionType1.setId(null);
        assertThat(regionType1).isNotEqualTo(regionType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegionTypeDTO.class);
        RegionTypeDTO regionTypeDTO1 = new RegionTypeDTO();
        regionTypeDTO1.setId(1L);
        RegionTypeDTO regionTypeDTO2 = new RegionTypeDTO();
        assertThat(regionTypeDTO1).isNotEqualTo(regionTypeDTO2);
        regionTypeDTO2.setId(regionTypeDTO1.getId());
        assertThat(regionTypeDTO1).isEqualTo(regionTypeDTO2);
        regionTypeDTO2.setId(2L);
        assertThat(regionTypeDTO1).isNotEqualTo(regionTypeDTO2);
        regionTypeDTO1.setId(null);
        assertThat(regionTypeDTO1).isNotEqualTo(regionTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(regionTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(regionTypeMapper.fromId(null)).isNull();
    }
}
