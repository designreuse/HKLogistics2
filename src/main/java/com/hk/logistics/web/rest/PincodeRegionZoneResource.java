package com.hk.logistics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hk.logistics.service.PincodeRegionZoneService;
import com.hk.logistics.web.rest.errors.BadRequestAlertException;
import com.hk.logistics.web.rest.util.HeaderUtil;
import com.hk.logistics.service.dto.PincodeRegionZoneDTO;
import com.hk.logistics.service.dto.PincodeRegionZoneCriteria;
import com.hk.logistics.service.PincodeRegionZoneQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

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

    public PincodeRegionZoneResource(PincodeRegionZoneService pincodeRegionZoneService, PincodeRegionZoneQueryService pincodeRegionZoneQueryService) {
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

}
