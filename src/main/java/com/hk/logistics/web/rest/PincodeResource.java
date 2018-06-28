package com.hk.logistics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hk.logistics.service.PincodeService;
import com.hk.logistics.web.rest.errors.BadRequestAlertException;
import com.hk.logistics.web.rest.util.HeaderUtil;
import com.hk.logistics.service.dto.PincodeDTO;
import com.hk.logistics.service.dto.PincodeCriteria;
import com.hk.logistics.service.PincodeQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

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

    public PincodeResource(PincodeService pincodeService, PincodeQueryService pincodeQueryService) {
        this.pincodeService = pincodeService;
        this.pincodeQueryService = pincodeQueryService;
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

}
