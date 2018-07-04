package com.hk.logistics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hk.logistics.service.PincodeRegionZoneService;
import com.hk.logistics.service.RegionTypeQueryService;
import com.hk.logistics.service.SourceDestinationMappingQueryService;
import com.hk.logistics.service.SourceDestinationMappingService;
import com.hk.logistics.web.rest.errors.BadRequestAlertException;
import com.hk.logistics.web.rest.util.HeaderUtil;
import com.hk.logistics.web.rest.util.PaginationUtil;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.hk.logistics.service.dto.PincodeRegionZoneDTO;
import com.hk.logistics.service.dto.RegionTypeCriteria;
import com.hk.logistics.service.dto.SourceDestinationMappingCriteria;
import com.hk.logistics.service.dto.SourceDestinationMappingDTO;
import com.hk.logistics.service.dto.StateCriteria;
import com.hk.logistics.service.dto.StateDTO;
import com.hk.logistics.service.dto.ZoneCriteria;
import com.hk.logistics.service.dto.ZoneDTO;
import com.hk.logistics.service.dto.CityCriteria;
import com.hk.logistics.service.dto.CityDTO;
import com.hk.logistics.service.dto.CourierCriteria;
import com.hk.logistics.service.dto.CourierDTO;
import com.hk.logistics.service.dto.CourierGroupCriteria;
import com.hk.logistics.service.dto.HubCriteria;
import com.hk.logistics.service.dto.HubDTO;
import com.hk.logistics.service.dto.PincodeDTO;
import com.hk.logistics.service.dto.PincodeRegionZoneCriteria;
import com.hk.logistics.domain.SourceDestinationMapping;
import com.hk.logistics.repository.SourceDestinationMappingRepository;
import com.hk.logistics.security.AuthoritiesConstants;
import com.hk.logistics.service.CourierGroupQueryService;
import com.hk.logistics.service.PincodeRegionZoneQueryService;

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletResponse;

import static org.elasticsearch.index.query.QueryBuilders.*;
import com.hk.logistics.service.dto.CourierGroupDTO;
import com.hk.logistics.service.dto.RegionTypeDTO;

/**
 * REST controller for managing PincodeRegionZone.
 */
@RestController
@RequestMapping("/api")
public class PincodeRegionZoneResource {

    private final Logger log = LoggerFactory.getLogger(PincodeRegionZoneResource.class);

    private static final String ENTITY_NAME = "pincodeRegionZone";

    private final PincodeRegionZoneService pincodeRegionZoneService;

    private final PincodeRegionZoneQueryService pincodeRegionZoneQueryService;
    
    @Autowired
    private SourceDestinationMappingRepository sourceDestinationMappingRepository;
    
    @Autowired
    private SourceDestinationMappingService sourceDestinationMappingService;
    
    @Autowired
    private SourceDestinationMappingQueryService sourceDestinationMappingQueryService;
    
    @Autowired
    private CourierGroupQueryService courierGroupQueryService;

    @Autowired
    private RegionTypeQueryService regionTypeQueryService;
    

    @Value("${batchSize:1000}")
    private int batchSize;

	public PincodeRegionZoneResource(PincodeRegionZoneService pincodeRegionZoneService,
			PincodeRegionZoneQueryService pincodeRegionZoneQueryService) {
        this.pincodeRegionZoneService = pincodeRegionZoneService;
        this.pincodeRegionZoneQueryService = pincodeRegionZoneQueryService;
    }

