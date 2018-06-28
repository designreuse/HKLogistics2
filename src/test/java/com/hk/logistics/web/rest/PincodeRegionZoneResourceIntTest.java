package com.hk.logistics.web.rest;

import com.hk.logistics.HkLogisticsApp;

import com.hk.logistics.domain.PincodeRegionZone;
import com.hk.logistics.domain.RegionType;
import com.hk.logistics.domain.CourierGroup;
import com.hk.logistics.domain.SourceDestinationMapping;
import com.hk.logistics.repository.PincodeRegionZoneRepository;
import com.hk.logistics.repository.search.PincodeRegionZoneSearchRepository;
import com.hk.logistics.service.PincodeRegionZoneService;
import com.hk.logistics.service.dto.PincodeRegionZoneDTO;
import com.hk.logistics.service.mapper.PincodeRegionZoneMapper;
import com.hk.logistics.web.rest.errors.ExceptionTranslator;
import com.hk.logistics.service.dto.PincodeRegionZoneCriteria;
import com.hk.logistics.service.PincodeRegionZoneQueryService;

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
 * Test class for the PincodeRegionZoneResource REST controller.
 *
 * @see PincodeRegionZoneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HkLogisticsApp.class)
public class PincodeRegionZoneResourceIntTest {

    @Autowired
    private PincodeRegionZoneRepository pincodeRegionZoneRepository;


    @Autowired
    private PincodeRegionZoneMapper pincodeRegionZoneMapper;
    

    @Autowired
    private PincodeRegionZoneService pincodeRegionZoneService;

    /**
     * This repository is mocked in the com.hk.logistics.repository.search test package.
     *
     * @see com.hk.logistics.repository.search.PincodeRegionZoneSearchRepositoryMockConfiguration
     */
    @Autowired
    private PincodeRegionZoneSearchRepository mockPincodeRegionZoneSearchRepository;

    @Autowired
    private PincodeRegionZoneQueryService pincodeRegionZoneQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPincodeRegionZoneMockMvc;

    private PincodeRegionZone pincodeRegionZone;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PincodeRegionZoneResource pincodeRegionZoneResource = new PincodeRegionZoneResource(pincodeRegionZoneService, pincodeRegionZoneQueryService);
        this.restPincodeRegionZoneMockMvc = MockMvcBuilders.standaloneSetup(pincodeRegionZoneResource)
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
    public static PincodeRegionZone createEntity(EntityManager em) {
        PincodeRegionZone pincodeRegionZone = new PincodeRegionZone();
        return pincodeRegionZone;
    }

    @Before
    public void initTest() {
        pincodeRegionZone = createEntity(em);
    }

