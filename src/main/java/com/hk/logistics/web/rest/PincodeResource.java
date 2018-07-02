package com.hk.logistics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hk.logistics.service.PincodeService;
import com.hk.logistics.service.StateQueryService;
import com.hk.logistics.service.StateService;
import com.hk.logistics.service.ZoneQueryService;
import com.hk.logistics.service.ZoneService;
import com.hk.logistics.web.rest.errors.BadRequestAlertException;
import com.hk.logistics.web.rest.util.HeaderUtil;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.hk.logistics.service.dto.PincodeDTO;
import com.hk.logistics.service.dto.StateCriteria;
import com.hk.logistics.service.dto.ZoneCriteria;
import com.hk.logistics.util.PincodeExcelUtil;
import com.hk.logistics.service.dto.AwbCriteria;
import com.hk.logistics.service.dto.AwbDTO;
import com.hk.logistics.service.dto.CityCriteria;
import com.hk.logistics.service.dto.HubCriteria;
import com.hk.logistics.service.dto.PincodeCriteria;
import com.hk.logistics.domain.AwbExcelPojo;
import com.hk.logistics.security.AuthoritiesConstants;
import com.hk.logistics.service.CityQueryService;
import com.hk.logistics.service.CityService;
import com.hk.logistics.service.HubQueryService;
import com.hk.logistics.service.HubService;
import com.hk.logistics.service.PincodeQueryService;

import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;
import com.hk.logistics.service.dto.CityDTO;
import com.hk.logistics.service.dto.StateDTO;
import com.hk.logistics.service.dto.ZoneDTO;
import com.hk.logistics.service.dto.HubDTO;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Pincode.
 */
@RestController
@RequestMapping("/api")
public class PincodeResource {

    private final Logger log = LoggerFactory.getLogger(PincodeResource.class);

    private static final String ENTITY_NAME = "pincode";

    private final PincodeService pincodeService;

    private final PincodeQueryService pincodeQueryService;
    
    private final CityService cityService;
    
    private final CityQueryService cityQueryService;
    
    private final StateService stateService;
    
    private final StateQueryService stateQueryService;
    
    private final ZoneService zoneService;
    
    private final ZoneQueryService zoneQueryService;
    
    private final HubService hubService;
    
    private final HubQueryService hubQueryService;
    
    @Autowired
    private PincodeExcelUtil pincodeExcelUtil;
    

    @Value("${batchSize:1000}")
    private int batchSize;

	public PincodeResource(PincodeService pincodeService, PincodeQueryService pincodeQueryService,
			CityService cityService, CityQueryService cityQueryService, StateService stateService, StateQueryService stateQueryService, ZoneService zoneService,
			ZoneQueryService zoneQueryService, HubService hubService, HubQueryService hubQueryService) {
        this.pincodeService = pincodeService;
        this.pincodeQueryService = pincodeQueryService;
        this.cityService = cityService;
        this.cityQueryService = cityQueryService;
        this.stateService = stateService;
        this.stateQueryService = stateQueryService;
        this.zoneService = zoneService;
        this.zoneQueryService = zoneQueryService;
        this.hubService = hubService;
        this.hubQueryService = hubQueryService;
    }

