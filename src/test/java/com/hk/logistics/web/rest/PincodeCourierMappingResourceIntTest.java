package com.hk.logistics.web.rest;

import com.hk.logistics.HkLogisticsApp;

import com.hk.logistics.domain.PincodeCourierMapping;
import com.hk.logistics.domain.Pincode;
import com.hk.logistics.domain.VendorWHCourierMapping;
import com.hk.logistics.domain.SourceDestinationMapping;
import com.hk.logistics.repository.PincodeCourierMappingRepository;
import com.hk.logistics.repository.search.PincodeCourierMappingSearchRepository;
import com.hk.logistics.service.PincodeCourierMappingService;
import com.hk.logistics.service.dto.PincodeCourierMappingDTO;
import com.hk.logistics.service.mapper.PincodeCourierMappingMapper;
import com.hk.logistics.web.rest.errors.ExceptionTranslator;
import com.hk.logistics.service.dto.PincodeCourierMappingCriteria;
import com.hk.logistics.service.PincodeCourierMappingQueryService;

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
 * Test class for the PincodeCourierMappingResource REST controller.
 *
 * @see PincodeCourierMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HkLogisticsApp.class)
public class PincodeCourierMappingResourceIntTest {

    private static final String DEFAULT_ROUTING_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ROUTING_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPLICABLE_FOR_CHEAPEST_COURIER = false;
    private static final Boolean UPDATED_APPLICABLE_FOR_CHEAPEST_COURIER = true;

    private static final Double DEFAULT_ESTIMATED_DELIVERY_DAYS = 1D;
    private static final Double UPDATED_ESTIMATED_DELIVERY_DAYS = 2D;

    private static final Boolean DEFAULT_PICKUP_AVAILABLE = false;
    private static final Boolean UPDATED_PICKUP_AVAILABLE = true;

    private static final Boolean DEFAULT_PREPAID_AIR = false;
    private static final Boolean UPDATED_PREPAID_AIR = true;

    private static final Boolean DEFAULT_PREPAID_GROUND = false;
    private static final Boolean UPDATED_PREPAID_GROUND = true;

    private static final Boolean DEFAULT_COD_AIR = false;
    private static final Boolean UPDATED_COD_AIR = true;

    private static final Boolean DEFAULT_COD_GROUND = false;
    private static final Boolean UPDATED_COD_GROUND = true;

    private static final Boolean DEFAULT_REVERSE_AIR = false;
    private static final Boolean UPDATED_REVERSE_AIR = true;

    private static final Boolean DEFAULT_REVERSE_GROUND = false;
    private static final Boolean UPDATED_REVERSE_GROUND = true;

    private static final Boolean DEFAULT_CARD_ON_DELIVERY_AIR = false;
    private static final Boolean UPDATED_CARD_ON_DELIVERY_AIR = true;

    private static final Boolean DEFAULT_CARD_ON_DELIVERY_GROUND = false;
    private static final Boolean UPDATED_CARD_ON_DELIVERY_GROUND = true;

    private static final Boolean DEFAULT_DELIVERY_TYPE_ONE = false;
    private static final Boolean UPDATED_DELIVERY_TYPE_ONE = true;

    private static final Boolean DEFAULT_DELIVERY_TYPE_TWO = false;
    private static final Boolean UPDATED_DELIVERY_TYPE_TWO = true;

    @Autowired
    private PincodeCourierMappingRepository pincodeCourierMappingRepository;


    @Autowired
    private PincodeCourierMappingMapper pincodeCourierMappingMapper;
    

    @Autowired
    private PincodeCourierMappingService pincodeCourierMappingService;

    /**
     * This repository is mocked in the com.hk.logistics.repository.search test package.
     *
     * @see com.hk.logistics.repository.search.PincodeCourierMappingSearchRepositoryMockConfiguration
     */
    @Autowired
    private PincodeCourierMappingSearchRepository mockPincodeCourierMappingSearchRepository;

    @Autowired
    private PincodeCourierMappingQueryService pincodeCourierMappingQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPincodeCourierMappingMockMvc;

    private PincodeCourierMapping pincodeCourierMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PincodeCourierMappingResource pincodeCourierMappingResource = new PincodeCourierMappingResource(pincodeCourierMappingService, pincodeCourierMappingQueryService);
        this.restPincodeCourierMappingMockMvc = MockMvcBuilders.standaloneSetup(pincodeCourierMappingResource)
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
    public static PincodeCourierMapping createEntity(EntityManager em) {
        PincodeCourierMapping pincodeCourierMapping = new PincodeCourierMapping()
            .routingCode(DEFAULT_ROUTING_CODE)
            .applicableForCheapestCourier(DEFAULT_APPLICABLE_FOR_CHEAPEST_COURIER)
            .estimatedDeliveryDays(DEFAULT_ESTIMATED_DELIVERY_DAYS)
            .pickupAvailable(DEFAULT_PICKUP_AVAILABLE)
            .prepaidAir(DEFAULT_PREPAID_AIR)
            .prepaidGround(DEFAULT_PREPAID_GROUND)
            .codAir(DEFAULT_COD_AIR)
            .codGround(DEFAULT_COD_GROUND)
            .reverseAir(DEFAULT_REVERSE_AIR)
            .reverseGround(DEFAULT_REVERSE_GROUND)
            .cardOnDeliveryAir(DEFAULT_CARD_ON_DELIVERY_AIR)
            .cardOnDeliveryGround(DEFAULT_CARD_ON_DELIVERY_GROUND)
            .deliveryTypeOne(DEFAULT_DELIVERY_TYPE_ONE)
            .deliveryTypeTwo(DEFAULT_DELIVERY_TYPE_TWO);
        return pincodeCourierMapping;
    }