    /**
     * POST  /pincode-region-zones : Create a new pincodeRegionZone.
     *
     * @param pincodeRegionZoneDTO the pincodeRegionZoneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pincodeRegionZoneDTO, or with status 400 (Bad Request) if the pincodeRegionZone has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pincode-region-zones")
    @Timed
    public ResponseEntity<PincodeRegionZoneDTO> createPincodeRegionZone(@RequestBody PincodeRegionZoneDTO pincodeRegionZoneDTO) throws URISyntaxException {
        log.debug("REST request to save PincodeRegionZone : {}", pincodeRegionZoneDTO);
        if (pincodeRegionZoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new pincodeRegionZone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PincodeRegionZoneDTO result = pincodeRegionZoneService.save(pincodeRegionZoneDTO);
        return ResponseEntity.created(new URI("/api/pincode-region-zones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pincode-region-zones : Updates an existing pincodeRegionZone.
     *
     * @param pincodeRegionZoneDTO the pincodeRegionZoneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pincodeRegionZoneDTO,
     * or with status 400 (Bad Request) if the pincodeRegionZoneDTO is not valid,
     * or with status 500 (Internal Server Error) if the pincodeRegionZoneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pincode-region-zones")
    @Timed
    public ResponseEntity<PincodeRegionZoneDTO> updatePincodeRegionZone(@RequestBody PincodeRegionZoneDTO pincodeRegionZoneDTO) throws URISyntaxException {
        log.debug("REST request to update PincodeRegionZone : {}", pincodeRegionZoneDTO);
        if (pincodeRegionZoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PincodeRegionZoneDTO result = pincodeRegionZoneService.save(pincodeRegionZoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pincodeRegionZoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pincode-region-zones : get all the pincodeRegionZones.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of pincodeRegionZones in body
     */
    @GetMapping("/pincode-region-zones")
    @Timed
    public ResponseEntity<List<PincodeRegionZoneDTO>> getAllPincodeRegionZones(PincodeRegionZoneCriteria criteria) {
        log.debug("REST request to get PincodeRegionZones by criteria: {}", criteria);
        List<PincodeRegionZoneDTO> entityList = pincodeRegionZoneQueryService.findByCriteria(criteria);
        entityList.forEach(entity->{
        	SourceDestinationMappingDTO sourceDestinationMappingDTO =  sourceDestinationMappingService.findOne(entity.getSourceDestinationMappingId()).get();
        	entity.setSourcePincode(sourceDestinationMappingDTO.getSourcePincode());
        	entity.setDestinationPincode(sourceDestinationMappingDTO.getDestinationPincode());
        });
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /pincode-region-zones/:id : get the "id" pincodeRegionZone.
     *
     * @param id the id of the pincodeRegionZoneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pincodeRegionZoneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pincode-region-zones/{id}")
    @Timed
    public ResponseEntity<PincodeRegionZoneDTO> getPincodeRegionZone(@PathVariable Long id) {
        log.debug("REST request to get PincodeRegionZone : {}", id);
        Optional<PincodeRegionZoneDTO> pincodeRegionZoneDTO = pincodeRegionZoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pincodeRegionZoneDTO);
    }

    /**
     * DELETE  /pincode-region-zones/:id : delete the "id" pincodeRegionZone.
     *
     * @param id the id of the pincodeRegionZoneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pincode-region-zones/{id}")
    @Timed
    public ResponseEntity<Void> deletePincodeRegionZone(@PathVariable Long id) {
        log.debug("REST request to delete PincodeRegionZone : {}", id);
        pincodeRegionZoneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pincode-region-zones?query=:query : search for the pincodeRegionZone corresponding
     * to the query.
     *
     * @param query the query of the pincodeRegionZone search
     * @return the result of the search
     */
    @GetMapping("/_search/pincode-region-zones")
    @Timed
    public List<PincodeRegionZoneDTO> searchPincodeRegionZones(@RequestParam String query) {
        log.debug("REST request to search PincodeRegionZones for query {}", query);
        return pincodeRegionZoneService.search(query);
    }
    
    @GetMapping("/pincode-region-zones/filter")
    @Timed
	public List<PincodeRegionZoneDTO> filter(HttpServletResponse response, PincodeRegionZoneCriteria criteria) {
		log.debug("REST request to filter Couriers for criteria {}", criteria);

		List<SourceDestinationMappingDTO> sourceDestinationMappingList = new ArrayList<SourceDestinationMappingDTO>();
		List<PincodeRegionZoneDTO> pincodeRegionZoneList = new ArrayList<PincodeRegionZoneDTO>();
		if (criteria.getSourcePincode() != null && criteria.getDestinationPincode() != null) {
			SourceDestinationMappingCriteria sourceDestinationMappingCriteria = new SourceDestinationMappingCriteria();
			sourceDestinationMappingCriteria.setDestinationPincode(criteria.getDestinationPincode());
			sourceDestinationMappingCriteria.setSourcePincode(criteria.getSourcePincode());

			sourceDestinationMappingList = sourceDestinationMappingQueryService
					.findByCriteria(sourceDestinationMappingCriteria);
		}

		else if (criteria.getDestinationPincode() != null) {
			SourceDestinationMappingCriteria sourceDestinationMappingCriteria = new SourceDestinationMappingCriteria();
			sourceDestinationMappingCriteria.setDestinationPincode(criteria.getDestinationPincode());
			sourceDestinationMappingList = sourceDestinationMappingQueryService
					.findByCriteria(sourceDestinationMappingCriteria);
		}

		for (SourceDestinationMappingDTO sourceDestinationMapping : sourceDestinationMappingList) {
			LongFilter sourceDestinationMappingIdFilter = new LongFilter();
			sourceDestinationMappingIdFilter.setEquals(sourceDestinationMapping.getId());

			criteria.setSourceDestinationMappingId(sourceDestinationMappingIdFilter);
			pincodeRegionZoneList.addAll(pincodeRegionZoneQueryService.findByCriteria(criteria));

			pincodeRegionZoneList.forEach(pincodeRegionZone -> {
				pincodeRegionZone.setSourcePincode(sourceDestinationMapping.getSourcePincode());
				pincodeRegionZone.setDestinationPincode(sourceDestinationMapping.getDestinationPincode());
			});
		}

		return pincodeRegionZoneList;
	}
    