    /**
     * POST  /pincodes : Create a new pincode.
     *
     * @param pincodeDTO the pincodeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pincodeDTO, or with status 400 (Bad Request) if the pincode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pincodes")
    @Timed
    public ResponseEntity<PincodeDTO> createPincode(@Valid @RequestBody PincodeDTO pincodeDTO) throws URISyntaxException {
        log.debug("REST request to save Pincode : {}", pincodeDTO);
        if (pincodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new pincode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PincodeDTO result = pincodeService.save(pincodeDTO);
        return ResponseEntity.created(new URI("/api/pincodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pincodes : Updates an existing pincode.
     *
     * @param pincodeDTO the pincodeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pincodeDTO,
     * or with status 400 (Bad Request) if the pincodeDTO is not valid,
     * or with status 500 (Internal Server Error) if the pincodeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pincodes")
    @Timed
    public ResponseEntity<PincodeDTO> updatePincode(@Valid @RequestBody PincodeDTO pincodeDTO) throws URISyntaxException {
        log.debug("REST request to update Pincode : {}", pincodeDTO);
        if (pincodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PincodeDTO result = pincodeService.save(pincodeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pincodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pincodes : get all the pincodes.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of pincodes in body
     */
    @GetMapping("/pincodes")
    @Timed
    public ResponseEntity<List<PincodeDTO>> getAllPincodes(PincodeCriteria criteria) {
        log.debug("REST request to get Pincodes by criteria: {}", criteria);
        List<PincodeDTO> entityList = pincodeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /pincodes/:id : get the "id" pincode.
     *
     * @param id the id of the pincodeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pincodeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pincodes/{id}")
    @Timed
    public ResponseEntity<PincodeDTO> getPincode(@PathVariable Long id) {
        log.debug("REST request to get Pincode : {}", id);
        Optional<PincodeDTO> pincodeDTO = pincodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pincodeDTO);
    }

    /**
     * DELETE  /pincodes/:id : delete the "id" pincode.
     *
     * @param id the id of the pincodeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pincodes/{id}")
    @Timed
    public ResponseEntity<Void> deletePincode(@PathVariable Long id) {
        log.debug("REST request to delete Pincode : {}", id);
        pincodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pincodes?query=:query : search for the pincode corresponding
     * to the query.
     *
     * @param query the query of the pincode search
     * @return the result of the search
     */
    @GetMapping("/_search/pincodes")
    @Timed
    public List<PincodeDTO> searchPincodes(@RequestParam String query) {
        log.debug("REST request to search Pincodes for query {}", query);
        return pincodeService.search(query);
    }
    
    /**
     * SEARCH  /_search/pincodes?query=:query : search for the pincode corresponding
     * to the query.
     *
     * @param query the query of the pincode search
     * @return the result of the search
     */
    @GetMapping("/_search/pincodes/name")
    @Timed
    public List<PincodeDTO> searchPincodesByName(@RequestParam String query) {
        log.debug("REST request to search PincodesByName for query {}", query);
        List<PincodeDTO> pincodes = pincodeService.searchByPincode(query);
        pincodes.forEach(pincode -> {
        	pincode.setCityName(cityService.findOne(pincode.getCityId()).get().getName());
        	pincode.setStateName(cityService.findOne(pincode.getStateId()).get().getName());
        	pincode.setZoneName(cityService.findOne(pincode.getZoneId()).get().getName());
        	pincode.setHubName(cityService.findOne(pincode.getHubId()).get().getName());
        });
        return pincodes;
    }
    
    @GetMapping("/pincodes/download")
	public void download(HttpServletResponse response) {
		log.debug("REST pincodes download: {}");
		try {

			List<PincodeDTO> pincodes = pincodeService.findAll();
			pincodes.forEach(pincode -> {
				if (pincode.getCityId() != null)
					pincode.setCityName(cityService.findOne(pincode.getCityId()).get().getName());

				if (pincode.getStateId() != null)
					pincode.setStateName(stateService.findOne(pincode.getStateId()).get().getName());

				if (pincode.getZoneId() != null)
					pincode.setZoneName(zoneService.findOne(pincode.getZoneId()).get().getName());

				if (pincode.getHubId() != null)
					pincode.setHubName(hubService.findOne(pincode.getHubId()).get().getName());
			});

			FileInputStream file = pincodeExcelUtil.createExcel(pincodes);

			// Set the content type and attachment header.
			response.addHeader("Content-disposition", "attachment;filename=pincodes.xls");
			response.setContentType("application/vnd.ms-excel");

			// get your file as InputStream
			InputStream is = file;
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception ex) {
			log.info("Error writing file to output stream. Filename was '{}'", ex);
			String responseToClient = ex.getMessage();
			try {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write(responseToClient);
				response.getWriter().flush();
				response.getWriter().close();
			} catch (Exception e) {
				throw new RuntimeException("IOError writing file to output stream");
			}
		}
	}
    
