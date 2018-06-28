package com.hk.logistics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hk.logistics.service.HubService;
import com.hk.logistics.web.rest.errors.BadRequestAlertException;
import com.hk.logistics.web.rest.util.HeaderUtil;
import com.hk.logistics.service.dto.HubDTO;
import com.hk.logistics.service.dto.HubCriteria;
import com.hk.logistics.service.HubQueryService;
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
 * REST controller for managing Hub.
 */
@RestController
@RequestMapping("/api")
public class HubResource {

    private final Logger log = LoggerFactory.getLogger(HubResource.class);

    private static final String ENTITY_NAME = "hub";

    private final HubService hubService;

    private final HubQueryService hubQueryService;

    public HubResource(HubService hubService, HubQueryService hubQueryService) {
        this.hubService = hubService;
        this.hubQueryService = hubQueryService;
    }

    /**
     * POST  /hubs : Create a new hub.
     *
     * @param hubDTO the hubDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hubDTO, or with status 400 (Bad Request) if the hub has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hubs")
    @Timed
    public ResponseEntity<HubDTO> createHub(@Valid @RequestBody HubDTO hubDTO) throws URISyntaxException {
        log.debug("REST request to save Hub : {}", hubDTO);
        if (hubDTO.getId() != null) {
            throw new BadRequestAlertException("A new hub cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HubDTO result = hubService.save(hubDTO);
        return ResponseEntity.created(new URI("/api/hubs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hubs : Updates an existing hub.
     *
     * @param hubDTO the hubDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hubDTO,
     * or with status 400 (Bad Request) if the hubDTO is not valid,
     * or with status 500 (Internal Server Error) if the hubDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hubs")
    @Timed
    public ResponseEntity<HubDTO> updateHub(@Valid @RequestBody HubDTO hubDTO) throws URISyntaxException {
        log.debug("REST request to update Hub : {}", hubDTO);
        if (hubDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HubDTO result = hubService.save(hubDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hubDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hubs : get all the hubs.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of hubs in body
     */
    @GetMapping("/hubs")
    @Timed
    public ResponseEntity<List<HubDTO>> getAllHubs(HubCriteria criteria) {
        log.debug("REST request to get Hubs by criteria: {}", criteria);
        List<HubDTO> entityList = hubQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /hubs/:id : get the "id" hub.
     *
     * @param id the id of the hubDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hubDTO, or with status 404 (Not Found)
     */
    @GetMapping("/hubs/{id}")
    @Timed
    public ResponseEntity<HubDTO> getHub(@PathVariable Long id) {
        log.debug("REST request to get Hub : {}", id);
        Optional<HubDTO> hubDTO = hubService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hubDTO);
    }

    /**
     * DELETE  /hubs/:id : delete the "id" hub.
     *
     * @param id the id of the hubDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hubs/{id}")
    @Timed
    public ResponseEntity<Void> deleteHub(@PathVariable Long id) {
        log.debug("REST request to delete Hub : {}", id);
        hubService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hubs?query=:query : search for the hub corresponding
     * to the query.
     *
     * @param query the query of the hub search
     * @return the result of the search
     */
    @GetMapping("/_search/hubs")
    @Timed
    public List<HubDTO> searchHubs(@RequestParam String query) {
        log.debug("REST request to search Hubs for query {}", query);
        return hubService.search(query);
    }

}