    @Before
    public void initTest() {
        pincodeCourierMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createPincodeCourierMapping() throws Exception {
        int databaseSizeBeforeCreate = pincodeCourierMappingRepository.findAll().size();

        // Create the PincodeCourierMapping
        PincodeCourierMappingDTO pincodeCourierMappingDTO = pincodeCourierMappingMapper.toDto(pincodeCourierMapping);
        restPincodeCourierMappingMockMvc.perform(post("/api/pincode-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeCourierMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the PincodeCourierMapping in the database
        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeCreate + 1);
        PincodeCourierMapping testPincodeCourierMapping = pincodeCourierMappingList.get(pincodeCourierMappingList.size() - 1);
        assertThat(testPincodeCourierMapping.getRoutingCode()).isEqualTo(DEFAULT_ROUTING_CODE);
        assertThat(testPincodeCourierMapping.isApplicableForCheapestCourier()).isEqualTo(DEFAULT_APPLICABLE_FOR_CHEAPEST_COURIER);
        assertThat(testPincodeCourierMapping.getEstimatedDeliveryDays()).isEqualTo(DEFAULT_ESTIMATED_DELIVERY_DAYS);
        assertThat(testPincodeCourierMapping.isPickupAvailable()).isEqualTo(DEFAULT_PICKUP_AVAILABLE);
        assertThat(testPincodeCourierMapping.isPrepaidAir()).isEqualTo(DEFAULT_PREPAID_AIR);
        assertThat(testPincodeCourierMapping.isPrepaidGround()).isEqualTo(DEFAULT_PREPAID_GROUND);
        assertThat(testPincodeCourierMapping.isCodAir()).isEqualTo(DEFAULT_COD_AIR);
        assertThat(testPincodeCourierMapping.isCodGround()).isEqualTo(DEFAULT_COD_GROUND);
        assertThat(testPincodeCourierMapping.isReverseAir()).isEqualTo(DEFAULT_REVERSE_AIR);
        assertThat(testPincodeCourierMapping.isReverseGround()).isEqualTo(DEFAULT_REVERSE_GROUND);
        assertThat(testPincodeCourierMapping.isCardOnDeliveryAir()).isEqualTo(DEFAULT_CARD_ON_DELIVERY_AIR);
        assertThat(testPincodeCourierMapping.isCardOnDeliveryGround()).isEqualTo(DEFAULT_CARD_ON_DELIVERY_GROUND);
        assertThat(testPincodeCourierMapping.isDeliveryTypeOne()).isEqualTo(DEFAULT_DELIVERY_TYPE_ONE);
        assertThat(testPincodeCourierMapping.isDeliveryTypeTwo()).isEqualTo(DEFAULT_DELIVERY_TYPE_TWO);

        // Validate the PincodeCourierMapping in Elasticsearch
        verify(mockPincodeCourierMappingSearchRepository, times(1)).save(testPincodeCourierMapping);
    }

    @Test
    @Transactional
    public void createPincodeCourierMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pincodeCourierMappingRepository.findAll().size();

        // Create the PincodeCourierMapping with an existing ID
        pincodeCourierMapping.setId(1L);
        PincodeCourierMappingDTO pincodeCourierMappingDTO = pincodeCourierMappingMapper.toDto(pincodeCourierMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPincodeCourierMappingMockMvc.perform(post("/api/pincode-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PincodeCourierMapping in the database
        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeCreate);

        // Validate the PincodeCourierMapping in Elasticsearch
        verify(mockPincodeCourierMappingSearchRepository, times(0)).save(pincodeCourierMapping);
    }

    @Test
    @Transactional
    public void checkApplicableForCheapestCourierIsRequired() throws Exception {
        int databaseSizeBeforeTest = pincodeCourierMappingRepository.findAll().size();
        // set the field null
        pincodeCourierMapping.setApplicableForCheapestCourier(null);

        // Create the PincodeCourierMapping, which fails.
        PincodeCourierMappingDTO pincodeCourierMappingDTO = pincodeCourierMappingMapper.toDto(pincodeCourierMapping);

        restPincodeCourierMappingMockMvc.perform(post("/api/pincode-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPickupAvailableIsRequired() throws Exception {
        int databaseSizeBeforeTest = pincodeCourierMappingRepository.findAll().size();
        // set the field null
        pincodeCourierMapping.setPickupAvailable(null);

        // Create the PincodeCourierMapping, which fails.
        PincodeCourierMappingDTO pincodeCourierMappingDTO = pincodeCourierMappingMapper.toDto(pincodeCourierMapping);

        restPincodeCourierMappingMockMvc.perform(post("/api/pincode-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrepaidAirIsRequired() throws Exception {
        int databaseSizeBeforeTest = pincodeCourierMappingRepository.findAll().size();
        // set the field null
        pincodeCourierMapping.setPrepaidAir(null);

        // Create the PincodeCourierMapping, which fails.
        PincodeCourierMappingDTO pincodeCourierMappingDTO = pincodeCourierMappingMapper.toDto(pincodeCourierMapping);

        restPincodeCourierMappingMockMvc.perform(post("/api/pincode-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrepaidGroundIsRequired() throws Exception {
        int databaseSizeBeforeTest = pincodeCourierMappingRepository.findAll().size();
        // set the field null
        pincodeCourierMapping.setPrepaidGround(null);

        // Create the PincodeCourierMapping, which fails.
        PincodeCourierMappingDTO pincodeCourierMappingDTO = pincodeCourierMappingMapper.toDto(pincodeCourierMapping);

        restPincodeCourierMappingMockMvc.perform(post("/api/pincode-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodAirIsRequired() throws Exception {
        int databaseSizeBeforeTest = pincodeCourierMappingRepository.findAll().size();
        // set the field null
        pincodeCourierMapping.setCodAir(null);

        // Create the PincodeCourierMapping, which fails.
        PincodeCourierMappingDTO pincodeCourierMappingDTO = pincodeCourierMappingMapper.toDto(pincodeCourierMapping);

        restPincodeCourierMappingMockMvc.perform(post("/api/pincode-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodGroundIsRequired() throws Exception {
        int databaseSizeBeforeTest = pincodeCourierMappingRepository.findAll().size();
        // set the field null
        pincodeCourierMapping.setCodGround(null);

        // Create the PincodeCourierMapping, which fails.
        PincodeCourierMappingDTO pincodeCourierMappingDTO = pincodeCourierMappingMapper.toDto(pincodeCourierMapping);

        restPincodeCourierMappingMockMvc.perform(post("/api/pincode-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReverseAirIsRequired() throws Exception {
        int databaseSizeBeforeTest = pincodeCourierMappingRepository.findAll().size();
        // set the field null
        pincodeCourierMapping.setReverseAir(null);

        // Create the PincodeCourierMapping, which fails.
        PincodeCourierMappingDTO pincodeCourierMappingDTO = pincodeCourierMappingMapper.toDto(pincodeCourierMapping);

        restPincodeCourierMappingMockMvc.perform(post("/api/pincode-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReverseGroundIsRequired() throws Exception {
        int databaseSizeBeforeTest = pincodeCourierMappingRepository.findAll().size();
        // set the field null
        pincodeCourierMapping.setReverseGround(null);

        // Create the PincodeCourierMapping, which fails.
        PincodeCourierMappingDTO pincodeCourierMappingDTO = pincodeCourierMappingMapper.toDto(pincodeCourierMapping);

        restPincodeCourierMappingMockMvc.perform(post("/api/pincode-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCardOnDeliveryAirIsRequired() throws Exception {
        int databaseSizeBeforeTest = pincodeCourierMappingRepository.findAll().size();
        // set the field null
        pincodeCourierMapping.setCardOnDeliveryAir(null);

        // Create the PincodeCourierMapping, which fails.
        PincodeCourierMappingDTO pincodeCourierMappingDTO = pincodeCourierMappingMapper.toDto(pincodeCourierMapping);

        restPincodeCourierMappingMockMvc.perform(post("/api/pincode-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCardOnDeliveryGroundIsRequired() throws Exception {
        int databaseSizeBeforeTest = pincodeCourierMappingRepository.findAll().size();
        // set the field null
        pincodeCourierMapping.setCardOnDeliveryGround(null);

        // Create the PincodeCourierMapping, which fails.
        PincodeCourierMappingDTO pincodeCourierMappingDTO = pincodeCourierMappingMapper.toDto(pincodeCourierMapping);

        restPincodeCourierMappingMockMvc.perform(post("/api/pincode-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappings() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList
        restPincodeCourierMappingMockMvc.perform(get("/api/pincode-courier-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pincodeCourierMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].routingCode").value(hasItem(DEFAULT_ROUTING_CODE.toString())))
            .andExpect(jsonPath("$.[*].applicableForCheapestCourier").value(hasItem(DEFAULT_APPLICABLE_FOR_CHEAPEST_COURIER.booleanValue())))
            .andExpect(jsonPath("$.[*].estimatedDeliveryDays").value(hasItem(DEFAULT_ESTIMATED_DELIVERY_DAYS.doubleValue())))
            .andExpect(jsonPath("$.[*].pickupAvailable").value(hasItem(DEFAULT_PICKUP_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].prepaidAir").value(hasItem(DEFAULT_PREPAID_AIR.booleanValue())))
            .andExpect(jsonPath("$.[*].prepaidGround").value(hasItem(DEFAULT_PREPAID_GROUND.booleanValue())))
            .andExpect(jsonPath("$.[*].codAir").value(hasItem(DEFAULT_COD_AIR.booleanValue())))
            .andExpect(jsonPath("$.[*].codGround").value(hasItem(DEFAULT_COD_GROUND.booleanValue())))
            .andExpect(jsonPath("$.[*].reverseAir").value(hasItem(DEFAULT_REVERSE_AIR.booleanValue())))
            .andExpect(jsonPath("$.[*].reverseGround").value(hasItem(DEFAULT_REVERSE_GROUND.booleanValue())))
            .andExpect(jsonPath("$.[*].cardOnDeliveryAir").value(hasItem(DEFAULT_CARD_ON_DELIVERY_AIR.booleanValue())))
            .andExpect(jsonPath("$.[*].cardOnDeliveryGround").value(hasItem(DEFAULT_CARD_ON_DELIVERY_GROUND.booleanValue())))
            .andExpect(jsonPath("$.[*].deliveryTypeOne").value(hasItem(DEFAULT_DELIVERY_TYPE_ONE.booleanValue())))
            .andExpect(jsonPath("$.[*].deliveryTypeTwo").value(hasItem(DEFAULT_DELIVERY_TYPE_TWO.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getPincodeCourierMapping() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get the pincodeCourierMapping
        restPincodeCourierMappingMockMvc.perform(get("/api/pincode-courier-mappings/{id}", pincodeCourierMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pincodeCourierMapping.getId().intValue()))
            .andExpect(jsonPath("$.routingCode").value(DEFAULT_ROUTING_CODE.toString()))
            .andExpect(jsonPath("$.applicableForCheapestCourier").value(DEFAULT_APPLICABLE_FOR_CHEAPEST_COURIER.booleanValue()))
            .andExpect(jsonPath("$.estimatedDeliveryDays").value(DEFAULT_ESTIMATED_DELIVERY_DAYS.doubleValue()))
            .andExpect(jsonPath("$.pickupAvailable").value(DEFAULT_PICKUP_AVAILABLE.booleanValue()))
            .andExpect(jsonPath("$.prepaidAir").value(DEFAULT_PREPAID_AIR.booleanValue()))
            .andExpect(jsonPath("$.prepaidGround").value(DEFAULT_PREPAID_GROUND.booleanValue()))
            .andExpect(jsonPath("$.codAir").value(DEFAULT_COD_AIR.booleanValue()))
            .andExpect(jsonPath("$.codGround").value(DEFAULT_COD_GROUND.booleanValue()))
            .andExpect(jsonPath("$.reverseAir").value(DEFAULT_REVERSE_AIR.booleanValue()))
            .andExpect(jsonPath("$.reverseGround").value(DEFAULT_REVERSE_GROUND.booleanValue()))
            .andExpect(jsonPath("$.cardOnDeliveryAir").value(DEFAULT_CARD_ON_DELIVERY_AIR.booleanValue()))
            .andExpect(jsonPath("$.cardOnDeliveryGround").value(DEFAULT_CARD_ON_DELIVERY_GROUND.booleanValue()))
            .andExpect(jsonPath("$.deliveryTypeOne").value(DEFAULT_DELIVERY_TYPE_ONE.booleanValue()))
            .andExpect(jsonPath("$.deliveryTypeTwo").value(DEFAULT_DELIVERY_TYPE_TWO.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByRoutingCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where routingCode equals to DEFAULT_ROUTING_CODE
        defaultPincodeCourierMappingShouldBeFound("routingCode.equals=" + DEFAULT_ROUTING_CODE);

        // Get all the pincodeCourierMappingList where routingCode equals to UPDATED_ROUTING_CODE
        defaultPincodeCourierMappingShouldNotBeFound("routingCode.equals=" + UPDATED_ROUTING_CODE);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByRoutingCodeIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where routingCode in DEFAULT_ROUTING_CODE or UPDATED_ROUTING_CODE
        defaultPincodeCourierMappingShouldBeFound("routingCode.in=" + DEFAULT_ROUTING_CODE + "," + UPDATED_ROUTING_CODE);

        // Get all the pincodeCourierMappingList where routingCode equals to UPDATED_ROUTING_CODE
        defaultPincodeCourierMappingShouldNotBeFound("routingCode.in=" + UPDATED_ROUTING_CODE);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByRoutingCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where routingCode is not null
        defaultPincodeCourierMappingShouldBeFound("routingCode.specified=true");

        // Get all the pincodeCourierMappingList where routingCode is null
        defaultPincodeCourierMappingShouldNotBeFound("routingCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByApplicableForCheapestCourierIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where applicableForCheapestCourier equals to DEFAULT_APPLICABLE_FOR_CHEAPEST_COURIER
        defaultPincodeCourierMappingShouldBeFound("applicableForCheapestCourier.equals=" + DEFAULT_APPLICABLE_FOR_CHEAPEST_COURIER);

        // Get all the pincodeCourierMappingList where applicableForCheapestCourier equals to UPDATED_APPLICABLE_FOR_CHEAPEST_COURIER
        defaultPincodeCourierMappingShouldNotBeFound("applicableForCheapestCourier.equals=" + UPDATED_APPLICABLE_FOR_CHEAPEST_COURIER);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByApplicableForCheapestCourierIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where applicableForCheapestCourier in DEFAULT_APPLICABLE_FOR_CHEAPEST_COURIER or UPDATED_APPLICABLE_FOR_CHEAPEST_COURIER
        defaultPincodeCourierMappingShouldBeFound("applicableForCheapestCourier.in=" + DEFAULT_APPLICABLE_FOR_CHEAPEST_COURIER + "," + UPDATED_APPLICABLE_FOR_CHEAPEST_COURIER);

        // Get all the pincodeCourierMappingList where applicableForCheapestCourier equals to UPDATED_APPLICABLE_FOR_CHEAPEST_COURIER
        defaultPincodeCourierMappingShouldNotBeFound("applicableForCheapestCourier.in=" + UPDATED_APPLICABLE_FOR_CHEAPEST_COURIER);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByApplicableForCheapestCourierIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where applicableForCheapestCourier is not null
        defaultPincodeCourierMappingShouldBeFound("applicableForCheapestCourier.specified=true");

        // Get all the pincodeCourierMappingList where applicableForCheapestCourier is null
        defaultPincodeCourierMappingShouldNotBeFound("applicableForCheapestCourier.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByEstimatedDeliveryDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where estimatedDeliveryDays equals to DEFAULT_ESTIMATED_DELIVERY_DAYS
        defaultPincodeCourierMappingShouldBeFound("estimatedDeliveryDays.equals=" + DEFAULT_ESTIMATED_DELIVERY_DAYS);

        // Get all the pincodeCourierMappingList where estimatedDeliveryDays equals to UPDATED_ESTIMATED_DELIVERY_DAYS
        defaultPincodeCourierMappingShouldNotBeFound("estimatedDeliveryDays.equals=" + UPDATED_ESTIMATED_DELIVERY_DAYS);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByEstimatedDeliveryDaysIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where estimatedDeliveryDays in DEFAULT_ESTIMATED_DELIVERY_DAYS or UPDATED_ESTIMATED_DELIVERY_DAYS
        defaultPincodeCourierMappingShouldBeFound("estimatedDeliveryDays.in=" + DEFAULT_ESTIMATED_DELIVERY_DAYS + "," + UPDATED_ESTIMATED_DELIVERY_DAYS);

        // Get all the pincodeCourierMappingList where estimatedDeliveryDays equals to UPDATED_ESTIMATED_DELIVERY_DAYS
        defaultPincodeCourierMappingShouldNotBeFound("estimatedDeliveryDays.in=" + UPDATED_ESTIMATED_DELIVERY_DAYS);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByEstimatedDeliveryDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where estimatedDeliveryDays is not null
        defaultPincodeCourierMappingShouldBeFound("estimatedDeliveryDays.specified=true");

        // Get all the pincodeCourierMappingList where estimatedDeliveryDays is null
        defaultPincodeCourierMappingShouldNotBeFound("estimatedDeliveryDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByPickupAvailableIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where pickupAvailable equals to DEFAULT_PICKUP_AVAILABLE
        defaultPincodeCourierMappingShouldBeFound("pickupAvailable.equals=" + DEFAULT_PICKUP_AVAILABLE);

        // Get all the pincodeCourierMappingList where pickupAvailable equals to UPDATED_PICKUP_AVAILABLE
        defaultPincodeCourierMappingShouldNotBeFound("pickupAvailable.equals=" + UPDATED_PICKUP_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByPickupAvailableIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where pickupAvailable in DEFAULT_PICKUP_AVAILABLE or UPDATED_PICKUP_AVAILABLE
        defaultPincodeCourierMappingShouldBeFound("pickupAvailable.in=" + DEFAULT_PICKUP_AVAILABLE + "," + UPDATED_PICKUP_AVAILABLE);

        // Get all the pincodeCourierMappingList where pickupAvailable equals to UPDATED_PICKUP_AVAILABLE
        defaultPincodeCourierMappingShouldNotBeFound("pickupAvailable.in=" + UPDATED_PICKUP_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByPickupAvailableIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where pickupAvailable is not null
        defaultPincodeCourierMappingShouldBeFound("pickupAvailable.specified=true");

        // Get all the pincodeCourierMappingList where pickupAvailable is null
        defaultPincodeCourierMappingShouldNotBeFound("pickupAvailable.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByPrepaidAirIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where prepaidAir equals to DEFAULT_PREPAID_AIR
        defaultPincodeCourierMappingShouldBeFound("prepaidAir.equals=" + DEFAULT_PREPAID_AIR);

        // Get all the pincodeCourierMappingList where prepaidAir equals to UPDATED_PREPAID_AIR
        defaultPincodeCourierMappingShouldNotBeFound("prepaidAir.equals=" + UPDATED_PREPAID_AIR);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByPrepaidAirIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where prepaidAir in DEFAULT_PREPAID_AIR or UPDATED_PREPAID_AIR
        defaultPincodeCourierMappingShouldBeFound("prepaidAir.in=" + DEFAULT_PREPAID_AIR + "," + UPDATED_PREPAID_AIR);

        // Get all the pincodeCourierMappingList where prepaidAir equals to UPDATED_PREPAID_AIR
        defaultPincodeCourierMappingShouldNotBeFound("prepaidAir.in=" + UPDATED_PREPAID_AIR);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByPrepaidAirIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where prepaidAir is not null
        defaultPincodeCourierMappingShouldBeFound("prepaidAir.specified=true");

        // Get all the pincodeCourierMappingList where prepaidAir is null
        defaultPincodeCourierMappingShouldNotBeFound("prepaidAir.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByPrepaidGroundIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where prepaidGround equals to DEFAULT_PREPAID_GROUND
        defaultPincodeCourierMappingShouldBeFound("prepaidGround.equals=" + DEFAULT_PREPAID_GROUND);

        // Get all the pincodeCourierMappingList where prepaidGround equals to UPDATED_PREPAID_GROUND
        defaultPincodeCourierMappingShouldNotBeFound("prepaidGround.equals=" + UPDATED_PREPAID_GROUND);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByPrepaidGroundIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where prepaidGround in DEFAULT_PREPAID_GROUND or UPDATED_PREPAID_GROUND
        defaultPincodeCourierMappingShouldBeFound("prepaidGround.in=" + DEFAULT_PREPAID_GROUND + "," + UPDATED_PREPAID_GROUND);

        // Get all the pincodeCourierMappingList where prepaidGround equals to UPDATED_PREPAID_GROUND
        defaultPincodeCourierMappingShouldNotBeFound("prepaidGround.in=" + UPDATED_PREPAID_GROUND);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByPrepaidGroundIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where prepaidGround is not null
        defaultPincodeCourierMappingShouldBeFound("prepaidGround.specified=true");

        // Get all the pincodeCourierMappingList where prepaidGround is null
        defaultPincodeCourierMappingShouldNotBeFound("prepaidGround.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByCodAirIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where codAir equals to DEFAULT_COD_AIR
        defaultPincodeCourierMappingShouldBeFound("codAir.equals=" + DEFAULT_COD_AIR);

        // Get all the pincodeCourierMappingList where codAir equals to UPDATED_COD_AIR
        defaultPincodeCourierMappingShouldNotBeFound("codAir.equals=" + UPDATED_COD_AIR);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByCodAirIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where codAir in DEFAULT_COD_AIR or UPDATED_COD_AIR
        defaultPincodeCourierMappingShouldBeFound("codAir.in=" + DEFAULT_COD_AIR + "," + UPDATED_COD_AIR);

        // Get all the pincodeCourierMappingList where codAir equals to UPDATED_COD_AIR
        defaultPincodeCourierMappingShouldNotBeFound("codAir.in=" + UPDATED_COD_AIR);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByCodAirIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where codAir is not null
        defaultPincodeCourierMappingShouldBeFound("codAir.specified=true");

        // Get all the pincodeCourierMappingList where codAir is null
        defaultPincodeCourierMappingShouldNotBeFound("codAir.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByCodGroundIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where codGround equals to DEFAULT_COD_GROUND
        defaultPincodeCourierMappingShouldBeFound("codGround.equals=" + DEFAULT_COD_GROUND);

        // Get all the pincodeCourierMappingList where codGround equals to UPDATED_COD_GROUND
        defaultPincodeCourierMappingShouldNotBeFound("codGround.equals=" + UPDATED_COD_GROUND);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByCodGroundIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where codGround in DEFAULT_COD_GROUND or UPDATED_COD_GROUND
        defaultPincodeCourierMappingShouldBeFound("codGround.in=" + DEFAULT_COD_GROUND + "," + UPDATED_COD_GROUND);

        // Get all the pincodeCourierMappingList where codGround equals to UPDATED_COD_GROUND
        defaultPincodeCourierMappingShouldNotBeFound("codGround.in=" + UPDATED_COD_GROUND);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByCodGroundIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where codGround is not null
        defaultPincodeCourierMappingShouldBeFound("codGround.specified=true");

        // Get all the pincodeCourierMappingList where codGround is null
        defaultPincodeCourierMappingShouldNotBeFound("codGround.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByReverseAirIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where reverseAir equals to DEFAULT_REVERSE_AIR
        defaultPincodeCourierMappingShouldBeFound("reverseAir.equals=" + DEFAULT_REVERSE_AIR);

        // Get all the pincodeCourierMappingList where reverseAir equals to UPDATED_REVERSE_AIR
        defaultPincodeCourierMappingShouldNotBeFound("reverseAir.equals=" + UPDATED_REVERSE_AIR);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByReverseAirIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where reverseAir in DEFAULT_REVERSE_AIR or UPDATED_REVERSE_AIR
        defaultPincodeCourierMappingShouldBeFound("reverseAir.in=" + DEFAULT_REVERSE_AIR + "," + UPDATED_REVERSE_AIR);

        // Get all the pincodeCourierMappingList where reverseAir equals to UPDATED_REVERSE_AIR
        defaultPincodeCourierMappingShouldNotBeFound("reverseAir.in=" + UPDATED_REVERSE_AIR);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByReverseAirIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where reverseAir is not null
        defaultPincodeCourierMappingShouldBeFound("reverseAir.specified=true");

        // Get all the pincodeCourierMappingList where reverseAir is null
        defaultPincodeCourierMappingShouldNotBeFound("reverseAir.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByReverseGroundIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where reverseGround equals to DEFAULT_REVERSE_GROUND
        defaultPincodeCourierMappingShouldBeFound("reverseGround.equals=" + DEFAULT_REVERSE_GROUND);

        // Get all the pincodeCourierMappingList where reverseGround equals to UPDATED_REVERSE_GROUND
        defaultPincodeCourierMappingShouldNotBeFound("reverseGround.equals=" + UPDATED_REVERSE_GROUND);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByReverseGroundIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where reverseGround in DEFAULT_REVERSE_GROUND or UPDATED_REVERSE_GROUND
        defaultPincodeCourierMappingShouldBeFound("reverseGround.in=" + DEFAULT_REVERSE_GROUND + "," + UPDATED_REVERSE_GROUND);

        // Get all the pincodeCourierMappingList where reverseGround equals to UPDATED_REVERSE_GROUND
        defaultPincodeCourierMappingShouldNotBeFound("reverseGround.in=" + UPDATED_REVERSE_GROUND);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByReverseGroundIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where reverseGround is not null
        defaultPincodeCourierMappingShouldBeFound("reverseGround.specified=true");

        // Get all the pincodeCourierMappingList where reverseGround is null
        defaultPincodeCourierMappingShouldNotBeFound("reverseGround.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByCardOnDeliveryAirIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where cardOnDeliveryAir equals to DEFAULT_CARD_ON_DELIVERY_AIR
        defaultPincodeCourierMappingShouldBeFound("cardOnDeliveryAir.equals=" + DEFAULT_CARD_ON_DELIVERY_AIR);

        // Get all the pincodeCourierMappingList where cardOnDeliveryAir equals to UPDATED_CARD_ON_DELIVERY_AIR
        defaultPincodeCourierMappingShouldNotBeFound("cardOnDeliveryAir.equals=" + UPDATED_CARD_ON_DELIVERY_AIR);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByCardOnDeliveryAirIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where cardOnDeliveryAir in DEFAULT_CARD_ON_DELIVERY_AIR or UPDATED_CARD_ON_DELIVERY_AIR
        defaultPincodeCourierMappingShouldBeFound("cardOnDeliveryAir.in=" + DEFAULT_CARD_ON_DELIVERY_AIR + "," + UPDATED_CARD_ON_DELIVERY_AIR);

        // Get all the pincodeCourierMappingList where cardOnDeliveryAir equals to UPDATED_CARD_ON_DELIVERY_AIR
        defaultPincodeCourierMappingShouldNotBeFound("cardOnDeliveryAir.in=" + UPDATED_CARD_ON_DELIVERY_AIR);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByCardOnDeliveryAirIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where cardOnDeliveryAir is not null
        defaultPincodeCourierMappingShouldBeFound("cardOnDeliveryAir.specified=true");

        // Get all the pincodeCourierMappingList where cardOnDeliveryAir is null
        defaultPincodeCourierMappingShouldNotBeFound("cardOnDeliveryAir.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByCardOnDeliveryGroundIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where cardOnDeliveryGround equals to DEFAULT_CARD_ON_DELIVERY_GROUND
        defaultPincodeCourierMappingShouldBeFound("cardOnDeliveryGround.equals=" + DEFAULT_CARD_ON_DELIVERY_GROUND);

        // Get all the pincodeCourierMappingList where cardOnDeliveryGround equals to UPDATED_CARD_ON_DELIVERY_GROUND
        defaultPincodeCourierMappingShouldNotBeFound("cardOnDeliveryGround.equals=" + UPDATED_CARD_ON_DELIVERY_GROUND);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByCardOnDeliveryGroundIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where cardOnDeliveryGround in DEFAULT_CARD_ON_DELIVERY_GROUND or UPDATED_CARD_ON_DELIVERY_GROUND
        defaultPincodeCourierMappingShouldBeFound("cardOnDeliveryGround.in=" + DEFAULT_CARD_ON_DELIVERY_GROUND + "," + UPDATED_CARD_ON_DELIVERY_GROUND);

        // Get all the pincodeCourierMappingList where cardOnDeliveryGround equals to UPDATED_CARD_ON_DELIVERY_GROUND
        defaultPincodeCourierMappingShouldNotBeFound("cardOnDeliveryGround.in=" + UPDATED_CARD_ON_DELIVERY_GROUND);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByCardOnDeliveryGroundIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where cardOnDeliveryGround is not null
        defaultPincodeCourierMappingShouldBeFound("cardOnDeliveryGround.specified=true");

        // Get all the pincodeCourierMappingList where cardOnDeliveryGround is null
        defaultPincodeCourierMappingShouldNotBeFound("cardOnDeliveryGround.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByDeliveryTypeOneIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where deliveryTypeOne equals to DEFAULT_DELIVERY_TYPE_ONE
        defaultPincodeCourierMappingShouldBeFound("deliveryTypeOne.equals=" + DEFAULT_DELIVERY_TYPE_ONE);

        // Get all the pincodeCourierMappingList where deliveryTypeOne equals to UPDATED_DELIVERY_TYPE_ONE
        defaultPincodeCourierMappingShouldNotBeFound("deliveryTypeOne.equals=" + UPDATED_DELIVERY_TYPE_ONE);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByDeliveryTypeOneIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where deliveryTypeOne in DEFAULT_DELIVERY_TYPE_ONE or UPDATED_DELIVERY_TYPE_ONE
        defaultPincodeCourierMappingShouldBeFound("deliveryTypeOne.in=" + DEFAULT_DELIVERY_TYPE_ONE + "," + UPDATED_DELIVERY_TYPE_ONE);

        // Get all the pincodeCourierMappingList where deliveryTypeOne equals to UPDATED_DELIVERY_TYPE_ONE
        defaultPincodeCourierMappingShouldNotBeFound("deliveryTypeOne.in=" + UPDATED_DELIVERY_TYPE_ONE);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByDeliveryTypeOneIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where deliveryTypeOne is not null
        defaultPincodeCourierMappingShouldBeFound("deliveryTypeOne.specified=true");

        // Get all the pincodeCourierMappingList where deliveryTypeOne is null
        defaultPincodeCourierMappingShouldNotBeFound("deliveryTypeOne.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByDeliveryTypeTwoIsEqualToSomething() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where deliveryTypeTwo equals to DEFAULT_DELIVERY_TYPE_TWO
        defaultPincodeCourierMappingShouldBeFound("deliveryTypeTwo.equals=" + DEFAULT_DELIVERY_TYPE_TWO);

        // Get all the pincodeCourierMappingList where deliveryTypeTwo equals to UPDATED_DELIVERY_TYPE_TWO
        defaultPincodeCourierMappingShouldNotBeFound("deliveryTypeTwo.equals=" + UPDATED_DELIVERY_TYPE_TWO);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByDeliveryTypeTwoIsInShouldWork() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where deliveryTypeTwo in DEFAULT_DELIVERY_TYPE_TWO or UPDATED_DELIVERY_TYPE_TWO
        defaultPincodeCourierMappingShouldBeFound("deliveryTypeTwo.in=" + DEFAULT_DELIVERY_TYPE_TWO + "," + UPDATED_DELIVERY_TYPE_TWO);

        // Get all the pincodeCourierMappingList where deliveryTypeTwo equals to UPDATED_DELIVERY_TYPE_TWO
        defaultPincodeCourierMappingShouldNotBeFound("deliveryTypeTwo.in=" + UPDATED_DELIVERY_TYPE_TWO);
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByDeliveryTypeTwoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        // Get all the pincodeCourierMappingList where deliveryTypeTwo is not null
        defaultPincodeCourierMappingShouldBeFound("deliveryTypeTwo.specified=true");

        // Get all the pincodeCourierMappingList where deliveryTypeTwo is null
        defaultPincodeCourierMappingShouldNotBeFound("deliveryTypeTwo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        Pincode pincode = PincodeResourceIntTest.createEntity(em);
        em.persist(pincode);
        em.flush();
        pincodeCourierMapping.setPincode(pincode);
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);
        Long pincodeId = pincode.getId();

        // Get all the pincodeCourierMappingList where pincode equals to pincodeId
        defaultPincodeCourierMappingShouldBeFound("pincodeId.equals=" + pincodeId);

        // Get all the pincodeCourierMappingList where pincode equals to pincodeId + 1
        defaultPincodeCourierMappingShouldNotBeFound("pincodeId.equals=" + (pincodeId + 1));
    }


    @Test
    @Transactional
    public void getAllPincodeCourierMappingsByVendorWHCourierMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        VendorWHCourierMapping vendorWHCourierMapping = VendorWHCourierMappingResourceIntTest.createEntity(em);
        em.persist(vendorWHCourierMapping);
        em.flush();
        pincodeCourierMapping.setVendorWHCourierMapping(vendorWHCourierMapping);
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);
        Long vendorWHCourierMappingId = vendorWHCourierMapping.getId();

        // Get all the pincodeCourierMappingList where vendorWHCourierMapping equals to vendorWHCourierMappingId
        defaultPincodeCourierMappingShouldBeFound("vendorWHCourierMappingId.equals=" + vendorWHCourierMappingId);

        // Get all the pincodeCourierMappingList where vendorWHCourierMapping equals to vendorWHCourierMappingId + 1
        defaultPincodeCourierMappingShouldNotBeFound("vendorWHCourierMappingId.equals=" + (vendorWHCourierMappingId + 1));
    }


    @Test
    @Transactional
    public void getAllPincodeCourierMappingsBySourceDestinationMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        SourceDestinationMapping sourceDestinationMapping = SourceDestinationMappingResourceIntTest.createEntity(em);
        em.persist(sourceDestinationMapping);
        em.flush();
        pincodeCourierMapping.setSourceDestinationMapping(sourceDestinationMapping);
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);
        Long sourceDestinationMappingId = sourceDestinationMapping.getId();

        // Get all the pincodeCourierMappingList where sourceDestinationMapping equals to sourceDestinationMappingId
        defaultPincodeCourierMappingShouldBeFound("sourceDestinationMappingId.equals=" + sourceDestinationMappingId);

        // Get all the pincodeCourierMappingList where sourceDestinationMapping equals to sourceDestinationMappingId + 1
        defaultPincodeCourierMappingShouldNotBeFound("sourceDestinationMappingId.equals=" + (sourceDestinationMappingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPincodeCourierMappingShouldBeFound(String filter) throws Exception {
        restPincodeCourierMappingMockMvc.perform(get("/api/pincode-courier-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pincodeCourierMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].routingCode").value(hasItem(DEFAULT_ROUTING_CODE.toString())))
            .andExpect(jsonPath("$.[*].applicableForCheapestCourier").value(hasItem(DEFAULT_APPLICABLE_FOR_CHEAPEST_COURIER.booleanValue())))
            .andExpect(jsonPath("$.[*].estimatedDeliveryDays").value(hasItem(DEFAULT_ESTIMATED_DELIVERY_DAYS.doubleValue())))
            .andExpect(jsonPath("$.[*].pickupAvailable").value(hasItem(DEFAULT_PICKUP_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].prepaidAir").value(hasItem(DEFAULT_PREPAID_AIR.booleanValue())))
            .andExpect(jsonPath("$.[*].prepaidGround").value(hasItem(DEFAULT_PREPAID_GROUND.booleanValue())))
            .andExpect(jsonPath("$.[*].codAir").value(hasItem(DEFAULT_COD_AIR.booleanValue())))
            .andExpect(jsonPath("$.[*].codGround").value(hasItem(DEFAULT_COD_GROUND.booleanValue())))
            .andExpect(jsonPath("$.[*].reverseAir").value(hasItem(DEFAULT_REVERSE_AIR.booleanValue())))
            .andExpect(jsonPath("$.[*].reverseGround").value(hasItem(DEFAULT_REVERSE_GROUND.booleanValue())))
            .andExpect(jsonPath("$.[*].cardOnDeliveryAir").value(hasItem(DEFAULT_CARD_ON_DELIVERY_AIR.booleanValue())))
            .andExpect(jsonPath("$.[*].cardOnDeliveryGround").value(hasItem(DEFAULT_CARD_ON_DELIVERY_GROUND.booleanValue())))
            .andExpect(jsonPath("$.[*].deliveryTypeOne").value(hasItem(DEFAULT_DELIVERY_TYPE_ONE.booleanValue())))
            .andExpect(jsonPath("$.[*].deliveryTypeTwo").value(hasItem(DEFAULT_DELIVERY_TYPE_TWO.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPincodeCourierMappingShouldNotBeFound(String filter) throws Exception {
        restPincodeCourierMappingMockMvc.perform(get("/api/pincode-courier-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingPincodeCourierMapping() throws Exception {
        // Get the pincodeCourierMapping
        restPincodeCourierMappingMockMvc.perform(get("/api/pincode-courier-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePincodeCourierMapping() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        int databaseSizeBeforeUpdate = pincodeCourierMappingRepository.findAll().size();

        // Update the pincodeCourierMapping
        PincodeCourierMapping updatedPincodeCourierMapping = pincodeCourierMappingRepository.findById(pincodeCourierMapping.getId()).get();
        // Disconnect from session so that the updates on updatedPincodeCourierMapping are not directly saved in db
        em.detach(updatedPincodeCourierMapping);
        updatedPincodeCourierMapping
            .routingCode(UPDATED_ROUTING_CODE)
            .applicableForCheapestCourier(UPDATED_APPLICABLE_FOR_CHEAPEST_COURIER)
            .estimatedDeliveryDays(UPDATED_ESTIMATED_DELIVERY_DAYS)
            .pickupAvailable(UPDATED_PICKUP_AVAILABLE)
            .prepaidAir(UPDATED_PREPAID_AIR)
            .prepaidGround(UPDATED_PREPAID_GROUND)
            .codAir(UPDATED_COD_AIR)
            .codGround(UPDATED_COD_GROUND)
            .reverseAir(UPDATED_REVERSE_AIR)
            .reverseGround(UPDATED_REVERSE_GROUND)
            .cardOnDeliveryAir(UPDATED_CARD_ON_DELIVERY_AIR)
            .cardOnDeliveryGround(UPDATED_CARD_ON_DELIVERY_GROUND)
            .deliveryTypeOne(UPDATED_DELIVERY_TYPE_ONE)
            .deliveryTypeTwo(UPDATED_DELIVERY_TYPE_TWO);
        PincodeCourierMappingDTO pincodeCourierMappingDTO = pincodeCourierMappingMapper.toDto(updatedPincodeCourierMapping);

        restPincodeCourierMappingMockMvc.perform(put("/api/pincode-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeCourierMappingDTO)))
            .andExpect(status().isOk());

        // Validate the PincodeCourierMapping in the database
        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeUpdate);
        PincodeCourierMapping testPincodeCourierMapping = pincodeCourierMappingList.get(pincodeCourierMappingList.size() - 1);
        assertThat(testPincodeCourierMapping.getRoutingCode()).isEqualTo(UPDATED_ROUTING_CODE);
        assertThat(testPincodeCourierMapping.isApplicableForCheapestCourier()).isEqualTo(UPDATED_APPLICABLE_FOR_CHEAPEST_COURIER);
        assertThat(testPincodeCourierMapping.getEstimatedDeliveryDays()).isEqualTo(UPDATED_ESTIMATED_DELIVERY_DAYS);
        assertThat(testPincodeCourierMapping.isPickupAvailable()).isEqualTo(UPDATED_PICKUP_AVAILABLE);
        assertThat(testPincodeCourierMapping.isPrepaidAir()).isEqualTo(UPDATED_PREPAID_AIR);
        assertThat(testPincodeCourierMapping.isPrepaidGround()).isEqualTo(UPDATED_PREPAID_GROUND);
        assertThat(testPincodeCourierMapping.isCodAir()).isEqualTo(UPDATED_COD_AIR);
        assertThat(testPincodeCourierMapping.isCodGround()).isEqualTo(UPDATED_COD_GROUND);
        assertThat(testPincodeCourierMapping.isReverseAir()).isEqualTo(UPDATED_REVERSE_AIR);
        assertThat(testPincodeCourierMapping.isReverseGround()).isEqualTo(UPDATED_REVERSE_GROUND);
        assertThat(testPincodeCourierMapping.isCardOnDeliveryAir()).isEqualTo(UPDATED_CARD_ON_DELIVERY_AIR);
        assertThat(testPincodeCourierMapping.isCardOnDeliveryGround()).isEqualTo(UPDATED_CARD_ON_DELIVERY_GROUND);
        assertThat(testPincodeCourierMapping.isDeliveryTypeOne()).isEqualTo(UPDATED_DELIVERY_TYPE_ONE);
        assertThat(testPincodeCourierMapping.isDeliveryTypeTwo()).isEqualTo(UPDATED_DELIVERY_TYPE_TWO);

        // Validate the PincodeCourierMapping in Elasticsearch
        verify(mockPincodeCourierMappingSearchRepository, times(1)).save(testPincodeCourierMapping);
    }

    @Test
    @Transactional
    public void updateNonExistingPincodeCourierMapping() throws Exception {
        int databaseSizeBeforeUpdate = pincodeCourierMappingRepository.findAll().size();

        // Create the PincodeCourierMapping
        PincodeCourierMappingDTO pincodeCourierMappingDTO = pincodeCourierMappingMapper.toDto(pincodeCourierMapping);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPincodeCourierMappingMockMvc.perform(put("/api/pincode-courier-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pincodeCourierMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PincodeCourierMapping in the database
        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PincodeCourierMapping in Elasticsearch
        verify(mockPincodeCourierMappingSearchRepository, times(0)).save(pincodeCourierMapping);
    }

    @Test
    @Transactional
    public void deletePincodeCourierMapping() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);

        int databaseSizeBeforeDelete = pincodeCourierMappingRepository.findAll().size();

        // Get the pincodeCourierMapping
        restPincodeCourierMappingMockMvc.perform(delete("/api/pincode-courier-mappings/{id}", pincodeCourierMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PincodeCourierMapping> pincodeCourierMappingList = pincodeCourierMappingRepository.findAll();
        assertThat(pincodeCourierMappingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PincodeCourierMapping in Elasticsearch
        verify(mockPincodeCourierMappingSearchRepository, times(1)).deleteById(pincodeCourierMapping.getId());
    }

    @Test
    @Transactional
    public void searchPincodeCourierMapping() throws Exception {
        // Initialize the database
        pincodeCourierMappingRepository.saveAndFlush(pincodeCourierMapping);
        when(mockPincodeCourierMappingSearchRepository.search(queryStringQuery("id:" + pincodeCourierMapping.getId())))
            .thenReturn(Collections.singletonList(pincodeCourierMapping));
        // Search the pincodeCourierMapping
        restPincodeCourierMappingMockMvc.perform(get("/api/_search/pincode-courier-mappings?query=id:" + pincodeCourierMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pincodeCourierMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].routingCode").value(hasItem(DEFAULT_ROUTING_CODE.toString())))
            .andExpect(jsonPath("$.[*].applicableForCheapestCourier").value(hasItem(DEFAULT_APPLICABLE_FOR_CHEAPEST_COURIER.booleanValue())))
            .andExpect(jsonPath("$.[*].estimatedDeliveryDays").value(hasItem(DEFAULT_ESTIMATED_DELIVERY_DAYS.doubleValue())))
            .andExpect(jsonPath("$.[*].pickupAvailable").value(hasItem(DEFAULT_PICKUP_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].prepaidAir").value(hasItem(DEFAULT_PREPAID_AIR.booleanValue())))
            .andExpect(jsonPath("$.[*].prepaidGround").value(hasItem(DEFAULT_PREPAID_GROUND.booleanValue())))
            .andExpect(jsonPath("$.[*].codAir").value(hasItem(DEFAULT_COD_AIR.booleanValue())))
            .andExpect(jsonPath("$.[*].codGround").value(hasItem(DEFAULT_COD_GROUND.booleanValue())))
            .andExpect(jsonPath("$.[*].reverseAir").value(hasItem(DEFAULT_REVERSE_AIR.booleanValue())))
            .andExpect(jsonPath("$.[*].reverseGround").value(hasItem(DEFAULT_REVERSE_GROUND.booleanValue())))
            .andExpect(jsonPath("$.[*].cardOnDeliveryAir").value(hasItem(DEFAULT_CARD_ON_DELIVERY_AIR.booleanValue())))
            .andExpect(jsonPath("$.[*].cardOnDeliveryGround").value(hasItem(DEFAULT_CARD_ON_DELIVERY_GROUND.booleanValue())))
            .andExpect(jsonPath("$.[*].deliveryTypeOne").value(hasItem(DEFAULT_DELIVERY_TYPE_ONE.booleanValue())))
            .andExpect(jsonPath("$.[*].deliveryTypeTwo").value(hasItem(DEFAULT_DELIVERY_TYPE_TWO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PincodeCourierMapping.class);
        PincodeCourierMapping pincodeCourierMapping1 = new PincodeCourierMapping();
        pincodeCourierMapping1.setId(1L);
        PincodeCourierMapping pincodeCourierMapping2 = new PincodeCourierMapping();
        pincodeCourierMapping2.setId(pincodeCourierMapping1.getId());
        assertThat(pincodeCourierMapping1).isEqualTo(pincodeCourierMapping2);
        pincodeCourierMapping2.setId(2L);
        assertThat(pincodeCourierMapping1).isNotEqualTo(pincodeCourierMapping2);
        pincodeCourierMapping1.setId(null);
        assertThat(pincodeCourierMapping1).isNotEqualTo(pincodeCourierMapping2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PincodeCourierMappingDTO.class);
        PincodeCourierMappingDTO pincodeCourierMappingDTO1 = new PincodeCourierMappingDTO();
        pincodeCourierMappingDTO1.setId(1L);
        PincodeCourierMappingDTO pincodeCourierMappingDTO2 = new PincodeCourierMappingDTO();
        assertThat(pincodeCourierMappingDTO1).isNotEqualTo(pincodeCourierMappingDTO2);
        pincodeCourierMappingDTO2.setId(pincodeCourierMappingDTO1.getId());
        assertThat(pincodeCourierMappingDTO1).isEqualTo(pincodeCourierMappingDTO2);
        pincodeCourierMappingDTO2.setId(2L);
        assertThat(pincodeCourierMappingDTO1).isNotEqualTo(pincodeCourierMappingDTO2);
        pincodeCourierMappingDTO1.setId(null);
        assertThat(pincodeCourierMappingDTO1).isNotEqualTo(pincodeCourierMappingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pincodeCourierMappingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pincodeCourierMappingMapper.fromId(null)).isNull();
    }
}
