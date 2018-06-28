package com.hk.logistics.web.rest;

import com.hk.logistics.HkLogisticsApp;

import com.hk.logistics.domain.CourierPricingEngine;
import com.hk.logistics.domain.Courier;
import com.hk.logistics.domain.RegionType;
import com.hk.logistics.repository.CourierPricingEngineRepository;
import com.hk.logistics.repository.search.CourierPricingEngineSearchRepository;
import com.hk.logistics.service.CourierPricingEngineService;
import com.hk.logistics.service.dto.CourierPricingEngineDTO;
import com.hk.logistics.service.mapper.CourierPricingEngineMapper;
import com.hk.logistics.web.rest.errors.ExceptionTranslator;
import com.hk.logistics.service.dto.CourierPricingEngineCriteria;
import com.hk.logistics.service.CourierPricingEngineQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the CourierPricingEngineResource REST controller.
 *
 * @see CourierPricingEngineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HkLogisticsApp.class)
public class CourierPricingEngineResourceIntTest {

    private static final Double DEFAULT_FIRST_BASE_WT = 1D;
    private static final Double UPDATED_FIRST_BASE_WT = 2D;

    private static final Double DEFAULT_FIRST_BASE_COST = 1D;
    private static final Double UPDATED_FIRST_BASE_COST = 2D;

    private static final Double DEFAULT_SECOND_BASE_WT = 1D;
    private static final Double UPDATED_SECOND_BASE_WT = 2D;

    private static final Double DEFAULT_SECOND_BASE_COST = 1D;
    private static final Double UPDATED_SECOND_BASE_COST = 2D;

    private static final Double DEFAULT_ADDITIONAL_WT = 1D;
    private static final Double UPDATED_ADDITIONAL_WT = 2D;

    private static final Double DEFAULT_ADDITIONAL_COST = 1D;
    private static final Double UPDATED_ADDITIONAL_COST = 2D;

    private static final Double DEFAULT_FUEL_SURCHARGE = 1D;
    private static final Double UPDATED_FUEL_SURCHARGE = 2D;

    private static final Double DEFAULT_MIN_COD_CHARGES = 1D;
    private static final Double UPDATED_MIN_COD_CHARGES = 2D;

    private static final Double DEFAULT_COD_CUTOFF_AMOUNT = 1D;
    private static final Double UPDATED_COD_CUTOFF_AMOUNT = 2D;

    private static final Double DEFAULT_VARIABLE_COD_CHARGES = 1D;
    private static final Double UPDATED_VARIABLE_COD_CHARGES = 2D;

    private static final LocalDate DEFAULT_VALID_UPTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_UPTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COST_PARAMETERS = "AAAAAAAAAA";
    private static final String UPDATED_COST_PARAMETERS = "BBBBBBBBBB";

    @Autowired
    private CourierPricingEngineRepository courierPricingEngineRepository;


    @Autowired
    private CourierPricingEngineMapper courierPricingEngineMapper;
    

    @Autowired
    private CourierPricingEngineService courierPricingEngineService;

    /**
     * This repository is mocked in the com.hk.logistics.repository.search test package.
     *
     * @see com.hk.logistics.repository.search.CourierPricingEngineSearchRepositoryMockConfiguration
     */
    @Autowired
    private CourierPricingEngineSearchRepository mockCourierPricingEngineSearchRepository;

    @Autowired
    private CourierPricingEngineQueryService courierPricingEngineQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCourierPricingEngineMockMvc;

    private CourierPricingEngine courierPricingEngine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourierPricingEngineResource courierPricingEngineResource = new CourierPricingEngineResource(courierPricingEngineService, courierPricingEngineQueryService);
        this.restCourierPricingEngineMockMvc = MockMvcBuilders.standaloneSetup(courierPricingEngineResource)
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
    public static CourierPricingEngine createEntity(EntityManager em) {
        CourierPricingEngine courierPricingEngine = new CourierPricingEngine()
            .firstBaseWt(DEFAULT_FIRST_BASE_WT)
            .firstBaseCost(DEFAULT_FIRST_BASE_COST)
            .secondBaseWt(DEFAULT_SECOND_BASE_WT)
            .secondBaseCost(DEFAULT_SECOND_BASE_COST)
            .additionalWt(DEFAULT_ADDITIONAL_WT)
            .additionalCost(DEFAULT_ADDITIONAL_COST)
            .fuelSurcharge(DEFAULT_FUEL_SURCHARGE)
            .minCodCharges(DEFAULT_MIN_COD_CHARGES)
            .codCutoffAmount(DEFAULT_COD_CUTOFF_AMOUNT)
            .variableCodCharges(DEFAULT_VARIABLE_COD_CHARGES)
            .validUpto(DEFAULT_VALID_UPTO)
            .costParameters(DEFAULT_COST_PARAMETERS);
        return courierPricingEngine;
    }

