package com.hk.logistics.web.rest;

import com.hk.logistics.HkLogisticsApp;

import com.hk.logistics.domain.ShipmentServiceType;
import com.hk.logistics.repository.ShipmentServiceTypeRepository;
import com.hk.logistics.repository.search.ShipmentServiceTypeSearchRepository;
import com.hk.logistics.service.ShipmentServiceTypeService;
import com.hk.logistics.service.dto.ShipmentServiceTypeDTO;
import com.hk.logistics.service.mapper.ShipmentServiceTypeMapper;
import com.hk.logistics.web.rest.errors.ExceptionTranslator;
import com.hk.logistics.service.dto.ShipmentServiceTypeCriteria;
import com.hk.logistics.service.ShipmentServiceTypeQueryService;

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
 * Test class for the ShipmentServiceTypeResource REST controller.
 *
 * @see ShipmentServiceTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HkLogisticsApp.class)
public class ShipmentServiceTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ShipmentServiceTypeRepository shipmentServiceTypeRepository;


    @Autowired
    private ShipmentServiceTypeMapper shipmentServiceTypeMapper;
    

    @Autowired
    private ShipmentServiceTypeService shipmentServiceTypeService;

    /**
     * This repository is mocked in the com.hk.logistics.repository.search test package.
     *
     * @see com.hk.logistics.repository.search.ShipmentServiceTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ShipmentServiceTypeSearchRepository mockShipmentServiceTypeSearchRepository;

    @Autowired
    private ShipmentServiceTypeQueryService shipmentServiceTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShipmentServiceTypeMockMvc;

    private ShipmentServiceType shipmentServiceType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShipmentServiceTypeResource shipmentServiceTypeResource = new ShipmentServiceTypeResource(shipmentServiceTypeService, shipmentServiceTypeQueryService);
        this.restShipmentServiceTypeMockMvc = MockMvcBuilders.standaloneSetup(shipmentServiceTypeResource)
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
    public static ShipmentServiceType createEntity(EntityManager em) {
        ShipmentServiceType shipmentServiceType = new ShipmentServiceType()
            .name(DEFAULT_NAME);
        return shipmentServiceType;
    }

    @Before
    public void initTest() {
        shipmentServiceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipmentServiceType() throws Exception {
        int databaseSizeBeforeCreate = shipmentServiceTypeRepository.findAll().size();

        // Create the ShipmentServiceType
        ShipmentServiceTypeDTO shipmentServiceTypeDTO = shipmentServiceTypeMapper.toDto(shipmentServiceType);
        restShipmentServiceTypeMockMvc.perform(post("/api/shipment-service-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentServiceTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentServiceType in the database
        List<ShipmentServiceType> shipmentServiceTypeList = shipmentServiceTypeRepository.findAll();
        assertThat(shipmentServiceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentServiceType testShipmentServiceType = shipmentServiceTypeList.get(shipmentServiceTypeList.size() - 1);
        assertThat(testShipmentServiceType.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the ShipmentServiceType in Elasticsearch
        verify(mockShipmentServiceTypeSearchRepository, times(1)).save(testShipmentServiceType);
    }

    @Test
    @Transactional
    public void createShipmentServiceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentServiceTypeRepository.findAll().size();

        // Create the ShipmentServiceType with an existing ID
        shipmentServiceType.setId(1L);
        ShipmentServiceTypeDTO shipmentServiceTypeDTO = shipmentServiceTypeMapper.toDto(shipmentServiceType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentServiceTypeMockMvc.perform(post("/api/shipment-service-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentServiceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentServiceType in the database
        List<ShipmentServiceType> shipmentServiceTypeList = shipmentServiceTypeRepository.findAll();
        assertThat(shipmentServiceTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ShipmentServiceType in Elasticsearch
        verify(mockShipmentServiceTypeSearchRepository, times(0)).save(shipmentServiceType);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentServiceTypeRepository.findAll().size();
        // set the field null
        shipmentServiceType.setName(null);

        // Create the ShipmentServiceType, which fails.
        ShipmentServiceTypeDTO shipmentServiceTypeDTO = shipmentServiceTypeMapper.toDto(shipmentServiceType);

        restShipmentServiceTypeMockMvc.perform(post("/api/shipment-service-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentServiceTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ShipmentServiceType> shipmentServiceTypeList = shipmentServiceTypeRepository.findAll();
        assertThat(shipmentServiceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShipmentServiceTypes() throws Exception {
        // Initialize the database
        shipmentServiceTypeRepository.saveAndFlush(shipmentServiceType);

        // Get all the shipmentServiceTypeList
        restShipmentServiceTypeMockMvc.perform(get("/api/shipment-service-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentServiceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getShipmentServiceType() throws Exception {
        // Initialize the database
        shipmentServiceTypeRepository.saveAndFlush(shipmentServiceType);

        // Get the shipmentServiceType
        restShipmentServiceTypeMockMvc.perform(get("/api/shipment-service-types/{id}", shipmentServiceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentServiceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllShipmentServiceTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        shipmentServiceTypeRepository.saveAndFlush(shipmentServiceType);

        // Get all the shipmentServiceTypeList where name equals to DEFAULT_NAME
        defaultShipmentServiceTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the shipmentServiceTypeList where name equals to UPDATED_NAME
        defaultShipmentServiceTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllShipmentServiceTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        shipmentServiceTypeRepository.saveAndFlush(shipmentServiceType);

        // Get all the shipmentServiceTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultShipmentServiceTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the shipmentServiceTypeList where name equals to UPDATED_NAME
        defaultShipmentServiceTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllShipmentServiceTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        shipmentServiceTypeRepository.saveAndFlush(shipmentServiceType);

        // Get all the shipmentServiceTypeList where name is not null
        defaultShipmentServiceTypeShouldBeFound("name.specified=true");

        // Get all the shipmentServiceTypeList where name is null
        defaultShipmentServiceTypeShouldNotBeFound("name.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultShipmentServiceTypeShouldBeFound(String filter) throws Exception {
        restShipmentServiceTypeMockMvc.perform(get("/api/shipment-service-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentServiceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultShipmentServiceTypeShouldNotBeFound(String filter) throws Exception {
        restShipmentServiceTypeMockMvc.perform(get("/api/shipment-service-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingShipmentServiceType() throws Exception {
        // Get the shipmentServiceType
        restShipmentServiceTypeMockMvc.perform(get("/api/shipment-service-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentServiceType() throws Exception {
        // Initialize the database
        shipmentServiceTypeRepository.saveAndFlush(shipmentServiceType);

        int databaseSizeBeforeUpdate = shipmentServiceTypeRepository.findAll().size();

        // Update the shipmentServiceType
        ShipmentServiceType updatedShipmentServiceType = shipmentServiceTypeRepository.findById(shipmentServiceType.getId()).get();
        // Disconnect from session so that the updates on updatedShipmentServiceType are not directly saved in db
        em.detach(updatedShipmentServiceType);
        updatedShipmentServiceType
            .name(UPDATED_NAME);
        ShipmentServiceTypeDTO shipmentServiceTypeDTO = shipmentServiceTypeMapper.toDto(updatedShipmentServiceType);

        restShipmentServiceTypeMockMvc.perform(put("/api/shipment-service-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentServiceTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ShipmentServiceType in the database
        List<ShipmentServiceType> shipmentServiceTypeList = shipmentServiceTypeRepository.findAll();
        assertThat(shipmentServiceTypeList).hasSize(databaseSizeBeforeUpdate);
        ShipmentServiceType testShipmentServiceType = shipmentServiceTypeList.get(shipmentServiceTypeList.size() - 1);
        assertThat(testShipmentServiceType.getName()).isEqualTo(UPDATED_NAME);

        // Validate the ShipmentServiceType in Elasticsearch
        verify(mockShipmentServiceTypeSearchRepository, times(1)).save(testShipmentServiceType);
    }

    @Test
    @Transactional
    public void updateNonExistingShipmentServiceType() throws Exception {
        int databaseSizeBeforeUpdate = shipmentServiceTypeRepository.findAll().size();

        // Create the ShipmentServiceType
        ShipmentServiceTypeDTO shipmentServiceTypeDTO = shipmentServiceTypeMapper.toDto(shipmentServiceType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShipmentServiceTypeMockMvc.perform(put("/api/shipment-service-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentServiceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentServiceType in the database
        List<ShipmentServiceType> shipmentServiceTypeList = shipmentServiceTypeRepository.findAll();
        assertThat(shipmentServiceTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ShipmentServiceType in Elasticsearch
        verify(mockShipmentServiceTypeSearchRepository, times(0)).save(shipmentServiceType);
    }

    @Test
    @Transactional
    public void deleteShipmentServiceType() throws Exception {
        // Initialize the database
        shipmentServiceTypeRepository.saveAndFlush(shipmentServiceType);

        int databaseSizeBeforeDelete = shipmentServiceTypeRepository.findAll().size();

        // Get the shipmentServiceType
        restShipmentServiceTypeMockMvc.perform(delete("/api/shipment-service-types/{id}", shipmentServiceType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentServiceType> shipmentServiceTypeList = shipmentServiceTypeRepository.findAll();
        assertThat(shipmentServiceTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ShipmentServiceType in Elasticsearch
        verify(mockShipmentServiceTypeSearchRepository, times(1)).deleteById(shipmentServiceType.getId());
    }

    @Test
    @Transactional
    public void searchShipmentServiceType() throws Exception {
        // Initialize the database
        shipmentServiceTypeRepository.saveAndFlush(shipmentServiceType);
        when(mockShipmentServiceTypeSearchRepository.search(queryStringQuery("id:" + shipmentServiceType.getId())))
            .thenReturn(Collections.singletonList(shipmentServiceType));
        // Search the shipmentServiceType
        restShipmentServiceTypeMockMvc.perform(get("/api/_search/shipment-service-types?query=id:" + shipmentServiceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentServiceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentServiceType.class);
        ShipmentServiceType shipmentServiceType1 = new ShipmentServiceType();
        shipmentServiceType1.setId(1L);
        ShipmentServiceType shipmentServiceType2 = new ShipmentServiceType();
        shipmentServiceType2.setId(shipmentServiceType1.getId());
        assertThat(shipmentServiceType1).isEqualTo(shipmentServiceType2);
        shipmentServiceType2.setId(2L);
        assertThat(shipmentServiceType1).isNotEqualTo(shipmentServiceType2);
        shipmentServiceType1.setId(null);
        assertThat(shipmentServiceType1).isNotEqualTo(shipmentServiceType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentServiceTypeDTO.class);
        ShipmentServiceTypeDTO shipmentServiceTypeDTO1 = new ShipmentServiceTypeDTO();
        shipmentServiceTypeDTO1.setId(1L);
        ShipmentServiceTypeDTO shipmentServiceTypeDTO2 = new ShipmentServiceTypeDTO();
        assertThat(shipmentServiceTypeDTO1).isNotEqualTo(shipmentServiceTypeDTO2);
        shipmentServiceTypeDTO2.setId(shipmentServiceTypeDTO1.getId());
        assertThat(shipmentServiceTypeDTO1).isEqualTo(shipmentServiceTypeDTO2);
        shipmentServiceTypeDTO2.setId(2L);
        assertThat(shipmentServiceTypeDTO1).isNotEqualTo(shipmentServiceTypeDTO2);
        shipmentServiceTypeDTO1.setId(null);
        assertThat(shipmentServiceTypeDTO1).isNotEqualTo(shipmentServiceTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shipmentServiceTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shipmentServiceTypeMapper.fromId(null)).isNull();
    }
}
