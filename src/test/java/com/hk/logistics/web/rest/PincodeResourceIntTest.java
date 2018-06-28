package com.hk.logistics.web.rest;

import com.hk.logistics.HkLogisticsApp;

import com.hk.logistics.domain.Pincode;
import com.hk.logistics.domain.City;
import com.hk.logistics.domain.State;
import com.hk.logistics.domain.Zone;
import com.hk.logistics.domain.Hub;
import com.hk.logistics.repository.PincodeRepository;
import com.hk.logistics.repository.search.PincodeSearchRepository;
import com.hk.logistics.service.PincodeService;
import com.hk.logistics.service.dto.PincodeDTO;
import com.hk.logistics.service.mapper.PincodeMapper;
import com.hk.logistics.web.rest.errors.ExceptionTranslator;
import com.hk.logistics.service.dto.PincodeCriteria;
import com.hk.logistics.service.PincodeQueryService;

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
 * Test class for the PincodeResource REST controller.
 *
 * @see PincodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HkLogisticsApp.class)
public class PincodeResourceIntTest {

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALITY = "AAAAAAAAAA";
    private static final String UPDATED_LOCALITY = "BBBBBBBBBB";

    private static final Double DEFAULT_LAST_MILE_COST = 1D;
    private static final Double UPDATED_LAST_MILE_COST = 2D;

    private static final String DEFAULT_TIER = "AAAAAAAAAA";
    private static final String UPDATED_TIER = "BBBBBBBBBB";

    @Autowired
    private PincodeRepository pincodeRepository;


    @Autowired
    private PincodeMapper pincodeMapper;
    

    @Autowired
    private PincodeService pincodeService;

    /**
     * This repository is mocked in the com.hk.logistics.repository.search test package.
     *
     * @see com.hk.logistics.repository.search.PincodeSearchRepositoryMockConfiguration
     */
    @Autowired
    private PincodeSearchRepository mockPincodeSearchRepository;

    @Autowired
    private PincodeQueryService pincodeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPincodeMockMvc;

    private Pincode pincode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PincodeResource pincodeResource = new PincodeResource(pincodeService, pincodeQueryService);
        this.restPincodeMockMvc = MockMvcBuilders.standaloneSetup(pincodeResource)
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
    public static Pincode createEntity(EntityManager em) {
        Pincode pincode = new Pincode()
            .pincode(DEFAULT_PINCODE)
            .region(DEFAULT_REGION)
            .locality(DEFAULT_LOCALITY)
            .lastMileCost(DEFAULT_LAST_MILE_COST)
            .tier(DEFAULT_TIER);
        return pincode;
    }

    @Before
    public void initTest() {
        pincode = createEntity(em);
    }