    @Before
    public void initTest() {
        courierPricingEngine = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourierPricingEngine() throws Exception {
        int databaseSizeBeforeCreate = courierPricingEngineRepository.findAll().size();

        // Create the CourierPricingEngine
        CourierPricingEngineDTO courierPricingEngineDTO = courierPricingEngineMapper.toDto(courierPricingEngine);
        restCourierPricingEngineMockMvc.perform(post("/api/courier-pricing-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierPricingEngineDTO)))
            .andExpect(status().isCreated());

        // Validate the CourierPricingEngine in the database
        List<CourierPricingEngine> courierPricingEngineList = courierPricingEngineRepository.findAll();
        assertThat(courierPricingEngineList).hasSize(databaseSizeBeforeCreate + 1);
        CourierPricingEngine testCourierPricingEngine = courierPricingEngineList.get(courierPricingEngineList.size() - 1);
        assertThat(testCourierPricingEngine.getFirstBaseWt()).isEqualTo(DEFAULT_FIRST_BASE_WT);
        assertThat(testCourierPricingEngine.getFirstBaseCost()).isEqualTo(DEFAULT_FIRST_BASE_COST);
        assertThat(testCourierPricingEngine.getSecondBaseWt()).isEqualTo(DEFAULT_SECOND_BASE_WT);
        assertThat(testCourierPricingEngine.getSecondBaseCost()).isEqualTo(DEFAULT_SECOND_BASE_COST);
        assertThat(testCourierPricingEngine.getAdditionalWt()).isEqualTo(DEFAULT_ADDITIONAL_WT);
        assertThat(testCourierPricingEngine.getAdditionalCost()).isEqualTo(DEFAULT_ADDITIONAL_COST);
        assertThat(testCourierPricingEngine.getFuelSurcharge()).isEqualTo(DEFAULT_FUEL_SURCHARGE);
        assertThat(testCourierPricingEngine.getMinCodCharges()).isEqualTo(DEFAULT_MIN_COD_CHARGES);
        assertThat(testCourierPricingEngine.getCodCutoffAmount()).isEqualTo(DEFAULT_COD_CUTOFF_AMOUNT);
        assertThat(testCourierPricingEngine.getVariableCodCharges()).isEqualTo(DEFAULT_VARIABLE_COD_CHARGES);
        assertThat(testCourierPricingEngine.getValidUpto()).isEqualTo(DEFAULT_VALID_UPTO);
        assertThat(testCourierPricingEngine.getCostParameters()).isEqualTo(DEFAULT_COST_PARAMETERS);

        // Validate the CourierPricingEngine in Elasticsearch
        verify(mockCourierPricingEngineSearchRepository, times(1)).save(testCourierPricingEngine);
    }

    @Test
    @Transactional
    public void createCourierPricingEngineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courierPricingEngineRepository.findAll().size();

        // Create the CourierPricingEngine with an existing ID
        courierPricingEngine.setId(1L);
        CourierPricingEngineDTO courierPricingEngineDTO = courierPricingEngineMapper.toDto(courierPricingEngine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourierPricingEngineMockMvc.perform(post("/api/courier-pricing-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierPricingEngineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourierPricingEngine in the database
        List<CourierPricingEngine> courierPricingEngineList = courierPricingEngineRepository.findAll();
        assertThat(courierPricingEngineList).hasSize(databaseSizeBeforeCreate);

        // Validate the CourierPricingEngine in Elasticsearch
        verify(mockCourierPricingEngineSearchRepository, times(0)).save(courierPricingEngine);
    }

    @Test
    @Transactional
    public void checkFirstBaseWtIsRequired() throws Exception {
        int databaseSizeBeforeTest = courierPricingEngineRepository.findAll().size();
        // set the field null
        courierPricingEngine.setFirstBaseWt(null);

        // Create the CourierPricingEngine, which fails.
        CourierPricingEngineDTO courierPricingEngineDTO = courierPricingEngineMapper.toDto(courierPricingEngine);

        restCourierPricingEngineMockMvc.perform(post("/api/courier-pricing-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierPricingEngineDTO)))
            .andExpect(status().isBadRequest());

        List<CourierPricingEngine> courierPricingEngineList = courierPricingEngineRepository.findAll();
        assertThat(courierPricingEngineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstBaseCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = courierPricingEngineRepository.findAll().size();
        // set the field null
        courierPricingEngine.setFirstBaseCost(null);

        // Create the CourierPricingEngine, which fails.
        CourierPricingEngineDTO courierPricingEngineDTO = courierPricingEngineMapper.toDto(courierPricingEngine);

        restCourierPricingEngineMockMvc.perform(post("/api/courier-pricing-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierPricingEngineDTO)))
            .andExpect(status().isBadRequest());

        List<CourierPricingEngine> courierPricingEngineList = courierPricingEngineRepository.findAll();
        assertThat(courierPricingEngineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdditionalWtIsRequired() throws Exception {
        int databaseSizeBeforeTest = courierPricingEngineRepository.findAll().size();
        // set the field null
        courierPricingEngine.setAdditionalWt(null);

        // Create the CourierPricingEngine, which fails.
        CourierPricingEngineDTO courierPricingEngineDTO = courierPricingEngineMapper.toDto(courierPricingEngine);

        restCourierPricingEngineMockMvc.perform(post("/api/courier-pricing-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierPricingEngineDTO)))
            .andExpect(status().isBadRequest());

        List<CourierPricingEngine> courierPricingEngineList = courierPricingEngineRepository.findAll();
        assertThat(courierPricingEngineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdditionalCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = courierPricingEngineRepository.findAll().size();
        // set the field null
        courierPricingEngine.setAdditionalCost(null);

        // Create the CourierPricingEngine, which fails.
        CourierPricingEngineDTO courierPricingEngineDTO = courierPricingEngineMapper.toDto(courierPricingEngine);

        restCourierPricingEngineMockMvc.perform(post("/api/courier-pricing-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierPricingEngineDTO)))
            .andExpect(status().isBadRequest());

        List<CourierPricingEngine> courierPricingEngineList = courierPricingEngineRepository.findAll();
        assertThat(courierPricingEngineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEngines() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList
        restCourierPricingEngineMockMvc.perform(get("/api/courier-pricing-engines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courierPricingEngine.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstBaseWt").value(hasItem(DEFAULT_FIRST_BASE_WT.doubleValue())))
            .andExpect(jsonPath("$.[*].firstBaseCost").value(hasItem(DEFAULT_FIRST_BASE_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].secondBaseWt").value(hasItem(DEFAULT_SECOND_BASE_WT.doubleValue())))
            .andExpect(jsonPath("$.[*].secondBaseCost").value(hasItem(DEFAULT_SECOND_BASE_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalWt").value(hasItem(DEFAULT_ADDITIONAL_WT.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalCost").value(hasItem(DEFAULT_ADDITIONAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].fuelSurcharge").value(hasItem(DEFAULT_FUEL_SURCHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].minCodCharges").value(hasItem(DEFAULT_MIN_COD_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].codCutoffAmount").value(hasItem(DEFAULT_COD_CUTOFF_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].variableCodCharges").value(hasItem(DEFAULT_VARIABLE_COD_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].validUpto").value(hasItem(DEFAULT_VALID_UPTO.toString())))
            .andExpect(jsonPath("$.[*].costParameters").value(hasItem(DEFAULT_COST_PARAMETERS.toString())));
    }
    

    @Test
    @Transactional
    public void getCourierPricingEngine() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get the courierPricingEngine
        restCourierPricingEngineMockMvc.perform(get("/api/courier-pricing-engines/{id}", courierPricingEngine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courierPricingEngine.getId().intValue()))
            .andExpect(jsonPath("$.firstBaseWt").value(DEFAULT_FIRST_BASE_WT.doubleValue()))
            .andExpect(jsonPath("$.firstBaseCost").value(DEFAULT_FIRST_BASE_COST.doubleValue()))
            .andExpect(jsonPath("$.secondBaseWt").value(DEFAULT_SECOND_BASE_WT.doubleValue()))
            .andExpect(jsonPath("$.secondBaseCost").value(DEFAULT_SECOND_BASE_COST.doubleValue()))
            .andExpect(jsonPath("$.additionalWt").value(DEFAULT_ADDITIONAL_WT.doubleValue()))
            .andExpect(jsonPath("$.additionalCost").value(DEFAULT_ADDITIONAL_COST.doubleValue()))
            .andExpect(jsonPath("$.fuelSurcharge").value(DEFAULT_FUEL_SURCHARGE.doubleValue()))
            .andExpect(jsonPath("$.minCodCharges").value(DEFAULT_MIN_COD_CHARGES.doubleValue()))
            .andExpect(jsonPath("$.codCutoffAmount").value(DEFAULT_COD_CUTOFF_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.variableCodCharges").value(DEFAULT_VARIABLE_COD_CHARGES.doubleValue()))
            .andExpect(jsonPath("$.validUpto").value(DEFAULT_VALID_UPTO.toString()))
            .andExpect(jsonPath("$.costParameters").value(DEFAULT_COST_PARAMETERS.toString()));
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByFirstBaseWtIsEqualToSomething() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where firstBaseWt equals to DEFAULT_FIRST_BASE_WT
        defaultCourierPricingEngineShouldBeFound("firstBaseWt.equals=" + DEFAULT_FIRST_BASE_WT);

        // Get all the courierPricingEngineList where firstBaseWt equals to UPDATED_FIRST_BASE_WT
        defaultCourierPricingEngineShouldNotBeFound("firstBaseWt.equals=" + UPDATED_FIRST_BASE_WT);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByFirstBaseWtIsInShouldWork() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where firstBaseWt in DEFAULT_FIRST_BASE_WT or UPDATED_FIRST_BASE_WT
        defaultCourierPricingEngineShouldBeFound("firstBaseWt.in=" + DEFAULT_FIRST_BASE_WT + "," + UPDATED_FIRST_BASE_WT);

        // Get all the courierPricingEngineList where firstBaseWt equals to UPDATED_FIRST_BASE_WT
        defaultCourierPricingEngineShouldNotBeFound("firstBaseWt.in=" + UPDATED_FIRST_BASE_WT);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByFirstBaseWtIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where firstBaseWt is not null
        defaultCourierPricingEngineShouldBeFound("firstBaseWt.specified=true");

        // Get all the courierPricingEngineList where firstBaseWt is null
        defaultCourierPricingEngineShouldNotBeFound("firstBaseWt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByFirstBaseCostIsEqualToSomething() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where firstBaseCost equals to DEFAULT_FIRST_BASE_COST
        defaultCourierPricingEngineShouldBeFound("firstBaseCost.equals=" + DEFAULT_FIRST_BASE_COST);

        // Get all the courierPricingEngineList where firstBaseCost equals to UPDATED_FIRST_BASE_COST
        defaultCourierPricingEngineShouldNotBeFound("firstBaseCost.equals=" + UPDATED_FIRST_BASE_COST);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByFirstBaseCostIsInShouldWork() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where firstBaseCost in DEFAULT_FIRST_BASE_COST or UPDATED_FIRST_BASE_COST
        defaultCourierPricingEngineShouldBeFound("firstBaseCost.in=" + DEFAULT_FIRST_BASE_COST + "," + UPDATED_FIRST_BASE_COST);

        // Get all the courierPricingEngineList where firstBaseCost equals to UPDATED_FIRST_BASE_COST
        defaultCourierPricingEngineShouldNotBeFound("firstBaseCost.in=" + UPDATED_FIRST_BASE_COST);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByFirstBaseCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where firstBaseCost is not null
        defaultCourierPricingEngineShouldBeFound("firstBaseCost.specified=true");

        // Get all the courierPricingEngineList where firstBaseCost is null
        defaultCourierPricingEngineShouldNotBeFound("firstBaseCost.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesBySecondBaseWtIsEqualToSomething() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where secondBaseWt equals to DEFAULT_SECOND_BASE_WT
        defaultCourierPricingEngineShouldBeFound("secondBaseWt.equals=" + DEFAULT_SECOND_BASE_WT);

        // Get all the courierPricingEngineList where secondBaseWt equals to UPDATED_SECOND_BASE_WT
        defaultCourierPricingEngineShouldNotBeFound("secondBaseWt.equals=" + UPDATED_SECOND_BASE_WT);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesBySecondBaseWtIsInShouldWork() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where secondBaseWt in DEFAULT_SECOND_BASE_WT or UPDATED_SECOND_BASE_WT
        defaultCourierPricingEngineShouldBeFound("secondBaseWt.in=" + DEFAULT_SECOND_BASE_WT + "," + UPDATED_SECOND_BASE_WT);

        // Get all the courierPricingEngineList where secondBaseWt equals to UPDATED_SECOND_BASE_WT
        defaultCourierPricingEngineShouldNotBeFound("secondBaseWt.in=" + UPDATED_SECOND_BASE_WT);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesBySecondBaseWtIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where secondBaseWt is not null
        defaultCourierPricingEngineShouldBeFound("secondBaseWt.specified=true");

        // Get all the courierPricingEngineList where secondBaseWt is null
        defaultCourierPricingEngineShouldNotBeFound("secondBaseWt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesBySecondBaseCostIsEqualToSomething() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where secondBaseCost equals to DEFAULT_SECOND_BASE_COST
        defaultCourierPricingEngineShouldBeFound("secondBaseCost.equals=" + DEFAULT_SECOND_BASE_COST);

        // Get all the courierPricingEngineList where secondBaseCost equals to UPDATED_SECOND_BASE_COST
        defaultCourierPricingEngineShouldNotBeFound("secondBaseCost.equals=" + UPDATED_SECOND_BASE_COST);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesBySecondBaseCostIsInShouldWork() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where secondBaseCost in DEFAULT_SECOND_BASE_COST or UPDATED_SECOND_BASE_COST
        defaultCourierPricingEngineShouldBeFound("secondBaseCost.in=" + DEFAULT_SECOND_BASE_COST + "," + UPDATED_SECOND_BASE_COST);

        // Get all the courierPricingEngineList where secondBaseCost equals to UPDATED_SECOND_BASE_COST
        defaultCourierPricingEngineShouldNotBeFound("secondBaseCost.in=" + UPDATED_SECOND_BASE_COST);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesBySecondBaseCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where secondBaseCost is not null
        defaultCourierPricingEngineShouldBeFound("secondBaseCost.specified=true");

        // Get all the courierPricingEngineList where secondBaseCost is null
        defaultCourierPricingEngineShouldNotBeFound("secondBaseCost.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByAdditionalWtIsEqualToSomething() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where additionalWt equals to DEFAULT_ADDITIONAL_WT
        defaultCourierPricingEngineShouldBeFound("additionalWt.equals=" + DEFAULT_ADDITIONAL_WT);

        // Get all the courierPricingEngineList where additionalWt equals to UPDATED_ADDITIONAL_WT
        defaultCourierPricingEngineShouldNotBeFound("additionalWt.equals=" + UPDATED_ADDITIONAL_WT);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByAdditionalWtIsInShouldWork() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where additionalWt in DEFAULT_ADDITIONAL_WT or UPDATED_ADDITIONAL_WT
        defaultCourierPricingEngineShouldBeFound("additionalWt.in=" + DEFAULT_ADDITIONAL_WT + "," + UPDATED_ADDITIONAL_WT);

        // Get all the courierPricingEngineList where additionalWt equals to UPDATED_ADDITIONAL_WT
        defaultCourierPricingEngineShouldNotBeFound("additionalWt.in=" + UPDATED_ADDITIONAL_WT);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByAdditionalWtIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where additionalWt is not null
        defaultCourierPricingEngineShouldBeFound("additionalWt.specified=true");

        // Get all the courierPricingEngineList where additionalWt is null
        defaultCourierPricingEngineShouldNotBeFound("additionalWt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByAdditionalCostIsEqualToSomething() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where additionalCost equals to DEFAULT_ADDITIONAL_COST
        defaultCourierPricingEngineShouldBeFound("additionalCost.equals=" + DEFAULT_ADDITIONAL_COST);

        // Get all the courierPricingEngineList where additionalCost equals to UPDATED_ADDITIONAL_COST
        defaultCourierPricingEngineShouldNotBeFound("additionalCost.equals=" + UPDATED_ADDITIONAL_COST);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByAdditionalCostIsInShouldWork() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where additionalCost in DEFAULT_ADDITIONAL_COST or UPDATED_ADDITIONAL_COST
        defaultCourierPricingEngineShouldBeFound("additionalCost.in=" + DEFAULT_ADDITIONAL_COST + "," + UPDATED_ADDITIONAL_COST);

        // Get all the courierPricingEngineList where additionalCost equals to UPDATED_ADDITIONAL_COST
        defaultCourierPricingEngineShouldNotBeFound("additionalCost.in=" + UPDATED_ADDITIONAL_COST);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByAdditionalCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where additionalCost is not null
        defaultCourierPricingEngineShouldBeFound("additionalCost.specified=true");

        // Get all the courierPricingEngineList where additionalCost is null
        defaultCourierPricingEngineShouldNotBeFound("additionalCost.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByFuelSurchargeIsEqualToSomething() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where fuelSurcharge equals to DEFAULT_FUEL_SURCHARGE
        defaultCourierPricingEngineShouldBeFound("fuelSurcharge.equals=" + DEFAULT_FUEL_SURCHARGE);

        // Get all the courierPricingEngineList where fuelSurcharge equals to UPDATED_FUEL_SURCHARGE
        defaultCourierPricingEngineShouldNotBeFound("fuelSurcharge.equals=" + UPDATED_FUEL_SURCHARGE);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByFuelSurchargeIsInShouldWork() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where fuelSurcharge in DEFAULT_FUEL_SURCHARGE or UPDATED_FUEL_SURCHARGE
        defaultCourierPricingEngineShouldBeFound("fuelSurcharge.in=" + DEFAULT_FUEL_SURCHARGE + "," + UPDATED_FUEL_SURCHARGE);

        // Get all the courierPricingEngineList where fuelSurcharge equals to UPDATED_FUEL_SURCHARGE
        defaultCourierPricingEngineShouldNotBeFound("fuelSurcharge.in=" + UPDATED_FUEL_SURCHARGE);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByFuelSurchargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where fuelSurcharge is not null
        defaultCourierPricingEngineShouldBeFound("fuelSurcharge.specified=true");

        // Get all the courierPricingEngineList where fuelSurcharge is null
        defaultCourierPricingEngineShouldNotBeFound("fuelSurcharge.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByMinCodChargesIsEqualToSomething() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where minCodCharges equals to DEFAULT_MIN_COD_CHARGES
        defaultCourierPricingEngineShouldBeFound("minCodCharges.equals=" + DEFAULT_MIN_COD_CHARGES);

        // Get all the courierPricingEngineList where minCodCharges equals to UPDATED_MIN_COD_CHARGES
        defaultCourierPricingEngineShouldNotBeFound("minCodCharges.equals=" + UPDATED_MIN_COD_CHARGES);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByMinCodChargesIsInShouldWork() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where minCodCharges in DEFAULT_MIN_COD_CHARGES or UPDATED_MIN_COD_CHARGES
        defaultCourierPricingEngineShouldBeFound("minCodCharges.in=" + DEFAULT_MIN_COD_CHARGES + "," + UPDATED_MIN_COD_CHARGES);

        // Get all the courierPricingEngineList where minCodCharges equals to UPDATED_MIN_COD_CHARGES
        defaultCourierPricingEngineShouldNotBeFound("minCodCharges.in=" + UPDATED_MIN_COD_CHARGES);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByMinCodChargesIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where minCodCharges is not null
        defaultCourierPricingEngineShouldBeFound("minCodCharges.specified=true");

        // Get all the courierPricingEngineList where minCodCharges is null
        defaultCourierPricingEngineShouldNotBeFound("minCodCharges.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByCodCutoffAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where codCutoffAmount equals to DEFAULT_COD_CUTOFF_AMOUNT
        defaultCourierPricingEngineShouldBeFound("codCutoffAmount.equals=" + DEFAULT_COD_CUTOFF_AMOUNT);

        // Get all the courierPricingEngineList where codCutoffAmount equals to UPDATED_COD_CUTOFF_AMOUNT
        defaultCourierPricingEngineShouldNotBeFound("codCutoffAmount.equals=" + UPDATED_COD_CUTOFF_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByCodCutoffAmountIsInShouldWork() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where codCutoffAmount in DEFAULT_COD_CUTOFF_AMOUNT or UPDATED_COD_CUTOFF_AMOUNT
        defaultCourierPricingEngineShouldBeFound("codCutoffAmount.in=" + DEFAULT_COD_CUTOFF_AMOUNT + "," + UPDATED_COD_CUTOFF_AMOUNT);

        // Get all the courierPricingEngineList where codCutoffAmount equals to UPDATED_COD_CUTOFF_AMOUNT
        defaultCourierPricingEngineShouldNotBeFound("codCutoffAmount.in=" + UPDATED_COD_CUTOFF_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByCodCutoffAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where codCutoffAmount is not null
        defaultCourierPricingEngineShouldBeFound("codCutoffAmount.specified=true");

        // Get all the courierPricingEngineList where codCutoffAmount is null
        defaultCourierPricingEngineShouldNotBeFound("codCutoffAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByVariableCodChargesIsEqualToSomething() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where variableCodCharges equals to DEFAULT_VARIABLE_COD_CHARGES
        defaultCourierPricingEngineShouldBeFound("variableCodCharges.equals=" + DEFAULT_VARIABLE_COD_CHARGES);

        // Get all the courierPricingEngineList where variableCodCharges equals to UPDATED_VARIABLE_COD_CHARGES
        defaultCourierPricingEngineShouldNotBeFound("variableCodCharges.equals=" + UPDATED_VARIABLE_COD_CHARGES);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByVariableCodChargesIsInShouldWork() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where variableCodCharges in DEFAULT_VARIABLE_COD_CHARGES or UPDATED_VARIABLE_COD_CHARGES
        defaultCourierPricingEngineShouldBeFound("variableCodCharges.in=" + DEFAULT_VARIABLE_COD_CHARGES + "," + UPDATED_VARIABLE_COD_CHARGES);

        // Get all the courierPricingEngineList where variableCodCharges equals to UPDATED_VARIABLE_COD_CHARGES
        defaultCourierPricingEngineShouldNotBeFound("variableCodCharges.in=" + UPDATED_VARIABLE_COD_CHARGES);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByVariableCodChargesIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where variableCodCharges is not null
        defaultCourierPricingEngineShouldBeFound("variableCodCharges.specified=true");

        // Get all the courierPricingEngineList where variableCodCharges is null
        defaultCourierPricingEngineShouldNotBeFound("variableCodCharges.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByValidUptoIsEqualToSomething() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where validUpto equals to DEFAULT_VALID_UPTO
        defaultCourierPricingEngineShouldBeFound("validUpto.equals=" + DEFAULT_VALID_UPTO);

        // Get all the courierPricingEngineList where validUpto equals to UPDATED_VALID_UPTO
        defaultCourierPricingEngineShouldNotBeFound("validUpto.equals=" + UPDATED_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByValidUptoIsInShouldWork() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where validUpto in DEFAULT_VALID_UPTO or UPDATED_VALID_UPTO
        defaultCourierPricingEngineShouldBeFound("validUpto.in=" + DEFAULT_VALID_UPTO + "," + UPDATED_VALID_UPTO);

        // Get all the courierPricingEngineList where validUpto equals to UPDATED_VALID_UPTO
        defaultCourierPricingEngineShouldNotBeFound("validUpto.in=" + UPDATED_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByValidUptoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where validUpto is not null
        defaultCourierPricingEngineShouldBeFound("validUpto.specified=true");

        // Get all the courierPricingEngineList where validUpto is null
        defaultCourierPricingEngineShouldNotBeFound("validUpto.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByValidUptoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where validUpto greater than or equals to DEFAULT_VALID_UPTO
        defaultCourierPricingEngineShouldBeFound("validUpto.greaterOrEqualThan=" + DEFAULT_VALID_UPTO);

        // Get all the courierPricingEngineList where validUpto greater than or equals to UPDATED_VALID_UPTO
        defaultCourierPricingEngineShouldNotBeFound("validUpto.greaterOrEqualThan=" + UPDATED_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByValidUptoIsLessThanSomething() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where validUpto less than or equals to DEFAULT_VALID_UPTO
        defaultCourierPricingEngineShouldNotBeFound("validUpto.lessThan=" + DEFAULT_VALID_UPTO);

        // Get all the courierPricingEngineList where validUpto less than or equals to UPDATED_VALID_UPTO
        defaultCourierPricingEngineShouldBeFound("validUpto.lessThan=" + UPDATED_VALID_UPTO);
    }


    @Test
    @Transactional
    public void getAllCourierPricingEnginesByCostParametersIsEqualToSomething() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where costParameters equals to DEFAULT_COST_PARAMETERS
        defaultCourierPricingEngineShouldBeFound("costParameters.equals=" + DEFAULT_COST_PARAMETERS);

        // Get all the courierPricingEngineList where costParameters equals to UPDATED_COST_PARAMETERS
        defaultCourierPricingEngineShouldNotBeFound("costParameters.equals=" + UPDATED_COST_PARAMETERS);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByCostParametersIsInShouldWork() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where costParameters in DEFAULT_COST_PARAMETERS or UPDATED_COST_PARAMETERS
        defaultCourierPricingEngineShouldBeFound("costParameters.in=" + DEFAULT_COST_PARAMETERS + "," + UPDATED_COST_PARAMETERS);

        // Get all the courierPricingEngineList where costParameters equals to UPDATED_COST_PARAMETERS
        defaultCourierPricingEngineShouldNotBeFound("costParameters.in=" + UPDATED_COST_PARAMETERS);
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByCostParametersIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        // Get all the courierPricingEngineList where costParameters is not null
        defaultCourierPricingEngineShouldBeFound("costParameters.specified=true");

        // Get all the courierPricingEngineList where costParameters is null
        defaultCourierPricingEngineShouldNotBeFound("costParameters.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourierPricingEnginesByCourierIsEqualToSomething() throws Exception {
        // Initialize the database
        Courier courier = CourierResourceIntTest.createEntity(em);
        em.persist(courier);
        em.flush();
        courierPricingEngine.setCourier(courier);
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);
        Long courierId = courier.getId();

        // Get all the courierPricingEngineList where courier equals to courierId
        defaultCourierPricingEngineShouldBeFound("courierId.equals=" + courierId);

        // Get all the courierPricingEngineList where courier equals to courierId + 1
        defaultCourierPricingEngineShouldNotBeFound("courierId.equals=" + (courierId + 1));
    }


    @Test
    @Transactional
    public void getAllCourierPricingEnginesByRegionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        RegionType regionType = RegionTypeResourceIntTest.createEntity(em);
        em.persist(regionType);
        em.flush();
        courierPricingEngine.setRegionType(regionType);
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);
        Long regionTypeId = regionType.getId();

        // Get all the courierPricingEngineList where regionType equals to regionTypeId
        defaultCourierPricingEngineShouldBeFound("regionTypeId.equals=" + regionTypeId);

        // Get all the courierPricingEngineList where regionType equals to regionTypeId + 1
        defaultCourierPricingEngineShouldNotBeFound("regionTypeId.equals=" + (regionTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCourierPricingEngineShouldBeFound(String filter) throws Exception {
        restCourierPricingEngineMockMvc.perform(get("/api/courier-pricing-engines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courierPricingEngine.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstBaseWt").value(hasItem(DEFAULT_FIRST_BASE_WT.doubleValue())))
            .andExpect(jsonPath("$.[*].firstBaseCost").value(hasItem(DEFAULT_FIRST_BASE_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].secondBaseWt").value(hasItem(DEFAULT_SECOND_BASE_WT.doubleValue())))
            .andExpect(jsonPath("$.[*].secondBaseCost").value(hasItem(DEFAULT_SECOND_BASE_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalWt").value(hasItem(DEFAULT_ADDITIONAL_WT.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalCost").value(hasItem(DEFAULT_ADDITIONAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].fuelSurcharge").value(hasItem(DEFAULT_FUEL_SURCHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].minCodCharges").value(hasItem(DEFAULT_MIN_COD_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].codCutoffAmount").value(hasItem(DEFAULT_COD_CUTOFF_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].variableCodCharges").value(hasItem(DEFAULT_VARIABLE_COD_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].validUpto").value(hasItem(DEFAULT_VALID_UPTO.toString())))
            .andExpect(jsonPath("$.[*].costParameters").value(hasItem(DEFAULT_COST_PARAMETERS.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCourierPricingEngineShouldNotBeFound(String filter) throws Exception {
        restCourierPricingEngineMockMvc.perform(get("/api/courier-pricing-engines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingCourierPricingEngine() throws Exception {
        // Get the courierPricingEngine
        restCourierPricingEngineMockMvc.perform(get("/api/courier-pricing-engines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourierPricingEngine() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        int databaseSizeBeforeUpdate = courierPricingEngineRepository.findAll().size();

        // Update the courierPricingEngine
        CourierPricingEngine updatedCourierPricingEngine = courierPricingEngineRepository.findById(courierPricingEngine.getId()).get();
        // Disconnect from session so that the updates on updatedCourierPricingEngine are not directly saved in db
        em.detach(updatedCourierPricingEngine);
        updatedCourierPricingEngine
            .firstBaseWt(UPDATED_FIRST_BASE_WT)
            .firstBaseCost(UPDATED_FIRST_BASE_COST)
            .secondBaseWt(UPDATED_SECOND_BASE_WT)
            .secondBaseCost(UPDATED_SECOND_BASE_COST)
            .additionalWt(UPDATED_ADDITIONAL_WT)
            .additionalCost(UPDATED_ADDITIONAL_COST)
            .fuelSurcharge(UPDATED_FUEL_SURCHARGE)
            .minCodCharges(UPDATED_MIN_COD_CHARGES)
            .codCutoffAmount(UPDATED_COD_CUTOFF_AMOUNT)
            .variableCodCharges(UPDATED_VARIABLE_COD_CHARGES)
            .validUpto(UPDATED_VALID_UPTO)
            .costParameters(UPDATED_COST_PARAMETERS);
        CourierPricingEngineDTO courierPricingEngineDTO = courierPricingEngineMapper.toDto(updatedCourierPricingEngine);

        restCourierPricingEngineMockMvc.perform(put("/api/courier-pricing-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierPricingEngineDTO)))
            .andExpect(status().isOk());

        // Validate the CourierPricingEngine in the database
        List<CourierPricingEngine> courierPricingEngineList = courierPricingEngineRepository.findAll();
        assertThat(courierPricingEngineList).hasSize(databaseSizeBeforeUpdate);
        CourierPricingEngine testCourierPricingEngine = courierPricingEngineList.get(courierPricingEngineList.size() - 1);
        assertThat(testCourierPricingEngine.getFirstBaseWt()).isEqualTo(UPDATED_FIRST_BASE_WT);
        assertThat(testCourierPricingEngine.getFirstBaseCost()).isEqualTo(UPDATED_FIRST_BASE_COST);
        assertThat(testCourierPricingEngine.getSecondBaseWt()).isEqualTo(UPDATED_SECOND_BASE_WT);
        assertThat(testCourierPricingEngine.getSecondBaseCost()).isEqualTo(UPDATED_SECOND_BASE_COST);
        assertThat(testCourierPricingEngine.getAdditionalWt()).isEqualTo(UPDATED_ADDITIONAL_WT);
        assertThat(testCourierPricingEngine.getAdditionalCost()).isEqualTo(UPDATED_ADDITIONAL_COST);
        assertThat(testCourierPricingEngine.getFuelSurcharge()).isEqualTo(UPDATED_FUEL_SURCHARGE);
        assertThat(testCourierPricingEngine.getMinCodCharges()).isEqualTo(UPDATED_MIN_COD_CHARGES);
        assertThat(testCourierPricingEngine.getCodCutoffAmount()).isEqualTo(UPDATED_COD_CUTOFF_AMOUNT);
        assertThat(testCourierPricingEngine.getVariableCodCharges()).isEqualTo(UPDATED_VARIABLE_COD_CHARGES);
        assertThat(testCourierPricingEngine.getValidUpto()).isEqualTo(UPDATED_VALID_UPTO);
        assertThat(testCourierPricingEngine.getCostParameters()).isEqualTo(UPDATED_COST_PARAMETERS);

        // Validate the CourierPricingEngine in Elasticsearch
        verify(mockCourierPricingEngineSearchRepository, times(1)).save(testCourierPricingEngine);
    }

    @Test
    @Transactional
    public void updateNonExistingCourierPricingEngine() throws Exception {
        int databaseSizeBeforeUpdate = courierPricingEngineRepository.findAll().size();

        // Create the CourierPricingEngine
        CourierPricingEngineDTO courierPricingEngineDTO = courierPricingEngineMapper.toDto(courierPricingEngine);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCourierPricingEngineMockMvc.perform(put("/api/courier-pricing-engines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierPricingEngineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourierPricingEngine in the database
        List<CourierPricingEngine> courierPricingEngineList = courierPricingEngineRepository.findAll();
        assertThat(courierPricingEngineList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CourierPricingEngine in Elasticsearch
        verify(mockCourierPricingEngineSearchRepository, times(0)).save(courierPricingEngine);
    }

    @Test
    @Transactional
    public void deleteCourierPricingEngine() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);

        int databaseSizeBeforeDelete = courierPricingEngineRepository.findAll().size();

        // Get the courierPricingEngine
        restCourierPricingEngineMockMvc.perform(delete("/api/courier-pricing-engines/{id}", courierPricingEngine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CourierPricingEngine> courierPricingEngineList = courierPricingEngineRepository.findAll();
        assertThat(courierPricingEngineList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CourierPricingEngine in Elasticsearch
        verify(mockCourierPricingEngineSearchRepository, times(1)).deleteById(courierPricingEngine.getId());
    }

    @Test
    @Transactional
    public void searchCourierPricingEngine() throws Exception {
        // Initialize the database
        courierPricingEngineRepository.saveAndFlush(courierPricingEngine);
        when(mockCourierPricingEngineSearchRepository.search(queryStringQuery("id:" + courierPricingEngine.getId())))
            .thenReturn(Collections.singletonList(courierPricingEngine));
        // Search the courierPricingEngine
        restCourierPricingEngineMockMvc.perform(get("/api/_search/courier-pricing-engines?query=id:" + courierPricingEngine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courierPricingEngine.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstBaseWt").value(hasItem(DEFAULT_FIRST_BASE_WT.doubleValue())))
            .andExpect(jsonPath("$.[*].firstBaseCost").value(hasItem(DEFAULT_FIRST_BASE_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].secondBaseWt").value(hasItem(DEFAULT_SECOND_BASE_WT.doubleValue())))
            .andExpect(jsonPath("$.[*].secondBaseCost").value(hasItem(DEFAULT_SECOND_BASE_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalWt").value(hasItem(DEFAULT_ADDITIONAL_WT.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalCost").value(hasItem(DEFAULT_ADDITIONAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].fuelSurcharge").value(hasItem(DEFAULT_FUEL_SURCHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].minCodCharges").value(hasItem(DEFAULT_MIN_COD_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].codCutoffAmount").value(hasItem(DEFAULT_COD_CUTOFF_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].variableCodCharges").value(hasItem(DEFAULT_VARIABLE_COD_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].validUpto").value(hasItem(DEFAULT_VALID_UPTO.toString())))
            .andExpect(jsonPath("$.[*].costParameters").value(hasItem(DEFAULT_COST_PARAMETERS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourierPricingEngine.class);
        CourierPricingEngine courierPricingEngine1 = new CourierPricingEngine();
        courierPricingEngine1.setId(1L);
        CourierPricingEngine courierPricingEngine2 = new CourierPricingEngine();
        courierPricingEngine2.setId(courierPricingEngine1.getId());
        assertThat(courierPricingEngine1).isEqualTo(courierPricingEngine2);
        courierPricingEngine2.setId(2L);
        assertThat(courierPricingEngine1).isNotEqualTo(courierPricingEngine2);
        courierPricingEngine1.setId(null);
        assertThat(courierPricingEngine1).isNotEqualTo(courierPricingEngine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourierPricingEngineDTO.class);
        CourierPricingEngineDTO courierPricingEngineDTO1 = new CourierPricingEngineDTO();
        courierPricingEngineDTO1.setId(1L);
        CourierPricingEngineDTO courierPricingEngineDTO2 = new CourierPricingEngineDTO();
        assertThat(courierPricingEngineDTO1).isNotEqualTo(courierPricingEngineDTO2);
        courierPricingEngineDTO2.setId(courierPricingEngineDTO1.getId());
        assertThat(courierPricingEngineDTO1).isEqualTo(courierPricingEngineDTO2);
        courierPricingEngineDTO2.setId(2L);
        assertThat(courierPricingEngineDTO1).isNotEqualTo(courierPricingEngineDTO2);
        courierPricingEngineDTO1.setId(null);
        assertThat(courierPricingEngineDTO1).isNotEqualTo(courierPricingEngineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(courierPricingEngineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(courierPricingEngineMapper.fromId(null)).isNull();
    }
}
