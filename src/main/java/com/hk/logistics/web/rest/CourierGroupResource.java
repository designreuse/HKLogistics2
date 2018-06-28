package com.hk.logistics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hk.logistics.service.CourierGroupService;
import com.hk.logistics.web.rest.errors.BadRequestAlertException;
import com.hk.logistics.web.rest.util.HeaderUtil;
import com.hk.logistics.service.dto.CourierGroupDTO;
import com.hk.logistics.service.dto.CourierGroupCriteria;
import com.hk.logistics.service.CourierGroupQueryService;
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
 * REST controller for managing CourierGroup.
 */
@RestController
@RequestMapping("/api")
public class CourierGroupResource {

    private final Logger log = LoggerFactory.getLogger(CourierGroupResource.class);

    private static final String ENTITY_NAME = "courierGroup";

    private final CourierGroupService courierGroupService;

    private final CourierGroupQueryService courierGroupQueryService;

    public CourierGroupResource(CourierGroupService courierGroupService, CourierGroupQueryService courierGroupQueryService) {
        this.courierGroupService = courierGroupService;
        this.courierGroupQueryService = courierGroupQueryService;
    }

    /**
     * POST  /courier-groups : Create a new courierGroup.
     *
     * @param courierGroupDTO the courierGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new courierGroupDTO, or with status 400 (Bad Request) if the courierGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/courier-groups")
    @Timed
    public ResponseEntity<CourierGroupDTO> createCourierGroup(@RequestBody CourierGroupDTO courierGroupDTO) throws URISyntaxException {
        log.debug("REST request to save CourierGroup : {}", courierGroupDTO);
        if (courierGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new courierGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourierGroupDTO result = courierGroupService.save(courierGroupDTO);
        return ResponseEntity.created(new URI("/api/courier-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /courier-groups : Updates an existing courierGroup.
     *
     * @param courierGroupDTO the courierGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated courierGroupDTO,
     * or with status 400 (Bad Request) if the courierGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the courierGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/courier-groups")
    @Timed
    public ResponseEntity<CourierGroupDTO> updateCourierGroup(@RequestBody CourierGroupDTO courierGroupDTO) throws URISyntaxException {
        log.debug("REST request to update CourierGroup : {}", courierGroupDTO);
        if (courierGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CourierGroupDTO result = courierGroupService.save(courierGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, courierGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /courier-groups : get all the courierGroups.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of courierGroups in body
     */
    @GetMapping("/courier-groups")
    @Timed
    public ResponseEntity<List<CourierGroupDTO>> getAllCourierGroups(CourierGroupCriteria criteria) {
        log.debug("REST request to get CourierGroups by criteria: {}", criteria);
        List<CourierGroupDTO> entityList = courierGroupQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /courier-groups/:id : get the "id" courierGroup.
     *
     * @param id the id of the courierGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the courierGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/courier-groups/{id}")
    @Timed
    public ResponseEntity<CourierGroupDTO> getCourierGroup(@PathVariable Long id) {
        log.debug("REST request to get CourierGroup : {}", id);
        Optional<CourierGroupDTO> courierGroupDTO = courierGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courierGroupDTO);
    }

    /**
     * DELETE  /courier-groups/:id : delete the "id" courierGroup.
     *
     * @param id the id of the courierGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/courier-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteCourierGroup(@PathVariable Long id) {
        log.debug("REST request to delete CourierGroup : {}", id);
        courierGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/courier-groups?query=:query : search for the courierGroup corresponding
     * to the query.
     *
     * @param query the query of the courierGroup search
     * @return the result of the search
     */
    @GetMapping("/_search/courier-groups")
    @Timed
    public List<CourierGroupDTO> searchCourierGroups(@RequestParam String query) {
        log.debug("REST request to search CourierGroups for query {}", query);
        return courierGroupService.search(query);
    }

}
