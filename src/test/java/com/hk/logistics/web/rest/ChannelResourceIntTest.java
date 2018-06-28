package com.hk.logistics.web.rest;

import com.hk.logistics.HkLogisticsApp;

import com.hk.logistics.domain.Channel;
import com.hk.logistics.domain.CourierChannel;
import com.hk.logistics.repository.ChannelRepository;
import com.hk.logistics.repository.search.ChannelSearchRepository;
import com.hk.logistics.service.ChannelService;
import com.hk.logistics.service.dto.ChannelDTO;
import com.hk.logistics.service.mapper.ChannelMapper;
import com.hk.logistics.web.rest.errors.ExceptionTranslator;
import com.hk.logistics.service.dto.ChannelCriteria;
import com.hk.logistics.service.ChannelQueryService;

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
 * Test class for the ChannelResource REST controller.
 *
 * @see ChannelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HkLogisticsApp.class)
public class ChannelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STORE = "AAAAAAAAAA";
    private static final String UPDATED_STORE = "BBBBBBBBBB";

    @Autowired
    private ChannelRepository channelRepository;


    @Autowired
    private ChannelMapper channelMapper;
    

    @Autowired
    private ChannelService channelService;

    /**
     * This repository is mocked in the com.hk.logistics.repository.search test package.
     *
     * @see com.hk.logistics.repository.search.ChannelSearchRepositoryMockConfiguration
     */
    @Autowired
    private ChannelSearchRepository mockChannelSearchRepository;

    @Autowired
    private ChannelQueryService channelQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChannelMockMvc;

    private Channel channel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChannelResource channelResource = new ChannelResource(channelService, channelQueryService);
        this.restChannelMockMvc = MockMvcBuilders.standaloneSetup(channelResource)
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
    public static Channel createEntity(EntityManager em) {
        Channel channel = new Channel()
            .name(DEFAULT_NAME)
            .store(DEFAULT_STORE);
        return channel;
    }

    @Before
    public void initTest() {
        channel = createEntity(em);
    }

    @Test
    @Transactional
    public void createChannel() throws Exception {
        int databaseSizeBeforeCreate = channelRepository.findAll().size();

        // Create the Channel
        ChannelDTO channelDTO = channelMapper.toDto(channel);
        restChannelMockMvc.perform(post("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isCreated());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeCreate + 1);
        Channel testChannel = channelList.get(channelList.size() - 1);
        assertThat(testChannel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChannel.getStore()).isEqualTo(DEFAULT_STORE);

        // Validate the Channel in Elasticsearch
        verify(mockChannelSearchRepository, times(1)).save(testChannel);
    }

    @Test
    @Transactional
    public void createChannelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = channelRepository.findAll().size();

        // Create the Channel with an existing ID
        channel.setId(1L);
        ChannelDTO channelDTO = channelMapper.toDto(channel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChannelMockMvc.perform(post("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeCreate);

        // Validate the Channel in Elasticsearch
        verify(mockChannelSearchRepository, times(0)).save(channel);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = channelRepository.findAll().size();
        // set the field null
        channel.setName(null);

        // Create the Channel, which fails.
        ChannelDTO channelDTO = channelMapper.toDto(channel);

        restChannelMockMvc.perform(post("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isBadRequest());

        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = channelRepository.findAll().size();
        // set the field null
        channel.setStore(null);

        // Create the Channel, which fails.
        ChannelDTO channelDTO = channelMapper.toDto(channel);

        restChannelMockMvc.perform(post("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isBadRequest());

        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChannels() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList
        restChannelMockMvc.perform(get("/api/channels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].store").value(hasItem(DEFAULT_STORE.toString())));
    }
    

    @Test
    @Transactional
    public void getChannel() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get the channel
        restChannelMockMvc.perform(get("/api/channels/{id}", channel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(channel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.store").value(DEFAULT_STORE.toString()));
    }

    @Test
    @Transactional
    public void getAllChannelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where name equals to DEFAULT_NAME
        defaultChannelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the channelList where name equals to UPDATED_NAME
        defaultChannelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllChannelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultChannelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the channelList where name equals to UPDATED_NAME
        defaultChannelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllChannelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where name is not null
        defaultChannelShouldBeFound("name.specified=true");

        // Get all the channelList where name is null
        defaultChannelShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllChannelsByStoreIsEqualToSomething() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where store equals to DEFAULT_STORE
        defaultChannelShouldBeFound("store.equals=" + DEFAULT_STORE);

        // Get all the channelList where store equals to UPDATED_STORE
        defaultChannelShouldNotBeFound("store.equals=" + UPDATED_STORE);
    }

    @Test
    @Transactional
    public void getAllChannelsByStoreIsInShouldWork() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where store in DEFAULT_STORE or UPDATED_STORE
        defaultChannelShouldBeFound("store.in=" + DEFAULT_STORE + "," + UPDATED_STORE);

        // Get all the channelList where store equals to UPDATED_STORE
        defaultChannelShouldNotBeFound("store.in=" + UPDATED_STORE);
    }

    @Test
    @Transactional
    public void getAllChannelsByStoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList where store is not null
        defaultChannelShouldBeFound("store.specified=true");

        // Get all the channelList where store is null
        defaultChannelShouldNotBeFound("store.specified=false");
    }

    @Test
    @Transactional
    public void getAllChannelsByCourierChannelIsEqualToSomething() throws Exception {
        // Initialize the database
        CourierChannel courierChannel = CourierChannelResourceIntTest.createEntity(em);
        em.persist(courierChannel);
        em.flush();
        channel.addCourierChannel(courierChannel);
        channelRepository.saveAndFlush(channel);
        Long courierChannelId = courierChannel.getId();

        // Get all the channelList where courierChannel equals to courierChannelId
        defaultChannelShouldBeFound("courierChannelId.equals=" + courierChannelId);

        // Get all the channelList where courierChannel equals to courierChannelId + 1
        defaultChannelShouldNotBeFound("courierChannelId.equals=" + (courierChannelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultChannelShouldBeFound(String filter) throws Exception {
        restChannelMockMvc.perform(get("/api/channels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].store").value(hasItem(DEFAULT_STORE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultChannelShouldNotBeFound(String filter) throws Exception {
        restChannelMockMvc.perform(get("/api/channels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingChannel() throws Exception {
        // Get the channel
        restChannelMockMvc.perform(get("/api/channels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChannel() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        int databaseSizeBeforeUpdate = channelRepository.findAll().size();

        // Update the channel
        Channel updatedChannel = channelRepository.findById(channel.getId()).get();
        // Disconnect from session so that the updates on updatedChannel are not directly saved in db
        em.detach(updatedChannel);
        updatedChannel
            .name(UPDATED_NAME)
            .store(UPDATED_STORE);
        ChannelDTO channelDTO = channelMapper.toDto(updatedChannel);

        restChannelMockMvc.perform(put("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isOk());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeUpdate);
        Channel testChannel = channelList.get(channelList.size() - 1);
        assertThat(testChannel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChannel.getStore()).isEqualTo(UPDATED_STORE);

        // Validate the Channel in Elasticsearch
        verify(mockChannelSearchRepository, times(1)).save(testChannel);
    }

    @Test
    @Transactional
    public void updateNonExistingChannel() throws Exception {
        int databaseSizeBeforeUpdate = channelRepository.findAll().size();

        // Create the Channel
        ChannelDTO channelDTO = channelMapper.toDto(channel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChannelMockMvc.perform(put("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Channel in Elasticsearch
        verify(mockChannelSearchRepository, times(0)).save(channel);
    }

    @Test
    @Transactional
    public void deleteChannel() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        int databaseSizeBeforeDelete = channelRepository.findAll().size();

        // Get the channel
        restChannelMockMvc.perform(delete("/api/channels/{id}", channel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Channel in Elasticsearch
        verify(mockChannelSearchRepository, times(1)).deleteById(channel.getId());
    }

    @Test
    @Transactional
    public void searchChannel() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);
        when(mockChannelSearchRepository.search(queryStringQuery("id:" + channel.getId())))
            .thenReturn(Collections.singletonList(channel));
        // Search the channel
        restChannelMockMvc.perform(get("/api/_search/channels?query=id:" + channel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].store").value(hasItem(DEFAULT_STORE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Channel.class);
        Channel channel1 = new Channel();
        channel1.setId(1L);
        Channel channel2 = new Channel();
        channel2.setId(channel1.getId());
        assertThat(channel1).isEqualTo(channel2);
        channel2.setId(2L);
        assertThat(channel1).isNotEqualTo(channel2);
        channel1.setId(null);
        assertThat(channel1).isNotEqualTo(channel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChannelDTO.class);
        ChannelDTO channelDTO1 = new ChannelDTO();
        channelDTO1.setId(1L);
        ChannelDTO channelDTO2 = new ChannelDTO();
        assertThat(channelDTO1).isNotEqualTo(channelDTO2);
        channelDTO2.setId(channelDTO1.getId());
        assertThat(channelDTO1).isEqualTo(channelDTO2);
        channelDTO2.setId(2L);
        assertThat(channelDTO1).isNotEqualTo(channelDTO2);
        channelDTO1.setId(null);
        assertThat(channelDTO1).isNotEqualTo(channelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(channelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(channelMapper.fromId(null)).isNull();
    }
}