    @Test
    @Transactional
    public void createPincodeRegionZone() throws Exception {
        int databaseSizeBeforeCreate = pincodeRegionZoneRepository.findAll().size();

        // Create the PincodeRegionZone
        PincodeRegionZoneDTO pincodeRegionZoneDTO = pincodeRegionZoneMapper.toDto(pincodeRegionZone);
        restPincodeRegionZoneMockMvc.perform(post("/api/pincode-region-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeRegionZoneDTO)))
            .andExpect(status().isCreated());

        // Validate the PincodeRegionZone in the database
        List<PincodeRegionZone> pincodeRegionZoneList = pincodeRegionZoneRepository.findAll();
        assertThat(pincodeRegionZoneList).hasSize(databaseSizeBeforeCreate + 1);
        PincodeRegionZone testPincodeRegionZone = pincodeRegionZoneList.get(pincodeRegionZoneList.size() - 1);

        // Validate the PincodeRegionZone in Elasticsearch
        verify(mockPincodeRegionZoneSearchRepository, times(1)).save(testPincodeRegionZone);
    }

    @Test
    @Transactional
    public void createPincodeRegionZoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pincodeRegionZoneRepository.findAll().size();

        // Create the PincodeRegionZone with an existing ID
        pincodeRegionZone.setId(1L);
        PincodeRegionZoneDTO pincodeRegionZoneDTO = pincodeRegionZoneMapper.toDto(pincodeRegionZone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPincodeRegionZoneMockMvc.perform(post("/api/pincode-region-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeRegionZoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PincodeRegionZone in the database
        List<PincodeRegionZone> pincodeRegionZoneList = pincodeRegionZoneRepository.findAll();
        assertThat(pincodeRegionZoneList).hasSize(databaseSizeBeforeCreate);

        // Validate the PincodeRegionZone in Elasticsearch
        verify(mockPincodeRegionZoneSearchRepository, times(0)).save(pincodeRegionZone);
    }

    @Test
    @Transactional
    public void getAllPincodeRegionZones() throws Exception {
        // Initialize the database
        pincodeRegionZoneRepository.saveAndFlush(pincodeRegionZone);

        // Get all the pincodeRegionZoneList
        restPincodeRegionZoneMockMvc.perform(get("/api/pincode-region-zones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pincodeRegionZone.getId().intValue())));
    }
    

    @Test
    @Transactional
    public void getPincodeRegionZone() throws Exception {
        // Initialize the database
        pincodeRegionZoneRepository.saveAndFlush(pincodeRegionZone);

        // Get the pincodeRegionZone
        restPincodeRegionZoneMockMvc.perform(get("/api/pincode-region-zones/{id}", pincodeRegionZone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pincodeRegionZone.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllPincodeRegionZonesByRegionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        RegionType regionType = RegionTypeResourceIntTest.createEntity(em);
        em.persist(regionType);
        em.flush();
        pincodeRegionZone.setRegionType(regionType);
        pincodeRegionZoneRepository.saveAndFlush(pincodeRegionZone);
        Long regionTypeId = regionType.getId();

        // Get all the pincodeRegionZoneList where regionType equals to regionTypeId
        defaultPincodeRegionZoneShouldBeFound("regionTypeId.equals=" + regionTypeId);

        // Get all the pincodeRegionZoneList where regionType equals to regionTypeId + 1
        defaultPincodeRegionZoneShouldNotBeFound("regionTypeId.equals=" + (regionTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllPincodeRegionZonesByCourierGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        CourierGroup courierGroup = CourierGroupResourceIntTest.createEntity(em);
        em.persist(courierGroup);
        em.flush();
        pincodeRegionZone.setCourierGroup(courierGroup);
        pincodeRegionZoneRepository.saveAndFlush(pincodeRegionZone);
        Long courierGroupId = courierGroup.getId();

        // Get all the pincodeRegionZoneList where courierGroup equals to courierGroupId
        defaultPincodeRegionZoneShouldBeFound("courierGroupId.equals=" + courierGroupId);

        // Get all the pincodeRegionZoneList where courierGroup equals to courierGroupId + 1
        defaultPincodeRegionZoneShouldNotBeFound("courierGroupId.equals=" + (courierGroupId + 1));
    }


    @Test
    @Transactional
    public void getAllPincodeRegionZonesBySourceDestinationMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        SourceDestinationMapping sourceDestinationMapping = SourceDestinationMappingResourceIntTest.createEntity(em);
        em.persist(sourceDestinationMapping);
        em.flush();
        pincodeRegionZone.setSourceDestinationMapping(sourceDestinationMapping);
        pincodeRegionZoneRepository.saveAndFlush(pincodeRegionZone);
        Long sourceDestinationMappingId = sourceDestinationMapping.getId();

        // Get all the pincodeRegionZoneList where sourceDestinationMapping equals to sourceDestinationMappingId
        defaultPincodeRegionZoneShouldBeFound("sourceDestinationMappingId.equals=" + sourceDestinationMappingId);

        // Get all the pincodeRegionZoneList where sourceDestinationMapping equals to sourceDestinationMappingId + 1
        defaultPincodeRegionZoneShouldNotBeFound("sourceDestinationMappingId.equals=" + (sourceDestinationMappingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPincodeRegionZoneShouldBeFound(String filter) throws Exception {
        restPincodeRegionZoneMockMvc.perform(get("/api/pincode-region-zones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pincodeRegionZone.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPincodeRegionZoneShouldNotBeFound(String filter) throws Exception {
        restPincodeRegionZoneMockMvc.perform(get("/api/pincode-region-zones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingPincodeRegionZone() throws Exception {
        // Get the pincodeRegionZone
        restPincodeRegionZoneMockMvc.perform(get("/api/pincode-region-zones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePincodeRegionZone() throws Exception {
        // Initialize the database
        pincodeRegionZoneRepository.saveAndFlush(pincodeRegionZone);

        int databaseSizeBeforeUpdate = pincodeRegionZoneRepository.findAll().size();

        // Update the pincodeRegionZone
        PincodeRegionZone updatedPincodeRegionZone = pincodeRegionZoneRepository.findById(pincodeRegionZone.getId()).get();
        // Disconnect from session so that the updates on updatedPincodeRegionZone are not directly saved in db
        em.detach(updatedPincodeRegionZone);
        PincodeRegionZoneDTO pincodeRegionZoneDTO = pincodeRegionZoneMapper.toDto(updatedPincodeRegionZone);

        restPincodeRegionZoneMockMvc.perform(put("/api/pincode-region-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeRegionZoneDTO)))
            .andExpect(status().isOk());

        // Validate the PincodeRegionZone in the database
        List<PincodeRegionZone> pincodeRegionZoneList = pincodeRegionZoneRepository.findAll();
        assertThat(pincodeRegionZoneList).hasSize(databaseSizeBeforeUpdate);
        PincodeRegionZone testPincodeRegionZone = pincodeRegionZoneList.get(pincodeRegionZoneList.size() - 1);

        // Validate the PincodeRegionZone in Elasticsearch
        verify(mockPincodeRegionZoneSearchRepository, times(1)).save(testPincodeRegionZone);
    }

    @Test
    @Transactional
    public void updateNonExistingPincodeRegionZone() throws Exception {
        int databaseSizeBeforeUpdate = pincodeRegionZoneRepository.findAll().size();

        // Create the PincodeRegionZone
        PincodeRegionZoneDTO pincodeRegionZoneDTO = pincodeRegionZoneMapper.toDto(pincodeRegionZone);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPincodeRegionZoneMockMvc.perform(put("/api/pincode-region-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeRegionZoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PincodeRegionZone in the database
        List<PincodeRegionZone> pincodeRegionZoneList = pincodeRegionZoneRepository.findAll();
        assertThat(pincodeRegionZoneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PincodeRegionZone in Elasticsearch
        verify(mockPincodeRegionZoneSearchRepository, times(0)).save(pincodeRegionZone);
    }

    @Test
    @Transactional
    public void deletePincodeRegionZone() throws Exception {
        // Initialize the database
        pincodeRegionZoneRepository.saveAndFlush(pincodeRegionZone);

        int databaseSizeBeforeDelete = pincodeRegionZoneRepository.findAll().size();

        // Get the pincodeRegionZone
        restPincodeRegionZoneMockMvc.perform(delete("/api/pincode-region-zones/{id}", pincodeRegionZone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PincodeRegionZone> pincodeRegionZoneList = pincodeRegionZoneRepository.findAll();
        assertThat(pincodeRegionZoneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PincodeRegionZone in Elasticsearch
        verify(mockPincodeRegionZoneSearchRepository, times(1)).deleteById(pincodeRegionZone.getId());
    }

    @Test
    @Transactional
    public void searchPincodeRegionZone() throws Exception {
        // Initialize the database
        pincodeRegionZoneRepository.saveAndFlush(pincodeRegionZone);
        when(mockPincodeRegionZoneSearchRepository.search(queryStringQuery("id:" + pincodeRegionZone.getId())))
            .thenReturn(Collections.singletonList(pincodeRegionZone));
        // Search the pincodeRegionZone
        restPincodeRegionZoneMockMvc.perform(get("/api/_search/pincode-region-zones?query=id:" + pincodeRegionZone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pincodeRegionZone.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PincodeRegionZone.class);
        PincodeRegionZone pincodeRegionZone1 = new PincodeRegionZone();
        pincodeRegionZone1.setId(1L);
        PincodeRegionZone pincodeRegionZone2 = new PincodeRegionZone();
        pincodeRegionZone2.setId(pincodeRegionZone1.getId());
        assertThat(pincodeRegionZone1).isEqualTo(pincodeRegionZone2);
        pincodeRegionZone2.setId(2L);
        assertThat(pincodeRegionZone1).isNotEqualTo(pincodeRegionZone2);
        pincodeRegionZone1.setId(null);
        assertThat(pincodeRegionZone1).isNotEqualTo(pincodeRegionZone2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PincodeRegionZoneDTO.class);
        PincodeRegionZoneDTO pincodeRegionZoneDTO1 = new PincodeRegionZoneDTO();
        pincodeRegionZoneDTO1.setId(1L);
        PincodeRegionZoneDTO pincodeRegionZoneDTO2 = new PincodeRegionZoneDTO();
        assertThat(pincodeRegionZoneDTO1).isNotEqualTo(pincodeRegionZoneDTO2);
        pincodeRegionZoneDTO2.setId(pincodeRegionZoneDTO1.getId());
        assertThat(pincodeRegionZoneDTO1).isEqualTo(pincodeRegionZoneDTO2);
        pincodeRegionZoneDTO2.setId(2L);
        assertThat(pincodeRegionZoneDTO1).isNotEqualTo(pincodeRegionZoneDTO2);
        pincodeRegionZoneDTO1.setId(null);
        assertThat(pincodeRegionZoneDTO1).isNotEqualTo(pincodeRegionZoneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pincodeRegionZoneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pincodeRegionZoneMapper.fromId(null)).isNull();
    }
}
