package com.hk.logistics.web.rest;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.hk.logistics.domain.Awb;
import com.hk.logistics.domain.AwbExcelPojo;
import com.hk.logistics.security.AuthoritiesConstants;
import com.hk.logistics.service.AwbQueryService;
import com.hk.logistics.service.AwbService;
import com.hk.logistics.service.dto.AwbCriteria;
import com.hk.logistics.service.dto.AwbDTO;
import com.hk.logistics.service.dto.VendorWHCourierMappingDTO;
import com.hk.logistics.util.AwbExcelUtil;
import com.hk.logistics.web.rest.errors.BadRequestAlertException;
import com.hk.logistics.web.rest.util.HeaderUtil;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Awb.
 */
@RestController
@RequestMapping("/api")
public class AwbResource {

    private final Logger log = LoggerFactory.getLogger(AwbResource.class);

    private static final String ENTITY_NAME = "awb";

    private final AwbService awbService;

    private final AwbQueryService awbQueryService;
    
    @Autowired
    private final AwbExcelUtil awbExcelUtil;

    @Value("${batchSize:1000}")
    private int batchSize;

    public AwbResource(AwbService awbService, AwbQueryService awbQueryService, AwbExcelUtil awbExcelUtil) {
        this.awbService = awbService;
        this.awbQueryService = awbQueryService;
        this.awbExcelUtil = awbExcelUtil;
    }
    
