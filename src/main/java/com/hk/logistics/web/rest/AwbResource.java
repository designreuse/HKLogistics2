package com.hk.logistics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hk.logistics.service.AwbService;
import com.hk.logistics.web.rest.errors.BadRequestAlertException;
import com.hk.logistics.web.rest.util.HeaderUtil;
import com.hk.logistics.service.dto.AwbDTO;
import com.hk.logistics.service.dto.AwbCriteria;
import com.hk.logistics.service.AwbQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

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

    public AwbResource(AwbService awbService, AwbQueryService awbQueryService) {
        this.awbService = awbService;
        this.awbQueryService = awbQueryService;
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
       // model.addAttribute("Awb", awbQueryService.findByCriteria(criteria));
        //return "awbExcelView";
        log.debug("REST Awb download: {}", criteria.getAwbStatusId(), criteria.getVendorWHCourierMappingId());
        String filepath = "/home/shashankshukla/shashank/Sample-File.xlsx";


        try {
            FileInputStream file = new FileInputStream(filepath);
            // Set the content type and attachment header.
            response.addHeader("Content-disposition", "attachment;filename=Sample-File.xlsx");
            response.setContentType("application/vnd.ms-excel");

            // get your file as InputStream
            InputStream is = file;
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception ex) {
            log.info("Error writing file to output stream. Filename was '{}'", ex);
            String responseToClient= ex.getMessage();
            try {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(responseToClient);
                response.getWriter().flush();
                response.getWriter().close();
            }catch (Exception e){
                throw new RuntimeException("IOError writing file to output stream");
            }

        }

    }

}
