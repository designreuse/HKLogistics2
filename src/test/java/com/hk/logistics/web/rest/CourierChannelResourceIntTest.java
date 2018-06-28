package com.hk.logistics.web.rest;

import com.hk.logistics.HkLogisticsApp;

import com.hk.logistics.domain.CourierChannel;
import com.hk.logistics.domain.VendorWHCourierMapping;
import com.hk.logistics.domain.Channel;
import com.hk.logistics.domain.Courier;
import com.hk.logistics.repository.CourierChannelRepository;
import com.hk.logistics.repository.search.CourierChannelSearchRepository;
import com.hk.logistics.service.CourierChannelService;
import com.hk.logistics.service.dto.CourierChannelDTO;
import com.hk.logistics.service.mapper.CourierChannelMapper;
import com.hk.logistics.web.rest.errors.ExceptionTranslator;
import com.hk.logistics.service.dto.CourierChannelCriteria;
import com.hk.logistics.service.CourierChannelQueryService;

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
 * Test class for the CourierChannelResource REST controller.
 *
 * @see CourierChannelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HkLogisticsApp.class)
public class CourierChannelResourceIntTest {

    @Autowired
    private CourierChannelRepository courierChannelRepository;


    @Autowired
    private CourierChannelMapper courierChannelMapper;
    

    @Autowired
    private CourierChannelService courierChannelService;

    /**
     * This repository is mocked in the com.hk.logistics.repository.search test package.
     *
     * @see com.hk.logistics.repository.search.CourierChannelSearchRepositoryMockConfiguration
     */
    @Autowired
    private CourierChannelSearchRepository mockCourierChannelSearchRepository;

    @Autowired
    private CourierChannelQueryService courierChannelQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCourierChannelMockMvc;

    private CourierChannel courierChannel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourierChannelResource courierChannelResource = new CourierChannelResource(courierChannelService, courierChannelQueryService);
        this.restCourierChannelMockMvc = MockMvcBuilders.standaloneSetup(courierChannelResource)
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
    public static CourierChannel createEntity(EntityManager em) {
        CourierChannel courierChannel = new CourierChannel();
        return courierChannel;
    }

