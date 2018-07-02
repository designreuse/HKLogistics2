package com.hk.logistics.web.rest;

import com.hk.logistics.HkLogisticsApp;

import com.hk.logistics.domain.Awb;
import com.hk.logistics.domain.Channel;
import com.hk.logistics.domain.VendorWHCourierMapping;
import com.hk.logistics.domain.AwbStatus;
import com.hk.logistics.repository.AwbRepository;
import com.hk.logistics.repository.search.AwbSearchRepository;
import com.hk.logistics.service.AwbService;
import com.hk.logistics.service.dto.AwbDTO;
import com.hk.logistics.service.mapper.AwbMapper;
import com.hk.logistics.util.AwbExcelUtil;
import com.hk.logistics.web.rest.errors.ExceptionTranslator;
import com.hk.logistics.service.dto.AwbCriteria;
import com.hk.logistics.service.AwbQueryService;

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
 * Test class for the AwbResource REST controller.
 *
 * @see AwbResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HkLogisticsApp.class)
public class AwbResourceIntTest {

    private static final String DEFAULT_AWB_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_AWB_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_AWB_BAR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AWB_BAR_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COD = false;
    private static final Boolean UPDATED_COD = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RETURN_AWB_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_RETURN_AWB_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_RETURN_AWB_BAR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RETURN_AWB_BAR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TRACKING_LINK = "AAAAAAAAAA";
    private static final String UPDATED_TRACKING_LINK = "BBBBBBBBBB";

    @Autowired
    private AwbRepository awbRepository;


    @Autowired
    private AwbMapper awbMapper;
    

    @Autowired
    private AwbService awbService;

    /**
     * This repository is mocked in the com.hk.logistics.repository.search test package.
     *
     * @see com.hk.logistics.repository.search.AwbSearchRepositoryMockConfiguration
     */
    @Autowired
    private AwbSearchRepository mockAwbSearchRepository;

    @Autowired
    private AwbQueryService awbQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;
    
    @Autowired
    private AwbExcelUtil awbExcelUtil;

    private MockMvc restAwbMockMvc;

    private Awb awb;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AwbResource awbResource = new AwbResource(awbService, awbQueryService, awbExcelUtil);
        this.restAwbMockMvc = MockMvcBuilders.standaloneSetup(awbResource)
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
    public static Awb createEntity(EntityManager em) {
        Awb awb = new Awb()
            .awbNumber(DEFAULT_AWB_NUMBER)
            .awbBarCode(DEFAULT_AWB_BAR_CODE)
            .cod(DEFAULT_COD)
            .createDate(DEFAULT_CREATE_DATE)
            .returnAwbNumber(DEFAULT_RETURN_AWB_NUMBER)
            .returnAwbBarCode(DEFAULT_RETURN_AWB_BAR_CODE)
            .trackingLink(DEFAULT_TRACKING_LINK);
        return awb;
    }

    @Before
    public void initTest() {
        awb = createEntity(em);
    }