    @RequestMapping(value = "/pincodes/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('" + AuthoritiesConstants.MANAGER + "')")
	public @ResponseBody ResponseEntity<PincodeDTO> handleFileUpload(
			@RequestParam(value = "file", required = false) MultipartFile file) throws URISyntaxException {
		PincodeDTO result = null;
		try {
			List<PincodeDTO> pincodeDTOList = Poiji.fromExcel(new ByteArrayInputStream(file.getBytes()),
					PoijiExcelType.XLS, PincodeDTO.class);

			Future<List<PincodeDTO>> savedEntities;

			if (CollectionUtils.isNotEmpty(pincodeDTOList)) {
				pincodeDTOList.forEach(pincode -> {
					if (pincode.getCityName() != null) {
						CityCriteria cityCriteria = new CityCriteria();
						StringFilter stringFilter = new StringFilter();
						stringFilter.setEquals(pincode.getCityName());
						cityCriteria.setName(stringFilter);
						List<CityDTO> cityList = cityQueryService.findByCriteria(cityCriteria);
						if (CollectionUtils.isNotEmpty(cityList))
							pincode.setCityId(cityList.get(0).getId());
					}
					if (pincode.getStateName() != null) {
						StateCriteria stateCriteria = new StateCriteria();
						StringFilter stringFilter = new StringFilter();
						stringFilter.setEquals(pincode.getStateName());
						stateCriteria.setName(stringFilter);
						List<StateDTO> stateList = stateQueryService.findByCriteria(stateCriteria);
						if (CollectionUtils.isNotEmpty(stateList))
							pincode.setCityId(stateList.get(0).getId());
					}
					if (pincode.getZoneName() != null) {
						ZoneCriteria zoneCriteria = new ZoneCriteria();
						StringFilter stringFilter = new StringFilter();
						stringFilter.setEquals(pincode.getZoneName());
						zoneCriteria.setName(stringFilter);
						List<ZoneDTO> zoneList = zoneQueryService.findByCriteria(zoneCriteria);
						if (CollectionUtils.isNotEmpty(zoneList))
							pincode.setCityId(zoneList.get(0).getId());
					}
					if (pincode.getHubName() != null) {
						HubCriteria hubCriteria = new HubCriteria();
						StringFilter stringFilter = new StringFilter();
						stringFilter.setEquals(pincode.getHubName());
						hubCriteria.setName(stringFilter);
						List<HubDTO> hubList = hubQueryService.findByCriteria(hubCriteria);
						if (CollectionUtils.isNotEmpty(hubList))
							pincode.setCityId(hubList.get(0).getId());
					}
				});

				savedEntities = bulkSave(pincodeDTOList);
				if (savedEntities.get().size() != pincodeDTOList.size())
					log.error("few pincodes weren't saved");

				return ResponseEntity.created(new URI("/pincodes/upload/")).body(result);

			} else {
				throw new BadRequestAlertException("A new vendor File cannot be empty", ENTITY_NAME, "");
			}
		} catch (RuntimeException | IOException e) {
			log.error("Error while uploading.", e);
			throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "");
		} catch (Exception e) {
			log.error("Error while uploading.", e);
			throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "");
		}
	}
    
    @Async
	public Future<List<PincodeDTO>> bulkSave(List<PincodeDTO> entities) {
		int size = entities.size();
		List<PincodeDTO> savedEntities = new ArrayList<>(size);
		try {
			for (int i = 0; i < size; i += batchSize) {
				int toIndex = i + (((i + batchSize) < size) ? batchSize : size - i);
				savedEntities.addAll(processBatch(entities.subList(i, toIndex)));
			}
		} catch (Exception e) {
			log.error(" bulkSave failed " + e);
		}
		if (savedEntities.size() != entities.size()) {
			log.error("few entities are not saved");
		} else {
			log.debug("entities are saved");
		}
		return new AsyncResult<List<PincodeDTO>>(savedEntities);
	}
    
    protected List<PincodeDTO> processBatch(List<PincodeDTO> batch) {
		List<PincodeDTO> list = pincodeService.upload(batch);
		return list;
	}

}
