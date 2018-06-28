package com.hk.logistics.web.rest;

import com.hk.logistics.HkLogisticsApp;

import com.hk.logistics.domain.Hub;
import com.hk.logistics.domain.Pincode;
import com.hk.logistics.repository.HubRepository;
import com.hk.logistics.repository.search.HubSearchRepository;
import com.hk.logistics.service.HubService;
import com.hk.logistics.service.dto.HubDTO;
import com.hk.logistics.service.mapper.HubMapper;
import com.hk.logistics.web.rest.errors.ExceptionTranslator;
import com.hk.logistics.service.dto.HubCriteria;
import com.hk.logistics.service.HubQueryService;

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
 * Test class for the HubResource REST controller.
 *
 * @see HubResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HkLogisticsApp.class)
public class HubResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private HubRepository hubRepository;


    @Autowired
    private HubMapper hubMapper;
    

    @Autowired
    private HubService hubService;

    /**
     * This repository is mocked in the com.hk.logistics.repository.search test package.
     *
     * @see com.hk.logistics.repository.search.HubSearchRepositoryMockConfiguration
     */
    @Autowired
    private HubSearchRepository mockHubSearchRepository;

    @Autowired
    private HubQueryService hubQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHubMockMvc;

    private Hub hub;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HubResource hubResource = new HubResource(hubService, hubQueryService);
        this.restHubMockMvc = MockMvcBuilders.standaloneSetup(hubResource)
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
    public static Hub createEntity(EntityManager em) {
        Hub hub = new Hub()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .country(DEFAULT_COUNTRY);
        return hub;
    }

    @Before
    public void initTest() {
        hub = createEntity(em);
    }

    @Test
    @Transactional
    public void createHub() throws Exception {
        int databaseSizeBeforeCreate = hubRepository.findAll().size();

        // Create the Hub
        HubDTO hubDTO = hubMapper.toDto(hub);
        restHubMockMvc.perform(post("/api/hubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hubDTO)))
            .andExpect(status().isCreated());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeCreate + 1);
        Hub testHub = hubList.get(hubList.size() - 1);
        assertThat(testHub.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHub.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testHub.getCountry()).isEqualTo(DEFAULT_COUNTRY);

        // Validate the Hub in Elasticsearch
        verify(mockHubSearchRepository, times(1)).save(testHub);
    }

    @Test
    @Transactional
    public void createHubWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hubRepository.findAll().size();

        // Create the Hub with an existing ID
        hub.setId(1L);
        HubDTO hubDTO = hubMapper.toDto(hub);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHubMockMvc.perform(post("/api/hubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hubDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeCreate);

        // Validate the Hub in Elasticsearch
        verify(mockHubSearchRepository, times(0)).save(hub);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hubRepository.findAll().size();
        // set the field null
        hub.setName(null);

        // Create the Hub, which fails.
        HubDTO hubDTO = hubMapper.toDto(hub);

        restHubMockMvc.perform(post("/api/hubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hubDTO)))
            .andExpect(status().isBadRequest());

        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHubs() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        // Get all the hubList
        restHubMockMvc.perform(get("/api/hubs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hub.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }
    

    @Test
    @Transactional
    public void getHub() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        // Get the hub
        restHubMockMvc.perform(get("/api/hubs/{id}", hub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hub.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getAllHubsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        // Get all the hubList where name equals to DEFAULT_NAME
        defaultHubShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the hubList where name equals to UPDATED_NAME
        defaultHubShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHubsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        // Get all the hubList where name in DEFAULT_NAME or UPDATED_NAME
        defaultHubShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the hubList where name equals to UPDATED_NAME
        defaultHubShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHubsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        // Get all the hubList where name is not null
        defaultHubShouldBeFound("name.specified=true");

        // Get all the hubList where name is null
        defaultHubShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllHubsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        // Get all the hubList where address equals to DEFAULT_ADDRESS
        defaultHubShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the hubList where address equals to UPDATED_ADDRESS
        defaultHubShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllHubsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        // Get all the hubList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultHubShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the hubList where address equals to UPDATED_ADDRESS
        defaultHubShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllHubsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        // Get all the hubList where address is not null
        defaultHubShouldBeFound("address.specified=true");

        // Get all the hubList where address is null
        defaultHubShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllHubsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        // Get all the hubList where country equals to DEFAULT_COUNTRY
        defaultHubShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the hubList where country equals to UPDATED_COUNTRY
        defaultHubShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllHubsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        // Get all the hubList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultHubShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the hubList where country equals to UPDATED_COUNTRY
        defaultHubShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllHubsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        // Get all the hubList where country is not null
        defaultHubShouldBeFound("country.specified=true");

        // Get all the hubList where country is null
        defaultHubShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    public void getAllHubsByPinCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        Pincode pinCode = PincodeResourceIntTest.createEntity(em);
        em.persist(pinCode);
        em.flush();
        hub.addPinCode(pinCode);
        hubRepository.saveAndFlush(hub);
        Long pinCodeId = pinCode.getId();

        // Get all the hubList where pinCode equals to pinCodeId
        defaultHubShouldBeFound("pinCodeId.equals=" + pinCodeId);

        // Get all the hubList where pinCode equals to pinCodeId + 1
        defaultHubShouldNotBeFound("pinCodeId.equals=" + (pinCodeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultHubShouldBeFound(String filter) throws Exception {
        restHubMockMvc.perform(get("/api/hubs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hub.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultHubShouldNotBeFound(String filter) throws Exception {
        restHubMockMvc.perform(get("/api/hubs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingHub() throws Exception {
        // Get the hub
        restHubMockMvc.perform(get("/api/hubs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHub() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        int databaseSizeBeforeUpdate = hubRepository.findAll().size();

        // Update the hub
        Hub updatedHub = hubRepository.findById(hub.getId()).get();
        // Disconnect from session so that the updates on updatedHub are not directly saved in db
        em.detach(updatedHub);
        updatedHub
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .country(UPDATED_COUNTRY);
        HubDTO hubDTO = hubMapper.toDto(updatedHub);

        restHubMockMvc.perform(put("/api/hubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hubDTO)))
            .andExpect(status().isOk());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeUpdate);
        Hub testHub = hubList.get(hubList.size() - 1);
        assertThat(testHub.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHub.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testHub.getCountry()).isEqualTo(UPDATED_COUNTRY);

        // Validate the Hub in Elasticsearch
        verify(mockHubSearchRepository, times(1)).save(testHub);
    }

    @Test
    @Transactional
    public void updateNonExistingHub() throws Exception {
        int databaseSizeBeforeUpdate = hubRepository.findAll().size();

        // Create the Hub
        HubDTO hubDTO = hubMapper.toDto(hub);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHubMockMvc.perform(put("/api/hubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hubDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hub in the database
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Hub in Elasticsearch
        verify(mockHubSearchRepository, times(0)).save(hub);
    }

    @Test
    @Transactional
    public void deleteHub() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);

        int databaseSizeBeforeDelete = hubRepository.findAll().size();

        // Get the hub
        restHubMockMvc.perform(delete("/api/hubs/{id}", hub.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Hub> hubList = hubRepository.findAll();
        assertThat(hubList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Hub in Elasticsearch
        verify(mockHubSearchRepository, times(1)).deleteById(hub.getId());
    }

    @Test
    @Transactional
    public void searchHub() throws Exception {
        // Initialize the database
        hubRepository.saveAndFlush(hub);
        when(mockHubSearchRepository.search(queryStringQuery("id:" + hub.getId())))
            .thenReturn(Collections.singletonList(hub));
        // Search the hub
        restHubMockMvc.perform(get("/api/_search/hubs?query=id:" + hub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hub.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hub.class);
        Hub hub1 = new Hub();
        hub1.setId(1L);
        Hub hub2 = new Hub();
        hub2.setId(hub1.getId());
        assertThat(hub1).isEqualTo(hub2);
        hub2.setId(2L);
        assertThat(hub1).isNotEqualTo(hub2);
        hub1.setId(null);
        assertThat(hub1).isNotEqualTo(hub2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HubDTO.class);
        HubDTO hubDTO1 = new HubDTO();
        hubDTO1.setId(1L);
        HubDTO hubDTO2 = new HubDTO();
        assertThat(hubDTO1).isNotEqualTo(hubDTO2);
        hubDTO2.setId(hubDTO1.getId());
        assertThat(hubDTO1).isEqualTo(hubDTO2);
        hubDTO2.setId(2L);
        assertThat(hubDTO1).isNotEqualTo(hubDTO2);
        hubDTO1.setId(null);
        assertThat(hubDTO1).isNotEqualTo(hubDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(hubMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(hubMapper.fromId(null)).isNull();
    }
}