    @RequestMapping(value = "/pincode-region-zones/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('" + AuthoritiesConstants.MANAGER + "')")
	public @ResponseBody ResponseEntity<PincodeDTO> handleFileUpload(
			@RequestParam(value = "file", required = false) MultipartFile file) throws URISyntaxException {
		PincodeDTO result = null;
		try {
			List<PincodeRegionZoneDTO> pincodeRegionZoneDTOList = Poiji.fromExcel(new ByteArrayInputStream(file.getBytes()),
					PoijiExcelType.XLS, PincodeRegionZoneDTO.class);

			Future<List<PincodeRegionZoneDTO>> savedEntities;

			if (CollectionUtils.isNotEmpty(pincodeRegionZoneDTOList)) {
				pincodeRegionZoneDTOList.forEach(pincodeRegionZone -> {
					if (pincodeRegionZone.getSourcePincode() != null) {
						SourceDestinationMappingCriteria sourceDestinationMappingCriteria = new SourceDestinationMappingCriteria();
						StringFilter sourcePincodeFilter = new StringFilter();
						sourcePincodeFilter.setEquals(pincodeRegionZone.getSourcePincode());
						StringFilter destinationPincodeFilter = new StringFilter();
						destinationPincodeFilter.setEquals(pincodeRegionZone.getDestinationPincode());
						sourceDestinationMappingCriteria.setSourcePincode(sourcePincodeFilter);
						sourceDestinationMappingCriteria.setDestinationPincode(destinationPincodeFilter);
						List<SourceDestinationMappingDTO> sourceDestinationMappingList = sourceDestinationMappingQueryService.findByCriteria(sourceDestinationMappingCriteria);
						if (CollectionUtils.isNotEmpty(sourceDestinationMappingList))
							pincodeRegionZone.setSourceDestinationMappingId(sourceDestinationMappingList.get(0).getId());
					}
					if (pincodeRegionZone.getCourierGroupName() != null) {
						CourierGroupCriteria courierGroupCriteria = new CourierGroupCriteria();
						StringFilter stringFilter = new StringFilter();
						stringFilter.setEquals(pincodeRegionZone.getCourierGroupName());
						courierGroupCriteria.setName(stringFilter);
						List<CourierGroupDTO> courierGroupList = courierGroupQueryService.findByCriteria(courierGroupCriteria);
						if (CollectionUtils.isNotEmpty(courierGroupList))
							pincodeRegionZone.setCourierGroupId(courierGroupList.get(0).getId());
					}
					if (pincodeRegionZone.getRegionTypeName() != null) {
						RegionTypeCriteria regionTypeCriteria = new RegionTypeCriteria();
						StringFilter stringFilter = new StringFilter();
						stringFilter.setEquals(pincodeRegionZone.getRegionTypeName());
						regionTypeCriteria.setName(stringFilter);
						List<RegionTypeDTO> regionTypeList = regionTypeQueryService.findByCriteria(regionTypeCriteria);
						if (CollectionUtils.isNotEmpty(regionTypeList))
							pincodeRegionZone.setRegionTypeId(regionTypeList.get(0).getId());
					}
				});

				savedEntities = bulkSave(pincodeRegionZoneDTOList);
				if (savedEntities.get().size() != pincodeRegionZoneDTOList.size())
					log.error("few PRZ weren't saved");

				return ResponseEntity.created(new URI("/pincode-region-zones/upload/")).body(result);

			} else {
				throw new BadRequestAlertException("A new PRZ File cannot be empty", ENTITY_NAME, "");
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
		public Future<List<PincodeRegionZoneDTO>> bulkSave(List<PincodeRegionZoneDTO> entities) {
			int size = entities.size();
			List<PincodeRegionZoneDTO> savedEntities = new ArrayList<>(size);
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
			return new AsyncResult<List<PincodeRegionZoneDTO>>(savedEntities);
		}
	    
	    protected List<PincodeRegionZoneDTO> processBatch(List<PincodeRegionZoneDTO> batch) {
			List<PincodeRegionZoneDTO> list = pincodeRegionZoneService.upload(batch);
			return list;
		}


}
