package com.hk.logistics.web.rest;

import com.hk.logistics.HkLogisticsApp;

import com.hk.logistics.domain.CourierGroup;
import com.hk.logistics.domain.Courier;
import com.hk.logistics.repository.CourierGroupRepository;
import com.hk.logistics.repository.search.CourierGroupSearchRepository;
import com.hk.logistics.service.CourierGroupService;
import com.hk.logistics.service.dto.CourierGroupDTO;
import com.hk.logistics.service.mapper.CourierGroupMapper;
import com.hk.logistics.web.rest.errors.ExceptionTranslator;
import com.hk.logistics.service.dto.CourierGroupCriteria;
import com.hk.logistics.service.CourierGroupQueryService;

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
 * Test class for the CourierGroupResource REST controller.
 *
 * @see CourierGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HkLogisticsApp.class)
public class CourierGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CourierGroupRepository courierGroupRepository;


    @Autowired
    private CourierGroupMapper courierGroupMapper;
    

    @Autowired
    private CourierGroupService courierGroupService;

    /**
     * This repository is mocked in the com.hk.logistics.repository.search test package.
     *
     * @see com.hk.logistics.repository.search.CourierGroupSearchRepositoryMockConfiguration
     */
    @Autowired
    private CourierGroupSearchRepository mockCourierGroupSearchRepository;

    @Autowired
    private CourierGroupQueryService courierGroupQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCourierGroupMockMvc;

    private CourierGroup courierGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourierGroupResource courierGroupResource = new CourierGroupResource(courierGroupService, courierGroupQueryService);
        this.restCourierGroupMockMvc = MockMvcBuilders.standaloneSetup(courierGroupResource)
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
    public static CourierGroup createEntity(EntityManager em) {
        CourierGroup courierGroup = new CourierGroup()
            .name(DEFAULT_NAME);
        return courierGroup;
    }

    @Before
    public void initTest() {
        courierGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourierGroup() throws Exception {
        int databaseSizeBeforeCreate = courierGroupRepository.findAll().size();

        // Create the CourierGroup
        CourierGroupDTO courierGroupDTO = courierGroupMapper.toDto(courierGroup);
        restCourierGroupMockMvc.perform(post("/api/courier-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the CourierGroup in the database
        List<CourierGroup> courierGroupList = courierGroupRepository.findAll();
        assertThat(courierGroupList).hasSize(databaseSizeBeforeCreate + 1);
        CourierGroup testCourierGroup = courierGroupList.get(courierGroupList.size() - 1);
        assertThat(testCourierGroup.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the CourierGroup in Elasticsearch
        verify(mockCourierGroupSearchRepository, times(1)).save(testCourierGroup);
    }

    @Test
    @Transactional
    public void createCourierGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courierGroupRepository.findAll().size();

        // Create the CourierGroup with an existing ID
        courierGroup.setId(1L);
        CourierGroupDTO courierGroupDTO = courierGroupMapper.toDto(courierGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourierGroupMockMvc.perform(post("/api/courier-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourierGroup in the database
        List<CourierGroup> courierGroupList = courierGroupRepository.findAll();
        assertThat(courierGroupList).hasSize(databaseSizeBeforeCreate);

        // Validate the CourierGroup in Elasticsearch
        verify(mockCourierGroupSearchRepository, times(0)).save(courierGroup);
    }

    @Test
    @Transactional
    public void getAllCourierGroups() throws Exception {
        // Initialize the database
        courierGroupRepository.saveAndFlush(courierGroup);

        // Get all the courierGroupList
        restCourierGroupMockMvc.perform(get("/api/courier-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courierGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getCourierGroup() throws Exception {
        // Initialize the database
        courierGroupRepository.saveAndFlush(courierGroup);

        // Get the courierGroup
        restCourierGroupMockMvc.perform(get("/api/courier-groups/{id}", courierGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courierGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllCourierGroupsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courierGroupRepository.saveAndFlush(courierGroup);

        // Get all the courierGroupList where name equals to DEFAULT_NAME
        defaultCourierGroupShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the courierGroupList where name equals to UPDATED_NAME
        defaultCourierGroupShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCourierGroupsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        courierGroupRepository.saveAndFlush(courierGroup);

        // Get all the courierGroupList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCourierGroupShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the courierGroupList where name equals to UPDATED_NAME
        defaultCourierGroupShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCourierGroupsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courierGroupRepository.saveAndFlush(courierGroup);

        // Get all the courierGroupList where name is not null
        defaultCourierGroupShouldBeFound("name.specified=true");

        // Get all the courierGroupList where name is null
        defaultCourierGroupShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourierGroupsByCourierIsEqualToSomething() throws Exception {
        // Initialize the database
        Courier courier = CourierResourceIntTest.createEntity(em);
        em.persist(courier);
        em.flush();
        courierGroup.addCourier(courier);
        courierGroupRepository.saveAndFlush(courierGroup);
        Long courierId = courier.getId();

        // Get all the courierGroupList where courier equals to courierId
        defaultCourierGroupShouldBeFound("courierId.equals=" + courierId);

        // Get all the courierGroupList where courier equals to courierId + 1
        defaultCourierGroupShouldNotBeFound("courierId.equals=" + (courierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCourierGroupShouldBeFound(String filter) throws Exception {
        restCourierGroupMockMvc.perform(get("/api/courier-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courierGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCourierGroupShouldNotBeFound(String filter) throws Exception {
        restCourierGroupMockMvc.perform(get("/api/courier-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingCourierGroup() throws Exception {
        // Get the courierGroup
        restCourierGroupMockMvc.perform(get("/api/courier-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourierGroup() throws Exception {
        // Initialize the database
        courierGroupRepository.saveAndFlush(courierGroup);

        int databaseSizeBeforeUpdate = courierGroupRepository.findAll().size();

        // Update the courierGroup
        CourierGroup updatedCourierGroup = courierGroupRepository.findById(courierGroup.getId()).get();
        // Disconnect from session so that the updates on updatedCourierGroup are not directly saved in db
        em.detach(updatedCourierGroup);
        updatedCourierGroup
            .name(UPDATED_NAME);
        CourierGroupDTO courierGroupDTO = courierGroupMapper.toDto(updatedCourierGroup);

        restCourierGroupMockMvc.perform(put("/api/courier-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierGroupDTO)))
            .andExpect(status().isOk());

        // Validate the CourierGroup in the database
        List<CourierGroup> courierGroupList = courierGroupRepository.findAll();
        assertThat(courierGroupList).hasSize(databaseSizeBeforeUpdate);
        CourierGroup testCourierGroup = courierGroupList.get(courierGroupList.size() - 1);
        assertThat(testCourierGroup.getName()).isEqualTo(UPDATED_NAME);

        // Validate the CourierGroup in Elasticsearch
        verify(mockCourierGroupSearchRepository, times(1)).save(testCourierGroup);
    }

    @Test
    @Transactional
    public void updateNonExistingCourierGroup() throws Exception {
        int databaseSizeBeforeUpdate = courierGroupRepository.findAll().size();

        // Create the CourierGroup
        CourierGroupDTO courierGroupDTO = courierGroupMapper.toDto(courierGroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCourierGroupMockMvc.perform(put("/api/courier-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourierGroup in the database
        List<CourierGroup> courierGroupList = courierGroupRepository.findAll();
        assertThat(courierGroupList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CourierGroup in Elasticsearch
        verify(mockCourierGroupSearchRepository, times(0)).save(courierGroup);
    }

    @Test
    @Transactional
    public void deleteCourierGroup() throws Exception {
        // Initialize the database
        courierGroupRepository.saveAndFlush(courierGroup);

        int databaseSizeBeforeDelete = courierGroupRepository.findAll().size();

        // Get the courierGroup
        restCourierGroupMockMvc.perform(delete("/api/courier-groups/{id}", courierGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CourierGroup> courierGroupList = courierGroupRepository.findAll();
        assertThat(courierGroupList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CourierGroup in Elasticsearch
        verify(mockCourierGroupSearchRepository, times(1)).deleteById(courierGroup.getId());
    }

    @Test
    @Transactional
    public void searchCourierGroup() throws Exception {
        // Initialize the database
        courierGroupRepository.saveAndFlush(courierGroup);
        when(mockCourierGroupSearchRepository.search(queryStringQuery("id:" + courierGroup.getId())))
            .thenReturn(Collections.singletonList(courierGroup));
        // Search the courierGroup
        restCourierGroupMockMvc.perform(get("/api/_search/courier-groups?query=id:" + courierGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courierGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourierGroup.class);
        CourierGroup courierGroup1 = new CourierGroup();
        courierGroup1.setId(1L);
        CourierGroup courierGroup2 = new CourierGroup();
        courierGroup2.setId(courierGroup1.getId());
        assertThat(courierGroup1).isEqualTo(courierGroup2);
        courierGroup2.setId(2L);
        assertThat(courierGroup1).isNotEqualTo(courierGroup2);
        courierGroup1.setId(null);
        assertThat(courierGroup1).isNotEqualTo(courierGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourierGroupDTO.class);
        CourierGroupDTO courierGroupDTO1 = new CourierGroupDTO();
        courierGroupDTO1.setId(1L);
        CourierGroupDTO courierGroupDTO2 = new CourierGroupDTO();
        assertThat(courierGroupDTO1).isNotEqualTo(courierGroupDTO2);
        courierGroupDTO2.setId(courierGroupDTO1.getId());
        assertThat(courierGroupDTO1).isEqualTo(courierGroupDTO2);
        courierGroupDTO2.setId(2L);
        assertThat(courierGroupDTO1).isNotEqualTo(courierGroupDTO2);
        courierGroupDTO1.setId(null);
        assertThat(courierGroupDTO1).isNotEqualTo(courierGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(courierGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(courierGroupMapper.fromId(null)).isNull();
    }
}