	/**
     * POST  /awbs : Create a new awb.
     *
     * @param awbDTO the awbDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new awbDTO, or with status 400 (Bad Request) if the awb has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/awbs")
    @Timed
    public ResponseEntity<AwbDTO> createAwb(@Valid @RequestBody AwbDTO awbDTO) throws URISyntaxException {
        log.debug("REST request to save Awb : {}", awbDTO);
        if (awbDTO.getId() != null) {
            throw new BadRequestAlertException("A new awb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AwbDTO result = awbService.save(awbDTO);
        return ResponseEntity.created(new URI("/api/awbs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /awbs : Updates an existing awb.
     *
     * @param awbDTO the awbDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated awbDTO,
     * or with status 400 (Bad Request) if the awbDTO is not valid,
     * or with status 500 (Internal Server Error) if the awbDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/awbs")
    @Timed
    public ResponseEntity<AwbDTO> updateAwb(@Valid @RequestBody AwbDTO awbDTO) throws URISyntaxException {
        log.debug("REST request to update Awb : {}", awbDTO);
        if (awbDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AwbDTO result = awbService.save(awbDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, awbDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /awbs : get all the awbs.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of awbs in body
     */
    @GetMapping("/awbs")
    @Timed
    public ResponseEntity<List<AwbDTO>> getAllAwbs(AwbCriteria criteria) {
        log.debug("REST request to get Awbs by criteria: {}", criteria);
        List<AwbDTO> entityList = awbQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /awbs/:id : get the "id" awb.
     *
     * @param id the id of the awbDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the awbDTO, or with status 404 (Not Found)
     */
    @GetMapping("/awbs/{id}")
    @Timed
    public ResponseEntity<AwbDTO> getAwb(@PathVariable Long id) {
        log.debug("REST request to get Awb : {}", id);
        Optional<AwbDTO> awbDTO = awbService.findOne(id);
        return ResponseUtil.wrapOrNotFound(awbDTO);
    }

    /**
     * DELETE  /awbs/:id : delete the "id" awb.
     *
     * @param id the id of the awbDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/awbs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAwb(@PathVariable Long id) {
        log.debug("REST request to delete Awb : {}", id);
        awbService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/awbs?query=:query : search for the awb corresponding
     * to the query.
     *
     * @param query the query of the awb search
     * @return the result of the search
     */
    @GetMapping("/_search/awbs")
    @Timed
    public List<AwbDTO> searchAwbs(@RequestParam String query) {
        log.debug("REST request to search Awbs for query {}", query);
        return awbService.search(query);
    }
    
    @GetMapping("/awbs/download")
	public void handleForexRequest(HttpServletResponse response, AwbCriteria criteria) {
		log.debug("REST Awb download: {}", criteria.getAwbStatusId(), criteria.getVendorWHCourierMappingId());
		try {
			List<AwbExcelPojo> pojoList = awbService.getAwbsForExcelDownload(criteria);
			FileInputStream file = awbExcelUtil.createExcel(pojoList);

			// Set the content type and attachment header.
			response.addHeader("Content-disposition", "attachment;filename=Sample-File.xls");
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
    
	@RequestMapping(value = "/awbs/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('" + AuthoritiesConstants.MANAGER + "')")
	public @ResponseBody ResponseEntity<AwbDTO> handleFileUpload(
			@RequestParam(value = "file", required = false) MultipartFile file) throws URISyntaxException {
		AwbDTO result = null;
		try {
			List<AwbExcelPojo> awbUploadModelList = Poiji.fromExcel(new ByteArrayInputStream(file.getBytes()),
					PoijiExcelType.XLS, AwbExcelPojo.class);

			Future<List<AwbDTO>> savedEntities;

			if (CollectionUtils.isNotEmpty(awbUploadModelList)) {
				List<AwbDTO> awbDtoList = mapToAwbDto(awbUploadModelList);

				savedEntities = bulkSave(awbDtoList);
				if (savedEntities.get().size() != awbUploadModelList.size())
					log.error("few awbs weren't saved");

				return ResponseEntity.created(new URI("/api/upload/")).body(result);

			} else {
				throw new BadRequestAlertException("A new vendor File cannot be empty", ENTITY_NAME, "idexists");
			}
		} catch (RuntimeException | IOException e) {
			log.error("Error while uploading.", e);
			throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "idexists");
		} catch (Exception e) {
			log.error("Error while uploading.", e);
			throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "idexists");
		}
	}

	private List<AwbDTO> mapToAwbDto(List<AwbExcelPojo> awbUploadModelList) {
		List<AwbDTO> list = awbUploadModelList.parallelStream().map(awbUploadModel -> convertToAwbDto(awbUploadModel))
				.collect(Collectors.toList());
		return list;
	}

	private AwbDTO convertToAwbDto(AwbExcelPojo awbUploadModel) {
		AwbDTO dto = new AwbDTO();
		dto.setAwbNumber(awbUploadModel.getAwbNumber());
		dto.setCod(awbUploadModel.getCod());
		dto.setAwbStatusId(2L);

		VendorWHCourierMappingDTO vendorWHCourierMapping = null;
		if (awbUploadModel.getWhId() != null)
			vendorWHCourierMapping = awbService.getVendorWHCourierMappingByCourierAndWHId(awbUploadModel.getCourierId(),
					awbUploadModel.getWhId());

		if (awbUploadModel.getVendorShortCode() != null)
			vendorWHCourierMapping = awbService.getVendorWHCourierMappingByCourierAndVendorShortCode(
					awbUploadModel.getCourierId(), awbUploadModel.getVendorShortCode());

		dto.setVendorWHCourierMappingId(vendorWHCourierMapping.getId());
		
		dto.setCreateDate(LocalDate.now());
		//::TODO confirm
		dto.setAwbBarCode(awbUploadModel.getAwbNumber());

		return dto;
	}

	@Async
	public Future<List<AwbDTO>> bulkSave(List<AwbDTO> entities) {
		int size = entities.size();
		List<AwbDTO> savedEntities = new ArrayList<>(size);
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
		return new AsyncResult<List<AwbDTO>>(savedEntities);
	}

	protected List<AwbDTO> processBatch(List<AwbDTO> batch) {
		List<AwbDTO> list = awbService.upload(batch);
		return list;
	}
	
	@RequestMapping(value = "/awbs/bulk-delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('" + AuthoritiesConstants.MANAGER + "')")
	public @ResponseBody ResponseEntity<AwbDTO> bulkDelete(
			@RequestParam(value = "file", required = false) MultipartFile file) throws URISyntaxException {
		AwbDTO result = null;
		try {
			List<AwbExcelPojo> awbExcelPojoList = Poiji.fromExcel(new ByteArrayInputStream(file.getBytes()),
					PoijiExcelType.XLS, AwbExcelPojo.class);

			List<Long> awbListToBeDeleted = new ArrayList<Long>();
			List<AwbExcelPojo> wrongEntriesInExcel = new ArrayList<AwbExcelPojo>();
			if (CollectionUtils.isNotEmpty(awbExcelPojoList)) {
				{
					for (AwbExcelPojo pojo : awbExcelPojoList) {
						AwbDTO awbFromDb = awbService.isAwbEligibleForDeletion(pojo.getCourierId(), pojo.getAwbNumber(),
								pojo.getWhId(), pojo.getCod());
						if (awbFromDb != null) {
							awbListToBeDeleted.add(awbFromDb.getId());
						} else {
							wrongEntriesInExcel.add(pojo);
						}
					}
				}
				
				if (CollectionUtils.isNotEmpty(awbListToBeDeleted)) {
					log.info(" following awbs will be deleted ");
					awbListToBeDeleted.forEach(id -> awbService.delete(id));
				}

				if (CollectionUtils.isNotEmpty(wrongEntriesInExcel)) {
					log.info(" following awbs coludn't be deleted ");
					wrongEntriesInExcel.forEach(entry -> log.info(entry.getAwbNumber()));
				}
				return ResponseEntity.created(new URI("/api/awbs/bulk-delete")).body(result);

			} else {
				throw new BadRequestAlertException("A new File cannot be empty", ENTITY_NAME, "idexists");
			}
		} catch (RuntimeException | IOException e) {
			log.error("Error while uploading.", e);
			throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "");
		} catch (Exception e) {
			log.error("Error while uploading.", e);
			throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "");
		}
	}

}