    @Before
    public void initTest() {
        courierChannel = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourierChannel() throws Exception {
        int databaseSizeBeforeCreate = courierChannelRepository.findAll().size();

        // Create the CourierChannel
        CourierChannelDTO courierChannelDTO = courierChannelMapper.toDto(courierChannel);
        restCourierChannelMockMvc.perform(post("/api/courier-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierChannelDTO)))
            .andExpect(status().isCreated());

        // Validate the CourierChannel in the database
        List<CourierChannel> courierChannelList = courierChannelRepository.findAll();
        assertThat(courierChannelList).hasSize(databaseSizeBeforeCreate + 1);
        CourierChannel testCourierChannel = courierChannelList.get(courierChannelList.size() - 1);

        // Validate the CourierChannel in Elasticsearch
        verify(mockCourierChannelSearchRepository, times(1)).save(testCourierChannel);
    }

    @Test
    @Transactional
    public void createCourierChannelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courierChannelRepository.findAll().size();

        // Create the CourierChannel with an existing ID
        courierChannel.setId(1L);
        CourierChannelDTO courierChannelDTO = courierChannelMapper.toDto(courierChannel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourierChannelMockMvc.perform(post("/api/courier-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierChannelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourierChannel in the database
        List<CourierChannel> courierChannelList = courierChannelRepository.findAll();
        assertThat(courierChannelList).hasSize(databaseSizeBeforeCreate);

        // Validate the CourierChannel in Elasticsearch
        verify(mockCourierChannelSearchRepository, times(0)).save(courierChannel);
    }

    @Test
    @Transactional
    public void getAllCourierChannels() throws Exception {
        // Initialize the database
        courierChannelRepository.saveAndFlush(courierChannel);

        // Get all the courierChannelList
        restCourierChannelMockMvc.perform(get("/api/courier-channels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courierChannel.getId().intValue())));
    }
    

    @Test
    @Transactional
    public void getCourierChannel() throws Exception {
        // Initialize the database
        courierChannelRepository.saveAndFlush(courierChannel);

        // Get the courierChannel
        restCourierChannelMockMvc.perform(get("/api/courier-channels/{id}", courierChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courierChannel.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllCourierChannelsByVendorWHCourierMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        VendorWHCourierMapping vendorWHCourierMapping = VendorWHCourierMappingResourceIntTest.createEntity(em);
        em.persist(vendorWHCourierMapping);
        em.flush();
        courierChannel.addVendorWHCourierMapping(vendorWHCourierMapping);
        courierChannelRepository.saveAndFlush(courierChannel);
        Long vendorWHCourierMappingId = vendorWHCourierMapping.getId();

        // Get all the courierChannelList where vendorWHCourierMapping equals to vendorWHCourierMappingId
        defaultCourierChannelShouldBeFound("vendorWHCourierMappingId.equals=" + vendorWHCourierMappingId);

        // Get all the courierChannelList where vendorWHCourierMapping equals to vendorWHCourierMappingId + 1
        defaultCourierChannelShouldNotBeFound("vendorWHCourierMappingId.equals=" + (vendorWHCourierMappingId + 1));
    }


    @Test
    @Transactional
    public void getAllCourierChannelsByChannelIsEqualToSomething() throws Exception {
        // Initialize the database
        Channel channel = ChannelResourceIntTest.createEntity(em);
        em.persist(channel);
        em.flush();
        courierChannel.setChannel(channel);
        courierChannelRepository.saveAndFlush(courierChannel);
        Long channelId = channel.getId();

        // Get all the courierChannelList where channel equals to channelId
        defaultCourierChannelShouldBeFound("channelId.equals=" + channelId);

        // Get all the courierChannelList where channel equals to channelId + 1
        defaultCourierChannelShouldNotBeFound("channelId.equals=" + (channelId + 1));
    }


    @Test
    @Transactional
    public void getAllCourierChannelsByCourierIsEqualToSomething() throws Exception {
        // Initialize the database
        Courier courier = CourierResourceIntTest.createEntity(em);
        em.persist(courier);
        em.flush();
        courierChannel.setCourier(courier);
        courierChannelRepository.saveAndFlush(courierChannel);
        Long courierId = courier.getId();

        // Get all the courierChannelList where courier equals to courierId
        defaultCourierChannelShouldBeFound("courierId.equals=" + courierId);

        // Get all the courierChannelList where courier equals to courierId + 1
        defaultCourierChannelShouldNotBeFound("courierId.equals=" + (courierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCourierChannelShouldBeFound(String filter) throws Exception {
        restCourierChannelMockMvc.perform(get("/api/courier-channels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courierChannel.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCourierChannelShouldNotBeFound(String filter) throws Exception {
        restCourierChannelMockMvc.perform(get("/api/courier-channels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingCourierChannel() throws Exception {
        // Get the courierChannel
        restCourierChannelMockMvc.perform(get("/api/courier-channels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourierChannel() throws Exception {
        // Initialize the database
        courierChannelRepository.saveAndFlush(courierChannel);

        int databaseSizeBeforeUpdate = courierChannelRepository.findAll().size();

        // Update the courierChannel
        CourierChannel updatedCourierChannel = courierChannelRepository.findById(courierChannel.getId()).get();
        // Disconnect from session so that the updates on updatedCourierChannel are not directly saved in db
        em.detach(updatedCourierChannel);
        CourierChannelDTO courierChannelDTO = courierChannelMapper.toDto(updatedCourierChannel);

        restCourierChannelMockMvc.perform(put("/api/courier-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierChannelDTO)))
            .andExpect(status().isOk());

        // Validate the CourierChannel in the database
        List<CourierChannel> courierChannelList = courierChannelRepository.findAll();
        assertThat(courierChannelList).hasSize(databaseSizeBeforeUpdate);
        CourierChannel testCourierChannel = courierChannelList.get(courierChannelList.size() - 1);

        // Validate the CourierChannel in Elasticsearch
        verify(mockCourierChannelSearchRepository, times(1)).save(testCourierChannel);
    }

    @Test
    @Transactional
    public void updateNonExistingCourierChannel() throws Exception {
        int databaseSizeBeforeUpdate = courierChannelRepository.findAll().size();

        // Create the CourierChannel
        CourierChannelDTO courierChannelDTO = courierChannelMapper.toDto(courierChannel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCourierChannelMockMvc.perform(put("/api/courier-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courierChannelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourierChannel in the database
        List<CourierChannel> courierChannelList = courierChannelRepository.findAll();
        assertThat(courierChannelList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CourierChannel in Elasticsearch
        verify(mockCourierChannelSearchRepository, times(0)).save(courierChannel);
    }

    @Test
    @Transactional
    public void deleteCourierChannel() throws Exception {
        // Initialize the database
        courierChannelRepository.saveAndFlush(courierChannel);

        int databaseSizeBeforeDelete = courierChannelRepository.findAll().size();

        // Get the courierChannel
        restCourierChannelMockMvc.perform(delete("/api/courier-channels/{id}", courierChannel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CourierChannel> courierChannelList = courierChannelRepository.findAll();
        assertThat(courierChannelList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CourierChannel in Elasticsearch
        verify(mockCourierChannelSearchRepository, times(1)).deleteById(courierChannel.getId());
    }

    @Test
    @Transactional
    public void searchCourierChannel() throws Exception {
        // Initialize the database
        courierChannelRepository.saveAndFlush(courierChannel);
        when(mockCourierChannelSearchRepository.search(queryStringQuery("id:" + courierChannel.getId())))
            .thenReturn(Collections.singletonList(courierChannel));
        // Search the courierChannel
        restCourierChannelMockMvc.perform(get("/api/_search/courier-channels?query=id:" + courierChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courierChannel.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourierChannel.class);
        CourierChannel courierChannel1 = new CourierChannel();
        courierChannel1.setId(1L);
        CourierChannel courierChannel2 = new CourierChannel();
        courierChannel2.setId(courierChannel1.getId());
        assertThat(courierChannel1).isEqualTo(courierChannel2);
        courierChannel2.setId(2L);
        assertThat(courierChannel1).isNotEqualTo(courierChannel2);
        courierChannel1.setId(null);
        assertThat(courierChannel1).isNotEqualTo(courierChannel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourierChannelDTO.class);
        CourierChannelDTO courierChannelDTO1 = new CourierChannelDTO();
        courierChannelDTO1.setId(1L);
        CourierChannelDTO courierChannelDTO2 = new CourierChannelDTO();
        assertThat(courierChannelDTO1).isNotEqualTo(courierChannelDTO2);
        courierChannelDTO2.setId(courierChannelDTO1.getId());
        assertThat(courierChannelDTO1).isEqualTo(courierChannelDTO2);
        courierChannelDTO2.setId(2L);
        assertThat(courierChannelDTO1).isNotEqualTo(courierChannelDTO2);
        courierChannelDTO1.setId(null);
        assertThat(courierChannelDTO1).isNotEqualTo(courierChannelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(courierChannelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(courierChannelMapper.fromId(null)).isNull();
    }
}