    @Test
    @Transactional
    public void createAwb() throws Exception {
        int databaseSizeBeforeCreate = awbRepository.findAll().size();

        // Create the Awb
        AwbDTO awbDTO = awbMapper.toDto(awb);
        restAwbMockMvc.perform(post("/api/awbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(awbDTO)))
            .andExpect(status().isCreated());

        // Validate the Awb in the database
        List<Awb> awbList = awbRepository.findAll();
        assertThat(awbList).hasSize(databaseSizeBeforeCreate + 1);
        Awb testAwb = awbList.get(awbList.size() - 1);
        assertThat(testAwb.getAwbNumber()).isEqualTo(DEFAULT_AWB_NUMBER);
        assertThat(testAwb.getAwbBarCode()).isEqualTo(DEFAULT_AWB_BAR_CODE);
        assertThat(testAwb.isCod()).isEqualTo(DEFAULT_COD);
        assertThat(testAwb.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testAwb.getReturnAwbNumber()).isEqualTo(DEFAULT_RETURN_AWB_NUMBER);
        assertThat(testAwb.getReturnAwbBarCode()).isEqualTo(DEFAULT_RETURN_AWB_BAR_CODE);
        assertThat(testAwb.getTrackingLink()).isEqualTo(DEFAULT_TRACKING_LINK);

        // Validate the Awb in Elasticsearch
        verify(mockAwbSearchRepository, times(1)).save(testAwb);
    }

    @Test
    @Transactional
    public void createAwbWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = awbRepository.findAll().size();

        // Create the Awb with an existing ID
        awb.setId(1L);
        AwbDTO awbDTO = awbMapper.toDto(awb);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAwbMockMvc.perform(post("/api/awbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(awbDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Awb in the database
        List<Awb> awbList = awbRepository.findAll();
        assertThat(awbList).hasSize(databaseSizeBeforeCreate);

        // Validate the Awb in Elasticsearch
        verify(mockAwbSearchRepository, times(0)).save(awb);
    }

    @Test
    @Transactional
    public void checkAwbNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = awbRepository.findAll().size();
        // set the field null
        awb.setAwbNumber(null);

        // Create the Awb, which fails.
        AwbDTO awbDTO = awbMapper.toDto(awb);

        restAwbMockMvc.perform(post("/api/awbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(awbDTO)))
            .andExpect(status().isBadRequest());

        List<Awb> awbList = awbRepository.findAll();
        assertThat(awbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAwbBarCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = awbRepository.findAll().size();
        // set the field null
        awb.setAwbBarCode(null);

        // Create the Awb, which fails.
        AwbDTO awbDTO = awbMapper.toDto(awb);

        restAwbMockMvc.perform(post("/api/awbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(awbDTO)))
            .andExpect(status().isBadRequest());

        List<Awb> awbList = awbRepository.findAll();
        assertThat(awbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodIsRequired() throws Exception {
        int databaseSizeBeforeTest = awbRepository.findAll().size();
        // set the field null
        awb.setCod(null);

        // Create the Awb, which fails.
        AwbDTO awbDTO = awbMapper.toDto(awb);

        restAwbMockMvc.perform(post("/api/awbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(awbDTO)))
            .andExpect(status().isBadRequest());

        List<Awb> awbList = awbRepository.findAll();
        assertThat(awbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = awbRepository.findAll().size();
        // set the field null
        awb.setCreateDate(null);

        // Create the Awb, which fails.
        AwbDTO awbDTO = awbMapper.toDto(awb);

        restAwbMockMvc.perform(post("/api/awbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(awbDTO)))
            .andExpect(status().isBadRequest());

        List<Awb> awbList = awbRepository.findAll();
        assertThat(awbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAwbs() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList
        restAwbMockMvc.perform(get("/api/awbs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(awb.getId().intValue())))
            .andExpect(jsonPath("$.[*].awbNumber").value(hasItem(DEFAULT_AWB_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].awbBarCode").value(hasItem(DEFAULT_AWB_BAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].cod").value(hasItem(DEFAULT_COD.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].returnAwbNumber").value(hasItem(DEFAULT_RETURN_AWB_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].returnAwbBarCode").value(hasItem(DEFAULT_RETURN_AWB_BAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].trackingLink").value(hasItem(DEFAULT_TRACKING_LINK.toString())));
    }
    

    @Test
    @Transactional
    public void getAwb() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get the awb
        restAwbMockMvc.perform(get("/api/awbs/{id}", awb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(awb.getId().intValue()))
            .andExpect(jsonPath("$.awbNumber").value(DEFAULT_AWB_NUMBER.toString()))
            .andExpect(jsonPath("$.awbBarCode").value(DEFAULT_AWB_BAR_CODE.toString()))
            .andExpect(jsonPath("$.cod").value(DEFAULT_COD.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.returnAwbNumber").value(DEFAULT_RETURN_AWB_NUMBER.toString()))
            .andExpect(jsonPath("$.returnAwbBarCode").value(DEFAULT_RETURN_AWB_BAR_CODE.toString()))
            .andExpect(jsonPath("$.trackingLink").value(DEFAULT_TRACKING_LINK.toString()));
    }

    @Test
    @Transactional
    public void getAllAwbsByAwbNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where awbNumber equals to DEFAULT_AWB_NUMBER
        defaultAwbShouldBeFound("awbNumber.equals=" + DEFAULT_AWB_NUMBER);

        // Get all the awbList where awbNumber equals to UPDATED_AWB_NUMBER
        defaultAwbShouldNotBeFound("awbNumber.equals=" + UPDATED_AWB_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAwbsByAwbNumberIsInShouldWork() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where awbNumber in DEFAULT_AWB_NUMBER or UPDATED_AWB_NUMBER
        defaultAwbShouldBeFound("awbNumber.in=" + DEFAULT_AWB_NUMBER + "," + UPDATED_AWB_NUMBER);

        // Get all the awbList where awbNumber equals to UPDATED_AWB_NUMBER
        defaultAwbShouldNotBeFound("awbNumber.in=" + UPDATED_AWB_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAwbsByAwbNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where awbNumber is not null
        defaultAwbShouldBeFound("awbNumber.specified=true");

        // Get all the awbList where awbNumber is null
        defaultAwbShouldNotBeFound("awbNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllAwbsByAwbBarCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where awbBarCode equals to DEFAULT_AWB_BAR_CODE
        defaultAwbShouldBeFound("awbBarCode.equals=" + DEFAULT_AWB_BAR_CODE);

        // Get all the awbList where awbBarCode equals to UPDATED_AWB_BAR_CODE
        defaultAwbShouldNotBeFound("awbBarCode.equals=" + UPDATED_AWB_BAR_CODE);
    }

    @Test
    @Transactional
    public void getAllAwbsByAwbBarCodeIsInShouldWork() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where awbBarCode in DEFAULT_AWB_BAR_CODE or UPDATED_AWB_BAR_CODE
        defaultAwbShouldBeFound("awbBarCode.in=" + DEFAULT_AWB_BAR_CODE + "," + UPDATED_AWB_BAR_CODE);

        // Get all the awbList where awbBarCode equals to UPDATED_AWB_BAR_CODE
        defaultAwbShouldNotBeFound("awbBarCode.in=" + UPDATED_AWB_BAR_CODE);
    }

    @Test
    @Transactional
    public void getAllAwbsByAwbBarCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where awbBarCode is not null
        defaultAwbShouldBeFound("awbBarCode.specified=true");

        // Get all the awbList where awbBarCode is null
        defaultAwbShouldNotBeFound("awbBarCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllAwbsByCodIsEqualToSomething() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where cod equals to DEFAULT_COD
        defaultAwbShouldBeFound("cod.equals=" + DEFAULT_COD);

        // Get all the awbList where cod equals to UPDATED_COD
        defaultAwbShouldNotBeFound("cod.equals=" + UPDATED_COD);
    }

    @Test
    @Transactional
    public void getAllAwbsByCodIsInShouldWork() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where cod in DEFAULT_COD or UPDATED_COD
        defaultAwbShouldBeFound("cod.in=" + DEFAULT_COD + "," + UPDATED_COD);

        // Get all the awbList where cod equals to UPDATED_COD
        defaultAwbShouldNotBeFound("cod.in=" + UPDATED_COD);
    }

    @Test
    @Transactional
    public void getAllAwbsByCodIsNullOrNotNull() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where cod is not null
        defaultAwbShouldBeFound("cod.specified=true");

        // Get all the awbList where cod is null
        defaultAwbShouldNotBeFound("cod.specified=false");
    }

    @Test
    @Transactional
    public void getAllAwbsByCreateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where createDate equals to DEFAULT_CREATE_DATE
        defaultAwbShouldBeFound("createDate.equals=" + DEFAULT_CREATE_DATE);

        // Get all the awbList where createDate equals to UPDATED_CREATE_DATE
        defaultAwbShouldNotBeFound("createDate.equals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllAwbsByCreateDateIsInShouldWork() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where createDate in DEFAULT_CREATE_DATE or UPDATED_CREATE_DATE
        defaultAwbShouldBeFound("createDate.in=" + DEFAULT_CREATE_DATE + "," + UPDATED_CREATE_DATE);

        // Get all the awbList where createDate equals to UPDATED_CREATE_DATE
        defaultAwbShouldNotBeFound("createDate.in=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllAwbsByCreateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where createDate is not null
        defaultAwbShouldBeFound("createDate.specified=true");

        // Get all the awbList where createDate is null
        defaultAwbShouldNotBeFound("createDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAwbsByCreateDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where createDate greater than or equals to DEFAULT_CREATE_DATE
        defaultAwbShouldBeFound("createDate.greaterOrEqualThan=" + DEFAULT_CREATE_DATE);

        // Get all the awbList where createDate greater than or equals to UPDATED_CREATE_DATE
        defaultAwbShouldNotBeFound("createDate.greaterOrEqualThan=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllAwbsByCreateDateIsLessThanSomething() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where createDate less than or equals to DEFAULT_CREATE_DATE
        defaultAwbShouldNotBeFound("createDate.lessThan=" + DEFAULT_CREATE_DATE);

        // Get all the awbList where createDate less than or equals to UPDATED_CREATE_DATE
        defaultAwbShouldBeFound("createDate.lessThan=" + UPDATED_CREATE_DATE);
    }


    @Test
    @Transactional
    public void getAllAwbsByReturnAwbNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where returnAwbNumber equals to DEFAULT_RETURN_AWB_NUMBER
        defaultAwbShouldBeFound("returnAwbNumber.equals=" + DEFAULT_RETURN_AWB_NUMBER);

        // Get all the awbList where returnAwbNumber equals to UPDATED_RETURN_AWB_NUMBER
        defaultAwbShouldNotBeFound("returnAwbNumber.equals=" + UPDATED_RETURN_AWB_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAwbsByReturnAwbNumberIsInShouldWork() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where returnAwbNumber in DEFAULT_RETURN_AWB_NUMBER or UPDATED_RETURN_AWB_NUMBER
        defaultAwbShouldBeFound("returnAwbNumber.in=" + DEFAULT_RETURN_AWB_NUMBER + "," + UPDATED_RETURN_AWB_NUMBER);

        // Get all the awbList where returnAwbNumber equals to UPDATED_RETURN_AWB_NUMBER
        defaultAwbShouldNotBeFound("returnAwbNumber.in=" + UPDATED_RETURN_AWB_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAwbsByReturnAwbNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where returnAwbNumber is not null
        defaultAwbShouldBeFound("returnAwbNumber.specified=true");

        // Get all the awbList where returnAwbNumber is null
        defaultAwbShouldNotBeFound("returnAwbNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllAwbsByReturnAwbBarCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where returnAwbBarCode equals to DEFAULT_RETURN_AWB_BAR_CODE
        defaultAwbShouldBeFound("returnAwbBarCode.equals=" + DEFAULT_RETURN_AWB_BAR_CODE);

        // Get all the awbList where returnAwbBarCode equals to UPDATED_RETURN_AWB_BAR_CODE
        defaultAwbShouldNotBeFound("returnAwbBarCode.equals=" + UPDATED_RETURN_AWB_BAR_CODE);
    }

    @Test
    @Transactional
    public void getAllAwbsByReturnAwbBarCodeIsInShouldWork() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where returnAwbBarCode in DEFAULT_RETURN_AWB_BAR_CODE or UPDATED_RETURN_AWB_BAR_CODE
        defaultAwbShouldBeFound("returnAwbBarCode.in=" + DEFAULT_RETURN_AWB_BAR_CODE + "," + UPDATED_RETURN_AWB_BAR_CODE);

        // Get all the awbList where returnAwbBarCode equals to UPDATED_RETURN_AWB_BAR_CODE
        defaultAwbShouldNotBeFound("returnAwbBarCode.in=" + UPDATED_RETURN_AWB_BAR_CODE);
    }

    @Test
    @Transactional
    public void getAllAwbsByReturnAwbBarCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where returnAwbBarCode is not null
        defaultAwbShouldBeFound("returnAwbBarCode.specified=true");

        // Get all the awbList where returnAwbBarCode is null
        defaultAwbShouldNotBeFound("returnAwbBarCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllAwbsByTrackingLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where trackingLink equals to DEFAULT_TRACKING_LINK
        defaultAwbShouldBeFound("trackingLink.equals=" + DEFAULT_TRACKING_LINK);

        // Get all the awbList where trackingLink equals to UPDATED_TRACKING_LINK
        defaultAwbShouldNotBeFound("trackingLink.equals=" + UPDATED_TRACKING_LINK);
    }

    @Test
    @Transactional
    public void getAllAwbsByTrackingLinkIsInShouldWork() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where trackingLink in DEFAULT_TRACKING_LINK or UPDATED_TRACKING_LINK
        defaultAwbShouldBeFound("trackingLink.in=" + DEFAULT_TRACKING_LINK + "," + UPDATED_TRACKING_LINK);

        // Get all the awbList where trackingLink equals to UPDATED_TRACKING_LINK
        defaultAwbShouldNotBeFound("trackingLink.in=" + UPDATED_TRACKING_LINK);
    }

    @Test
    @Transactional
    public void getAllAwbsByTrackingLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        // Get all the awbList where trackingLink is not null
        defaultAwbShouldBeFound("trackingLink.specified=true");

        // Get all the awbList where trackingLink is null
        defaultAwbShouldNotBeFound("trackingLink.specified=false");
    }

    @Test
    @Transactional
    public void getAllAwbsByChannelIsEqualToSomething() throws Exception {
        // Initialize the database
        Channel channel = ChannelResourceIntTest.createEntity(em);
        em.persist(channel);
        em.flush();
        awb.setChannel(channel);
        awbRepository.saveAndFlush(awb);
        Long channelId = channel.getId();

        // Get all the awbList where channel equals to channelId
        defaultAwbShouldBeFound("channelId.equals=" + channelId);

        // Get all the awbList where channel equals to channelId + 1
        defaultAwbShouldNotBeFound("channelId.equals=" + (channelId + 1));
    }


    @Test
    @Transactional
    public void getAllAwbsByVendorWHCourierMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        VendorWHCourierMapping vendorWHCourierMapping = VendorWHCourierMappingResourceIntTest.createEntity(em);
        em.persist(vendorWHCourierMapping);
        em.flush();
        awb.setVendorWHCourierMapping(vendorWHCourierMapping);
        awbRepository.saveAndFlush(awb);
        Long vendorWHCourierMappingId = vendorWHCourierMapping.getId();

        // Get all the awbList where vendorWHCourierMapping equals to vendorWHCourierMappingId
        defaultAwbShouldBeFound("vendorWHCourierMappingId.equals=" + vendorWHCourierMappingId);

        // Get all the awbList where vendorWHCourierMapping equals to vendorWHCourierMappingId + 1
        defaultAwbShouldNotBeFound("vendorWHCourierMappingId.equals=" + (vendorWHCourierMappingId + 1));
    }


    @Test
    @Transactional
    public void getAllAwbsByAwbStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        AwbStatus awbStatus = AwbStatusResourceIntTest.createEntity(em);
        em.persist(awbStatus);
        em.flush();
        awb.setAwbStatus(awbStatus);
        awbRepository.saveAndFlush(awb);
        Long awbStatusId = awbStatus.getId();

        // Get all the awbList where awbStatus equals to awbStatusId
        defaultAwbShouldBeFound("awbStatusId.equals=" + awbStatusId);

        // Get all the awbList where awbStatus equals to awbStatusId + 1
        defaultAwbShouldNotBeFound("awbStatusId.equals=" + (awbStatusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAwbShouldBeFound(String filter) throws Exception {
        restAwbMockMvc.perform(get("/api/awbs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(awb.getId().intValue())))
            .andExpect(jsonPath("$.[*].awbNumber").value(hasItem(DEFAULT_AWB_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].awbBarCode").value(hasItem(DEFAULT_AWB_BAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].cod").value(hasItem(DEFAULT_COD.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].returnAwbNumber").value(hasItem(DEFAULT_RETURN_AWB_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].returnAwbBarCode").value(hasItem(DEFAULT_RETURN_AWB_BAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].trackingLink").value(hasItem(DEFAULT_TRACKING_LINK.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAwbShouldNotBeFound(String filter) throws Exception {
        restAwbMockMvc.perform(get("/api/awbs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingAwb() throws Exception {
        // Get the awb
        restAwbMockMvc.perform(get("/api/awbs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAwb() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        int databaseSizeBeforeUpdate = awbRepository.findAll().size();

        // Update the awb
        Awb updatedAwb = awbRepository.findById(awb.getId()).get();
        // Disconnect from session so that the updates on updatedAwb are not directly saved in db
        em.detach(updatedAwb);
        updatedAwb
            .awbNumber(UPDATED_AWB_NUMBER)
            .awbBarCode(UPDATED_AWB_BAR_CODE)
            .cod(UPDATED_COD)
            .createDate(UPDATED_CREATE_DATE)
            .returnAwbNumber(UPDATED_RETURN_AWB_NUMBER)
            .returnAwbBarCode(UPDATED_RETURN_AWB_BAR_CODE)
            .trackingLink(UPDATED_TRACKING_LINK);
        AwbDTO awbDTO = awbMapper.toDto(updatedAwb);

        restAwbMockMvc.perform(put("/api/awbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(awbDTO)))
            .andExpect(status().isOk());

        // Validate the Awb in the database
        List<Awb> awbList = awbRepository.findAll();
        assertThat(awbList).hasSize(databaseSizeBeforeUpdate);
        Awb testAwb = awbList.get(awbList.size() - 1);
        assertThat(testAwb.getAwbNumber()).isEqualTo(UPDATED_AWB_NUMBER);
        assertThat(testAwb.getAwbBarCode()).isEqualTo(UPDATED_AWB_BAR_CODE);
        assertThat(testAwb.isCod()).isEqualTo(UPDATED_COD);
        assertThat(testAwb.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testAwb.getReturnAwbNumber()).isEqualTo(UPDATED_RETURN_AWB_NUMBER);
        assertThat(testAwb.getReturnAwbBarCode()).isEqualTo(UPDATED_RETURN_AWB_BAR_CODE);
        assertThat(testAwb.getTrackingLink()).isEqualTo(UPDATED_TRACKING_LINK);

        // Validate the Awb in Elasticsearch
        verify(mockAwbSearchRepository, times(1)).save(testAwb);
    }

    @Test
    @Transactional
    public void updateNonExistingAwb() throws Exception {
        int databaseSizeBeforeUpdate = awbRepository.findAll().size();

        // Create the Awb
        AwbDTO awbDTO = awbMapper.toDto(awb);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAwbMockMvc.perform(put("/api/awbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(awbDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Awb in the database
        List<Awb> awbList = awbRepository.findAll();
        assertThat(awbList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Awb in Elasticsearch
        verify(mockAwbSearchRepository, times(0)).save(awb);
    }

    @Test
    @Transactional
    public void deleteAwb() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);

        int databaseSizeBeforeDelete = awbRepository.findAll().size();

        // Get the awb
        restAwbMockMvc.perform(delete("/api/awbs/{id}", awb.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Awb> awbList = awbRepository.findAll();
        assertThat(awbList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Awb in Elasticsearch
        verify(mockAwbSearchRepository, times(1)).deleteById(awb.getId());
    }

    @Test
    @Transactional
    public void searchAwb() throws Exception {
        // Initialize the database
        awbRepository.saveAndFlush(awb);
        when(mockAwbSearchRepository.search(queryStringQuery("id:" + awb.getId())))
            .thenReturn(Collections.singletonList(awb));
        // Search the awb
        restAwbMockMvc.perform(get("/api/_search/awbs?query=id:" + awb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(awb.getId().intValue())))
            .andExpect(jsonPath("$.[*].awbNumber").value(hasItem(DEFAULT_AWB_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].awbBarCode").value(hasItem(DEFAULT_AWB_BAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].cod").value(hasItem(DEFAULT_COD.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].returnAwbNumber").value(hasItem(DEFAULT_RETURN_AWB_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].returnAwbBarCode").value(hasItem(DEFAULT_RETURN_AWB_BAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].trackingLink").value(hasItem(DEFAULT_TRACKING_LINK.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Awb.class);
        Awb awb1 = new Awb();
        awb1.setId(1L);
        Awb awb2 = new Awb();
        awb2.setId(awb1.getId());
        assertThat(awb1).isEqualTo(awb2);
        awb2.setId(2L);
        assertThat(awb1).isNotEqualTo(awb2);
        awb1.setId(null);
        assertThat(awb1).isNotEqualTo(awb2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AwbDTO.class);
        AwbDTO awbDTO1 = new AwbDTO();
        awbDTO1.setId(1L);
        AwbDTO awbDTO2 = new AwbDTO();
        assertThat(awbDTO1).isNotEqualTo(awbDTO2);
        awbDTO2.setId(awbDTO1.getId());
        assertThat(awbDTO1).isEqualTo(awbDTO2);
        awbDTO2.setId(2L);
        assertThat(awbDTO1).isNotEqualTo(awbDTO2);
        awbDTO1.setId(null);
        assertThat(awbDTO1).isNotEqualTo(awbDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(awbMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(awbMapper.fromId(null)).isNull();
    }
}
