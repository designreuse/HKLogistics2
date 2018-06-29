package com.hk.logistics.web.rest;

import com.hk.logistics.HkLogisticsApp;

import com.hk.logistics.domain.Courier;
import com.hk.logistics.domain.VendorWHCourierMapping;
import com.hk.logistics.domain.CourierChannel;
import com.hk.logistics.domain.CourierGroup;
import com.hk.logistics.repository.CourierRepository;
import com.hk.logistics.repository.search.CourierSearchRepository;
import com.hk.logistics.service.CourierService;
import com.hk.logistics.service.dto.CourierDTO;
import com.hk.logistics.service.mapper.CourierMapper;
import com.hk.logistics.web.rest.errors.ExceptionTranslator;
import com.hk.logistics.service.dto.CourierCriteria;
import com.hk.logistics.service.CourierQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
 * Test class for the CourierResource REST controller.
 *
 * @see CourierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HkLogisticsApp.class)
public class CourierResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Long DEFAULT_PARENT_COURIER_ID = 1L;
    private static final Long UPDATED_PARENT_COURIER_ID = 2L;

    private static final Boolean DEFAULT_HK_SHIPPING = false;
    private static final Boolean UPDATED_HK_SHIPPING = true;

    private static final Boolean DEFAULT_VENDOR_SHIPPING = false;
    private static final Boolean UPDATED_VENDOR_SHIPPING = true;

    private static final Boolean DEFAULT_REVERSE_PICKUP = false;
    private static final Boolean UPDATED_REVERSE_PICKUP = true;

    @Autowired
    private CourierRepository courierRepository;


    @Autowired
    private CourierMapper courierMapper;
    

    @Autowired
    private CourierService courierService;

    /**
     * This repository is mocked in the com.hk.logistics.repository.search test package.
     *
     * @see com.hk.logistics.repository.search.CourierSearchRepositoryMockConfiguration
     */
    @Autowired
    private CourierSearchRepository mockCourierSearchRepository;

    @Autowired
    private CourierQueryService courierQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCourierMockMvc;

    private Courier courier;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourierResource courierResource = new CourierResource(courierService, courierQueryService);
        this.restCourierMockMvc = MockMvcBuilders.standaloneSetup(courierResource)
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
    public static Courier createEntity(EntityManager em) {
        Courier courier = new Courier()
            .name(DEFAULT_NAME)
            .shortCode(DEFAULT_SHORT_CODE)
            .active(DEFAULT_ACTIVE)
            .parentCourierId(DEFAULT_PARENT_COURIER_ID)
            .hkShipping(DEFAULT_HK_SHIPPING)
            .vendorShipping(DEFAULT_VENDOR_SHIPPING)
            .reversePickup(DEFAULT_REVERSE_PICKUP);
        return courier;
    }

    @Before
    public void initTest() {
        courier = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourier() throws Exception {
        int databaseSizeBeforeCreate = courierRepository.findAll().size();

        // Create the Courier
        CourierDTO courierDTO = courierMapper.toDto(courier);
        restCourierMockMvc.perform(post("/api/couriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierDTO)))
            .andExpect(status().isCreated());

        // Validate the Courier in the database
        List<Courier> courierList = courierRepository.findAll();
        assertThat(courierList).hasSize(databaseSizeBeforeCreate + 1);
        Courier testCourier = courierList.get(courierList.size() - 1);
        assertThat(testCourier.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCourier.getShortCode()).isEqualTo(DEFAULT_SHORT_CODE);
        assertThat(testCourier.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testCourier.getParentCourierId()).isEqualTo(DEFAULT_PARENT_COURIER_ID);
        assertThat(testCourier.isHkShipping()).isEqualTo(DEFAULT_HK_SHIPPING);
        assertThat(testCourier.isVendorShipping()).isEqualTo(DEFAULT_VENDOR_SHIPPING);
        assertThat(testCourier.isReversePickup()).isEqualTo(DEFAULT_REVERSE_PICKUP);

        // Validate the Courier in Elasticsearch
        verify(mockCourierSearchRepository, times(1)).save(testCourier);
    }

    @Test
    @Transactional
    public void createCourierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courierRepository.findAll().size();

        // Create the Courier with an existing ID
        courier.setId(1L);
        CourierDTO courierDTO = courierMapper.toDto(courier);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourierMockMvc.perform(post("/api/couriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Courier in the database
        List<Courier> courierList = courierRepository.findAll();
        assertThat(courierList).hasSize(databaseSizeBeforeCreate);

        // Validate the Courier in Elasticsearch
        verify(mockCourierSearchRepository, times(0)).save(courier);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = courierRepository.findAll().size();
        // set the field null
        courier.setName(null);

        // Create the Courier, which fails.
        CourierDTO courierDTO = courierMapper.toDto(courier);

        restCourierMockMvc.perform(post("/api/couriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierDTO)))
            .andExpect(status().isBadRequest());

        List<Courier> courierList = courierRepository.findAll();
        assertThat(courierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShortCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = courierRepository.findAll().size();
        // set the field null
        courier.setShortCode(null);

        // Create the Courier, which fails.
        CourierDTO courierDTO = courierMapper.toDto(courier);

        restCourierMockMvc.perform(post("/api/couriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierDTO)))
            .andExpect(status().isBadRequest());

        List<Courier> courierList = courierRepository.findAll();
        assertThat(courierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = courierRepository.findAll().size();
        // set the field null
        courier.setActive(null);

        // Create the Courier, which fails.
        CourierDTO courierDTO = courierMapper.toDto(courier);

        restCourierMockMvc.perform(post("/api/couriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierDTO)))
            .andExpect(status().isBadRequest());

        List<Courier> courierList = courierRepository.findAll();
        assertThat(courierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCouriers() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList
        restCourierMockMvc.perform(get("/api/couriers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courier.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shortCode").value(hasItem(DEFAULT_SHORT_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].parentCourierId").value(hasItem(DEFAULT_PARENT_COURIER_ID.intValue())))
            .andExpect(jsonPath("$.[*].hkShipping").value(hasItem(DEFAULT_HK_SHIPPING.booleanValue())))
            .andExpect(jsonPath("$.[*].vendorShipping").value(hasItem(DEFAULT_VENDOR_SHIPPING.booleanValue())))
            .andExpect(jsonPath("$.[*].reversePickup").value(hasItem(DEFAULT_REVERSE_PICKUP.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getCourier() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get the courier
        restCourierMockMvc.perform(get("/api/couriers/{id}", courier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courier.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shortCode").value(DEFAULT_SHORT_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.parentCourierId").value(DEFAULT_PARENT_COURIER_ID.intValue()))
            .andExpect(jsonPath("$.hkShipping").value(DEFAULT_HK_SHIPPING.booleanValue()))
            .andExpect(jsonPath("$.vendorShipping").value(DEFAULT_VENDOR_SHIPPING.booleanValue()))
            .andExpect(jsonPath("$.reversePickup").value(DEFAULT_REVERSE_PICKUP.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllCouriersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where name equals to DEFAULT_NAME
        defaultCourierShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the courierList where name equals to UPDATED_NAME
        defaultCourierShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCouriersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCourierShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the courierList where name equals to UPDATED_NAME
        defaultCourierShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCouriersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where name is not null
        defaultCourierShouldBeFound("name.specified=true");

        // Get all the courierList where name is null
        defaultCourierShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCouriersByShortCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where shortCode equals to DEFAULT_SHORT_CODE
        defaultCourierShouldBeFound("shortCode.equals=" + DEFAULT_SHORT_CODE);

        // Get all the courierList where shortCode equals to UPDATED_SHORT_CODE
        defaultCourierShouldNotBeFound("shortCode.equals=" + UPDATED_SHORT_CODE);
    }

    @Test
    @Transactional
    public void getAllCouriersByShortCodeIsInShouldWork() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where shortCode in DEFAULT_SHORT_CODE or UPDATED_SHORT_CODE
        defaultCourierShouldBeFound("shortCode.in=" + DEFAULT_SHORT_CODE + "," + UPDATED_SHORT_CODE);

        // Get all the courierList where shortCode equals to UPDATED_SHORT_CODE
        defaultCourierShouldNotBeFound("shortCode.in=" + UPDATED_SHORT_CODE);
    }

    @Test
    @Transactional
    public void getAllCouriersByShortCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where shortCode is not null
        defaultCourierShouldBeFound("shortCode.specified=true");

        // Get all the courierList where shortCode is null
        defaultCourierShouldNotBeFound("shortCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllCouriersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where active equals to DEFAULT_ACTIVE
        defaultCourierShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the courierList where active equals to UPDATED_ACTIVE
        defaultCourierShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCouriersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCourierShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the courierList where active equals to UPDATED_ACTIVE
        defaultCourierShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCouriersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where active is not null
        defaultCourierShouldBeFound("active.specified=true");

        // Get all the courierList where active is null
        defaultCourierShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCouriersByParentCourierIdIsEqualToSomething() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where parentCourierId equals to DEFAULT_PARENT_COURIER_ID
        defaultCourierShouldBeFound("parentCourierId.equals=" + DEFAULT_PARENT_COURIER_ID);

        // Get all the courierList where parentCourierId equals to UPDATED_PARENT_COURIER_ID
        defaultCourierShouldNotBeFound("parentCourierId.equals=" + UPDATED_PARENT_COURIER_ID);
    }

    @Test
    @Transactional
    public void getAllCouriersByParentCourierIdIsInShouldWork() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where parentCourierId in DEFAULT_PARENT_COURIER_ID or UPDATED_PARENT_COURIER_ID
        defaultCourierShouldBeFound("parentCourierId.in=" + DEFAULT_PARENT_COURIER_ID + "," + UPDATED_PARENT_COURIER_ID);

        // Get all the courierList where parentCourierId equals to UPDATED_PARENT_COURIER_ID
        defaultCourierShouldNotBeFound("parentCourierId.in=" + UPDATED_PARENT_COURIER_ID);
    }

    @Test
    @Transactional
    public void getAllCouriersByParentCourierIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where parentCourierId is not null
        defaultCourierShouldBeFound("parentCourierId.specified=true");

        // Get all the courierList where parentCourierId is null
        defaultCourierShouldNotBeFound("parentCourierId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCouriersByParentCourierIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where parentCourierId greater than or equals to DEFAULT_PARENT_COURIER_ID
        defaultCourierShouldBeFound("parentCourierId.greaterOrEqualThan=" + DEFAULT_PARENT_COURIER_ID);

        // Get all the courierList where parentCourierId greater than or equals to UPDATED_PARENT_COURIER_ID
        defaultCourierShouldNotBeFound("parentCourierId.greaterOrEqualThan=" + UPDATED_PARENT_COURIER_ID);
    }

    @Test
    @Transactional
    public void getAllCouriersByParentCourierIdIsLessThanSomething() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where parentCourierId less than or equals to DEFAULT_PARENT_COURIER_ID
        defaultCourierShouldNotBeFound("parentCourierId.lessThan=" + DEFAULT_PARENT_COURIER_ID);

        // Get all the courierList where parentCourierId less than or equals to UPDATED_PARENT_COURIER_ID
        defaultCourierShouldBeFound("parentCourierId.lessThan=" + UPDATED_PARENT_COURIER_ID);
    }


    @Test
    @Transactional
    public void getAllCouriersByHkShippingIsEqualToSomething() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where hkShipping equals to DEFAULT_HK_SHIPPING
        defaultCourierShouldBeFound("hkShipping.equals=" + DEFAULT_HK_SHIPPING);

        // Get all the courierList where hkShipping equals to UPDATED_HK_SHIPPING
        defaultCourierShouldNotBeFound("hkShipping.equals=" + UPDATED_HK_SHIPPING);
    }

    @Test
    @Transactional
    public void getAllCouriersByHkShippingIsInShouldWork() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where hkShipping in DEFAULT_HK_SHIPPING or UPDATED_HK_SHIPPING
        defaultCourierShouldBeFound("hkShipping.in=" + DEFAULT_HK_SHIPPING + "," + UPDATED_HK_SHIPPING);

        // Get all the courierList where hkShipping equals to UPDATED_HK_SHIPPING
        defaultCourierShouldNotBeFound("hkShipping.in=" + UPDATED_HK_SHIPPING);
    }

    @Test
    @Transactional
    public void getAllCouriersByHkShippingIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where hkShipping is not null
        defaultCourierShouldBeFound("hkShipping.specified=true");

        // Get all the courierList where hkShipping is null
        defaultCourierShouldNotBeFound("hkShipping.specified=false");
    }

    @Test
    @Transactional
    public void getAllCouriersByVendorShippingIsEqualToSomething() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where vendorShipping equals to DEFAULT_VENDOR_SHIPPING
        defaultCourierShouldBeFound("vendorShipping.equals=" + DEFAULT_VENDOR_SHIPPING);

        // Get all the courierList where vendorShipping equals to UPDATED_VENDOR_SHIPPING
        defaultCourierShouldNotBeFound("vendorShipping.equals=" + UPDATED_VENDOR_SHIPPING);
    }

    @Test
    @Transactional
    public void getAllCouriersByVendorShippingIsInShouldWork() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where vendorShipping in DEFAULT_VENDOR_SHIPPING or UPDATED_VENDOR_SHIPPING
        defaultCourierShouldBeFound("vendorShipping.in=" + DEFAULT_VENDOR_SHIPPING + "," + UPDATED_VENDOR_SHIPPING);

        // Get all the courierList where vendorShipping equals to UPDATED_VENDOR_SHIPPING
        defaultCourierShouldNotBeFound("vendorShipping.in=" + UPDATED_VENDOR_SHIPPING);
    }

    @Test
    @Transactional
    public void getAllCouriersByVendorShippingIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where vendorShipping is not null
        defaultCourierShouldBeFound("vendorShipping.specified=true");

        // Get all the courierList where vendorShipping is null
        defaultCourierShouldNotBeFound("vendorShipping.specified=false");
    }

    @Test
    @Transactional
    public void getAllCouriersByReversePickupIsEqualToSomething() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where reversePickup equals to DEFAULT_REVERSE_PICKUP
        defaultCourierShouldBeFound("reversePickup.equals=" + DEFAULT_REVERSE_PICKUP);

        // Get all the courierList where reversePickup equals to UPDATED_REVERSE_PICKUP
        defaultCourierShouldNotBeFound("reversePickup.equals=" + UPDATED_REVERSE_PICKUP);
    }

    @Test
    @Transactional
    public void getAllCouriersByReversePickupIsInShouldWork() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where reversePickup in DEFAULT_REVERSE_PICKUP or UPDATED_REVERSE_PICKUP
        defaultCourierShouldBeFound("reversePickup.in=" + DEFAULT_REVERSE_PICKUP + "," + UPDATED_REVERSE_PICKUP);

        // Get all the courierList where reversePickup equals to UPDATED_REVERSE_PICKUP
        defaultCourierShouldNotBeFound("reversePickup.in=" + UPDATED_REVERSE_PICKUP);
    }

    @Test
    @Transactional
    public void getAllCouriersByReversePickupIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the courierList where reversePickup is not null
        defaultCourierShouldBeFound("reversePickup.specified=true");

        // Get all the courierList where reversePickup is null
        defaultCourierShouldNotBeFound("reversePickup.specified=false");
    }

    @Test
    @Transactional
    public void getAllCouriersByVendorWHCourierMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        VendorWHCourierMapping vendorWHCourierMapping = VendorWHCourierMappingResourceIntTest.createEntity(em);
        em.persist(vendorWHCourierMapping);
        em.flush();
        courier.addVendorWHCourierMapping(vendorWHCourierMapping);
        courierRepository.saveAndFlush(courier);
        Long vendorWHCourierMappingId = vendorWHCourierMapping.getId();

        // Get all the courierList where vendorWHCourierMapping equals to vendorWHCourierMappingId
        defaultCourierShouldBeFound("vendorWHCourierMappingId.equals=" + vendorWHCourierMappingId);

        // Get all the courierList where vendorWHCourierMapping equals to vendorWHCourierMappingId + 1
        defaultCourierShouldNotBeFound("vendorWHCourierMappingId.equals=" + (vendorWHCourierMappingId + 1));
    }


    @Test
    @Transactional
    public void getAllCouriersByCourierChannelIsEqualToSomething() throws Exception {
        // Initialize the database
        CourierChannel courierChannel = CourierChannelResourceIntTest.createEntity(em);
        em.persist(courierChannel);
        em.flush();
        courier.addCourierChannel(courierChannel);
        courierRepository.saveAndFlush(courier);
        Long courierChannelId = courierChannel.getId();

        // Get all the courierList where courierChannel equals to courierChannelId
        defaultCourierShouldBeFound("courierChannelId.equals=" + courierChannelId);

        // Get all the courierList where courierChannel equals to courierChannelId + 1
        defaultCourierShouldNotBeFound("courierChannelId.equals=" + (courierChannelId + 1));
    }


    @Test
    @Transactional
    public void getAllCouriersByCourierGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        CourierGroup courierGroup = CourierGroupResourceIntTest.createEntity(em);
        em.persist(courierGroup);
        em.flush();
        courier.setCourierGroup(courierGroup);
        courierRepository.saveAndFlush(courier);
        Long courierGroupId = courierGroup.getId();

        // Get all the courierList where courierGroup equals to courierGroupId
        defaultCourierShouldBeFound("courierGroupId.equals=" + courierGroupId);

        // Get all the courierList where courierGroup equals to courierGroupId + 1
        defaultCourierShouldNotBeFound("courierGroupId.equals=" + (courierGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCourierShouldBeFound(String filter) throws Exception {
        restCourierMockMvc.perform(get("/api/couriers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courier.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shortCode").value(hasItem(DEFAULT_SHORT_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].parentCourierId").value(hasItem(DEFAULT_PARENT_COURIER_ID.intValue())))
            .andExpect(jsonPath("$.[*].hkShipping").value(hasItem(DEFAULT_HK_SHIPPING.booleanValue())))
            .andExpect(jsonPath("$.[*].vendorShipping").value(hasItem(DEFAULT_VENDOR_SHIPPING.booleanValue())))
            .andExpect(jsonPath("$.[*].reversePickup").value(hasItem(DEFAULT_REVERSE_PICKUP.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCourierShouldNotBeFound(String filter) throws Exception {
        restCourierMockMvc.perform(get("/api/couriers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingCourier() throws Exception {
        // Get the courier
        restCourierMockMvc.perform(get("/api/couriers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourier() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        int databaseSizeBeforeUpdate = courierRepository.findAll().size();

        // Update the courier
        Courier updatedCourier = courierRepository.findById(courier.getId()).get();
        // Disconnect from session so that the updates on updatedCourier are not directly saved in db
        em.detach(updatedCourier);
        updatedCourier
            .name(UPDATED_NAME)
            .shortCode(UPDATED_SHORT_CODE)
            .active(UPDATED_ACTIVE)
            .parentCourierId(UPDATED_PARENT_COURIER_ID)
            .hkShipping(UPDATED_HK_SHIPPING)
            .vendorShipping(UPDATED_VENDOR_SHIPPING)
            .reversePickup(UPDATED_REVERSE_PICKUP);
        CourierDTO courierDTO = courierMapper.toDto(updatedCourier);

        restCourierMockMvc.perform(put("/api/couriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierDTO)))
            .andExpect(status().isOk());

        // Validate the Courier in the database
        List<Courier> courierList = courierRepository.findAll();
        assertThat(courierList).hasSize(databaseSizeBeforeUpdate);
        Courier testCourier = courierList.get(courierList.size() - 1);
        assertThat(testCourier.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCourier.getShortCode()).isEqualTo(UPDATED_SHORT_CODE);
        assertThat(testCourier.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testCourier.getParentCourierId()).isEqualTo(UPDATED_PARENT_COURIER_ID);
        assertThat(testCourier.isHkShipping()).isEqualTo(UPDATED_HK_SHIPPING);
        assertThat(testCourier.isVendorShipping()).isEqualTo(UPDATED_VENDOR_SHIPPING);
        assertThat(testCourier.isReversePickup()).isEqualTo(UPDATED_REVERSE_PICKUP);

        // Validate the Courier in Elasticsearch
        verify(mockCourierSearchRepository, times(1)).save(testCourier);
    }

    @Test
    @Transactional
    public void updateNonExistingCourier() throws Exception {
        int databaseSizeBeforeUpdate = courierRepository.findAll().size();

        // Create the Courier
        CourierDTO courierDTO = courierMapper.toDto(courier);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCourierMockMvc.perform(put("/api/couriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Courier in the database
        List<Courier> courierList = courierRepository.findAll();
        assertThat(courierList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Courier in Elasticsearch
        verify(mockCourierSearchRepository, times(0)).save(courier);
    }

    @Test
    @Transactional
    public void deleteCourier() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        int databaseSizeBeforeDelete = courierRepository.findAll().size();

        // Get the courier
        restCourierMockMvc.perform(delete("/api/couriers/{id}", courier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Courier> courierList = courierRepository.findAll();
        assertThat(courierList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Courier in Elasticsearch
        verify(mockCourierSearchRepository, times(1)).deleteById(courier.getId());
    }

    @Test
    @Transactional
    public void searchCourier() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);
        when(mockCourierSearchRepository.search(queryStringQuery("id:" + courier.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(courier), PageRequest.of(0, 1), 1));
        // Search the courier
        restCourierMockMvc.perform(get("/api/_search/couriers?query=id:" + courier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courier.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shortCode").value(hasItem(DEFAULT_SHORT_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].parentCourierId").value(hasItem(DEFAULT_PARENT_COURIER_ID.intValue())))
            .andExpect(jsonPath("$.[*].hkShipping").value(hasItem(DEFAULT_HK_SHIPPING.booleanValue())))
            .andExpect(jsonPath("$.[*].vendorShipping").value(hasItem(DEFAULT_VENDOR_SHIPPING.booleanValue())))
            .andExpect(jsonPath("$.[*].reversePickup").value(hasItem(DEFAULT_REVERSE_PICKUP.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Courier.class);
        Courier courier1 = new Courier();
        courier1.setId(1L);
        Courier courier2 = new Courier();
        courier2.setId(courier1.getId());
        assertThat(courier1).isEqualTo(courier2);
        courier2.setId(2L);
        assertThat(courier1).isNotEqualTo(courier2);
        courier1.setId(null);
        assertThat(courier1).isNotEqualTo(courier2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourierDTO.class);
        CourierDTO courierDTO1 = new CourierDTO();
        courierDTO1.setId(1L);
        CourierDTO courierDTO2 = new CourierDTO();
        assertThat(courierDTO1).isNotEqualTo(courierDTO2);
        courierDTO2.setId(courierDTO1.getId());
        assertThat(courierDTO1).isEqualTo(courierDTO2);
        courierDTO2.setId(2L);
        assertThat(courierDTO1).isNotEqualTo(courierDTO2);
        courierDTO1.setId(null);
        assertThat(courierDTO1).isNotEqualTo(courierDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(courierMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(courierMapper.fromId(null)).isNull();
    }
}