    @Test
    @Transactional
    public void createPincode() throws Exception {
        int databaseSizeBeforeCreate = pincodeRepository.findAll().size();

        // Create the Pincode
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);
        restPincodeMockMvc.perform(post("/api/pincodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeDTO)))
            .andExpect(status().isCreated());

        // Validate the Pincode in the database
        List<Pincode> pincodeList = pincodeRepository.findAll();
        assertThat(pincodeList).hasSize(databaseSizeBeforeCreate + 1);
        Pincode testPincode = pincodeList.get(pincodeList.size() - 1);
        assertThat(testPincode.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testPincode.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testPincode.getLocality()).isEqualTo(DEFAULT_LOCALITY);
        assertThat(testPincode.getLastMileCost()).isEqualTo(DEFAULT_LAST_MILE_COST);
        assertThat(testPincode.getTier()).isEqualTo(DEFAULT_TIER);

        // Validate the Pincode in Elasticsearch
        verify(mockPincodeSearchRepository, times(1)).save(testPincode);
    }

    @Test
    @Transactional
    public void createPincodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pincodeRepository.findAll().size();

        // Create the Pincode with an existing ID
        pincode.setId(1L);
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPincodeMockMvc.perform(post("/api/pincodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pincode in the database
        List<Pincode> pincodeList = pincodeRepository.findAll();
        assertThat(pincodeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Pincode in Elasticsearch
        verify(mockPincodeSearchRepository, times(0)).save(pincode);
    }

    @Test
    @Transactional
    public void checkPincodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pincodeRepository.findAll().size();
        // set the field null
        pincode.setPincode(null);

        // Create the Pincode, which fails.
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        restPincodeMockMvc.perform(post("/api/pincodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeDTO)))
            .andExpect(status().isBadRequest());

        List<Pincode> pincodeList = pincodeRepository.findAll();
        assertThat(pincodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPincodes() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList
        restPincodeMockMvc.perform(get("/api/pincodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pincode.getId().intValue())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].locality").value(hasItem(DEFAULT_LOCALITY.toString())))
            .andExpect(jsonPath("$.[*].lastMileCost").value(hasItem(DEFAULT_LAST_MILE_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].tier").value(hasItem(DEFAULT_TIER.toString())));
    }
    

    @Test
    @Transactional
    public void getPincode() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get the pincode
        restPincodeMockMvc.perform(get("/api/pincodes/{id}", pincode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pincode.getId().intValue()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.locality").value(DEFAULT_LOCALITY.toString()))
            .andExpect(jsonPath("$.lastMileCost").value(DEFAULT_LAST_MILE_COST.doubleValue()))
            .andExpect(jsonPath("$.tier").value(DEFAULT_TIER.toString()));
    }

    @Test
    @Transactional
    public void getAllPincodesByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where pincode equals to DEFAULT_PINCODE
        defaultPincodeShouldBeFound("pincode.equals=" + DEFAULT_PINCODE);

        // Get all the pincodeList where pincode equals to UPDATED_PINCODE
        defaultPincodeShouldNotBeFound("pincode.equals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    public void getAllPincodesByPincodeIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where pincode in DEFAULT_PINCODE or UPDATED_PINCODE
        defaultPincodeShouldBeFound("pincode.in=" + DEFAULT_PINCODE + "," + UPDATED_PINCODE);

        // Get all the pincodeList where pincode equals to UPDATED_PINCODE
        defaultPincodeShouldNotBeFound("pincode.in=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    public void getAllPincodesByPincodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where pincode is not null
        defaultPincodeShouldBeFound("pincode.specified=true");

        // Get all the pincodeList where pincode is null
        defaultPincodeShouldNotBeFound("pincode.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where region equals to DEFAULT_REGION
        defaultPincodeShouldBeFound("region.equals=" + DEFAULT_REGION);

        // Get all the pincodeList where region equals to UPDATED_REGION
        defaultPincodeShouldNotBeFound("region.equals=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllPincodesByRegionIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where region in DEFAULT_REGION or UPDATED_REGION
        defaultPincodeShouldBeFound("region.in=" + DEFAULT_REGION + "," + UPDATED_REGION);

        // Get all the pincodeList where region equals to UPDATED_REGION
        defaultPincodeShouldNotBeFound("region.in=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllPincodesByRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where region is not null
        defaultPincodeShouldBeFound("region.specified=true");

        // Get all the pincodeList where region is null
        defaultPincodeShouldNotBeFound("region.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodesByLocalityIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where locality equals to DEFAULT_LOCALITY
        defaultPincodeShouldBeFound("locality.equals=" + DEFAULT_LOCALITY);

        // Get all the pincodeList where locality equals to UPDATED_LOCALITY
        defaultPincodeShouldNotBeFound("locality.equals=" + UPDATED_LOCALITY);
    }

    @Test
    @Transactional
    public void getAllPincodesByLocalityIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where locality in DEFAULT_LOCALITY or UPDATED_LOCALITY
        defaultPincodeShouldBeFound("locality.in=" + DEFAULT_LOCALITY + "," + UPDATED_LOCALITY);

        // Get all the pincodeList where locality equals to UPDATED_LOCALITY
        defaultPincodeShouldNotBeFound("locality.in=" + UPDATED_LOCALITY);
    }

    @Test
    @Transactional
    public void getAllPincodesByLocalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where locality is not null
        defaultPincodeShouldBeFound("locality.specified=true");

        // Get all the pincodeList where locality is null
        defaultPincodeShouldNotBeFound("locality.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodesByLastMileCostIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where lastMileCost equals to DEFAULT_LAST_MILE_COST
        defaultPincodeShouldBeFound("lastMileCost.equals=" + DEFAULT_LAST_MILE_COST);

        // Get all the pincodeList where lastMileCost equals to UPDATED_LAST_MILE_COST
        defaultPincodeShouldNotBeFound("lastMileCost.equals=" + UPDATED_LAST_MILE_COST);
    }

    @Test
    @Transactional
    public void getAllPincodesByLastMileCostIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where lastMileCost in DEFAULT_LAST_MILE_COST or UPDATED_LAST_MILE_COST
        defaultPincodeShouldBeFound("lastMileCost.in=" + DEFAULT_LAST_MILE_COST + "," + UPDATED_LAST_MILE_COST);

        // Get all the pincodeList where lastMileCost equals to UPDATED_LAST_MILE_COST
        defaultPincodeShouldNotBeFound("lastMileCost.in=" + UPDATED_LAST_MILE_COST);
    }

    @Test
    @Transactional
    public void getAllPincodesByLastMileCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where lastMileCost is not null
        defaultPincodeShouldBeFound("lastMileCost.specified=true");

        // Get all the pincodeList where lastMileCost is null
        defaultPincodeShouldNotBeFound("lastMileCost.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodesByTierIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where tier equals to DEFAULT_TIER
        defaultPincodeShouldBeFound("tier.equals=" + DEFAULT_TIER);

        // Get all the pincodeList where tier equals to UPDATED_TIER
        defaultPincodeShouldNotBeFound("tier.equals=" + UPDATED_TIER);
    }

    @Test
    @Transactional
    public void getAllPincodesByTierIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where tier in DEFAULT_TIER or UPDATED_TIER
        defaultPincodeShouldBeFound("tier.in=" + DEFAULT_TIER + "," + UPDATED_TIER);

        // Get all the pincodeList where tier equals to UPDATED_TIER
        defaultPincodeShouldNotBeFound("tier.in=" + UPDATED_TIER);
    }

    @Test
    @Transactional
    public void getAllPincodesByTierIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where tier is not null
        defaultPincodeShouldBeFound("tier.specified=true");

        // Get all the pincodeList where tier is null
        defaultPincodeShouldNotBeFound("tier.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        City city = CityResourceIntTest.createEntity(em);
        em.persist(city);
        em.flush();
        pincode.setCity(city);
        pincodeRepository.saveAndFlush(pincode);
        Long cityId = city.getId();

        // Get all the pincodeList where city equals to cityId
        defaultPincodeShouldBeFound("cityId.equals=" + cityId);

        // Get all the pincodeList where city equals to cityId + 1
        defaultPincodeShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }


    @Test
    @Transactional
    public void getAllPincodesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        State state = StateResourceIntTest.createEntity(em);
        em.persist(state);
        em.flush();
        pincode.setState(state);
        pincodeRepository.saveAndFlush(pincode);
        Long stateId = state.getId();

        // Get all the pincodeList where state equals to stateId
        defaultPincodeShouldBeFound("stateId.equals=" + stateId);

        // Get all the pincodeList where state equals to stateId + 1
        defaultPincodeShouldNotBeFound("stateId.equals=" + (stateId + 1));
    }


    @Test
    @Transactional
    public void getAllPincodesByZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        Zone zone = ZoneResourceIntTest.createEntity(em);
        em.persist(zone);
        em.flush();
        pincode.setZone(zone);
        pincodeRepository.saveAndFlush(pincode);
        Long zoneId = zone.getId();

        // Get all the pincodeList where zone equals to zoneId
        defaultPincodeShouldBeFound("zoneId.equals=" + zoneId);

        // Get all the pincodeList where zone equals to zoneId + 1
        defaultPincodeShouldNotBeFound("zoneId.equals=" + (zoneId + 1));
    }


    @Test
    @Transactional
    public void getAllPincodesByHubIsEqualToSomething() throws Exception {
        // Initialize the database
        Hub hub = HubResourceIntTest.createEntity(em);
        em.persist(hub);
        em.flush();
        pincode.setHub(hub);
        pincodeRepository.saveAndFlush(pincode);
        Long hubId = hub.getId();

        // Get all the pincodeList where hub equals to hubId
        defaultPincodeShouldBeFound("hubId.equals=" + hubId);

        // Get all the pincodeList where hub equals to hubId + 1
        defaultPincodeShouldNotBeFound("hubId.equals=" + (hubId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPincodeShouldBeFound(String filter) throws Exception {
        restPincodeMockMvc.perform(get("/api/pincodes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pincode.getId().intValue())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].locality").value(hasItem(DEFAULT_LOCALITY.toString())))
            .andExpect(jsonPath("$.[*].lastMileCost").value(hasItem(DEFAULT_LAST_MILE_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].tier").value(hasItem(DEFAULT_TIER.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPincodeShouldNotBeFound(String filter) throws Exception {
        restPincodeMockMvc.perform(get("/api/pincodes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingPincode() throws Exception {
        // Get the pincode
        restPincodeMockMvc.perform(get("/api/pincodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePincode() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        int databaseSizeBeforeUpdate = pincodeRepository.findAll().size();

        // Update the pincode
        Pincode updatedPincode = pincodeRepository.findById(pincode.getId()).get();
        // Disconnect from session so that the updates on updatedPincode are not directly saved in db
        em.detach(updatedPincode);
        updatedPincode
            .pincode(UPDATED_PINCODE)
            .region(UPDATED_REGION)
            .locality(UPDATED_LOCALITY)
            .lastMileCost(UPDATED_LAST_MILE_COST)
            .tier(UPDATED_TIER);
        PincodeDTO pincodeDTO = pincodeMapper.toDto(updatedPincode);

        restPincodeMockMvc.perform(put("/api/pincodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeDTO)))
            .andExpect(status().isOk());

        // Validate the Pincode in the database
        List<Pincode> pincodeList = pincodeRepository.findAll();
        assertThat(pincodeList).hasSize(databaseSizeBeforeUpdate);
        Pincode testPincode = pincodeList.get(pincodeList.size() - 1);
        assertThat(testPincode.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testPincode.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testPincode.getLocality()).isEqualTo(UPDATED_LOCALITY);
        assertThat(testPincode.getLastMileCost()).isEqualTo(UPDATED_LAST_MILE_COST);
        assertThat(testPincode.getTier()).isEqualTo(UPDATED_TIER);

        // Validate the Pincode in Elasticsearch
        verify(mockPincodeSearchRepository, times(1)).save(testPincode);
    }

    @Test
    @Transactional
    public void updateNonExistingPincode() throws Exception {
        int databaseSizeBeforeUpdate = pincodeRepository.findAll().size();

        // Create the Pincode
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPincodeMockMvc.perform(put("/api/pincodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pincode in the database
        List<Pincode> pincodeList = pincodeRepository.findAll();
        assertThat(pincodeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Pincode in Elasticsearch
        verify(mockPincodeSearchRepository, times(0)).save(pincode);
    }

    @Test
    @Transactional
    public void deletePincode() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);

        int databaseSizeBeforeDelete = pincodeRepository.findAll().size();

        // Get the pincode
        restPincodeMockMvc.perform(delete("/api/pincodes/{id}", pincode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pincode> pincodeList = pincodeRepository.findAll();
        assertThat(pincodeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Pincode in Elasticsearch
        verify(mockPincodeSearchRepository, times(1)).deleteById(pincode.getId());
    }

    @Test
    @Transactional
    public void searchPincode() throws Exception {
        // Initialize the database
        pincodeRepository.saveAndFlush(pincode);
        when(mockPincodeSearchRepository.search(queryStringQuery("id:" + pincode.getId())))
            .thenReturn(Collections.singletonList(pincode));
        // Search the pincode
        restPincodeMockMvc.perform(get("/api/_search/pincodes?query=id:" + pincode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pincode.getId().intValue())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].locality").value(hasItem(DEFAULT_LOCALITY.toString())))
            .andExpect(jsonPath("$.[*].lastMileCost").value(hasItem(DEFAULT_LAST_MILE_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].tier").value(hasItem(DEFAULT_TIER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pincode.class);
        Pincode pincode1 = new Pincode();
        pincode1.setId(1L);
        Pincode pincode2 = new Pincode();
        pincode2.setId(pincode1.getId());
        assertThat(pincode1).isEqualTo(pincode2);
        pincode2.setId(2L);
        assertThat(pincode1).isNotEqualTo(pincode2);
        pincode1.setId(null);
        assertThat(pincode1).isNotEqualTo(pincode2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PincodeDTO.class);
        PincodeDTO pincodeDTO1 = new PincodeDTO();
        pincodeDTO1.setId(1L);
        PincodeDTO pincodeDTO2 = new PincodeDTO();
        assertThat(pincodeDTO1).isNotEqualTo(pincodeDTO2);
        pincodeDTO2.setId(pincodeDTO1.getId());
        assertThat(pincodeDTO1).isEqualTo(pincodeDTO2);
        pincodeDTO2.setId(2L);
        assertThat(pincodeDTO1).isNotEqualTo(pincodeDTO2);
        pincodeDTO1.setId(null);
        assertThat(pincodeDTO1).isNotEqualTo(pincodeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pincodeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pincodeMapper.fromId(null)).isNull();
    }
}
