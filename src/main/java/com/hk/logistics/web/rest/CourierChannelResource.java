package com.hk.logistics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hk.logistics.service.CourierChannelService;
import com.hk.logistics.web.rest.errors.BadRequestAlertException;
import com.hk.logistics.web.rest.util.HeaderUtil;
import com.hk.logistics.service.dto.CourierChannelDTO;
import com.hk.logistics.service.dto.CourierChannelCriteria;
import com.hk.logistics.service.CourierChannelQueryService;
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
 * REST controller for managing CourierChannel.
 */
@RestController
@RequestMapping("/api")
public class CourierChannelResource {

    private final Logger log = LoggerFactory.getLogger(CourierChannelResource.class);

    private static final String ENTITY_NAME = "courierChannel";

    private final CourierChannelService courierChannelService;

    private final CourierChannelQueryService courierChannelQueryService;

    public CourierChannelResource(CourierChannelService courierChannelService, CourierChannelQueryService courierChannelQueryService) {
        this.courierChannelService = courierChannelService;
        this.courierChannelQueryService = courierChannelQueryService;
    }

    /**
     * POST  /courier-channels : Create a new courierChannel.
     *
     * @param courierChannelDTO the courierChannelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new courierChannelDTO, or with status 400 (Bad Request) if the courierChannel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/courier-channels")
    @Timed
    public ResponseEntity<CourierChannelDTO> createCourierChannel(@RequestBody CourierChannelDTO courierChannelDTO) throws URISyntaxException {
        log.debug("REST request to save CourierChannel : {}", courierChannelDTO);
        if (courierChannelDTO.getId() != null) {
            throw new BadRequestAlertException("A new courierChannel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourierChannelDTO result = courierChannelService.save(courierChannelDTO);
        return ResponseEntity.created(new URI("/api/courier-channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /courier-channels : Updates an existing courierChannel.
     *
     * @param courierChannelDTO the courierChannelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated courierChannelDTO,
     * or with status 400 (Bad Request) if the courierChannelDTO is not valid,
     * or with status 500 (Internal Server Error) if the courierChannelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/courier-channels")
    @Timed
    public ResponseEntity<CourierChannelDTO> updateCourierChannel(@RequestBody CourierChannelDTO courierChannelDTO) throws URISyntaxException {
        log.debug("REST request to update CourierChannel : {}", courierChannelDTO);
        if (courierChannelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CourierChannelDTO result = courierChannelService.save(courierChannelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, courierChannelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /courier-channels : get all the courierChannels.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of courierChannels in body
     */
    @GetMapping("/courier-channels")
    @Timed
    public ResponseEntity<List<CourierChannelDTO>> getAllCourierChannels(CourierChannelCriteria criteria) {
        log.debug("REST request to get CourierChannels by criteria: {}", criteria);
        List<CourierChannelDTO> entityList = courierChannelQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /courier-channels/:id : get the "id" courierChannel.
     *
     * @param id the id of the courierChannelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the courierChannelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/courier-channels/{id}")
    @Timed
    public ResponseEntity<CourierChannelDTO> getCourierChannel(@PathVariable Long id) {
        log.debug("REST request to get CourierChannel : {}", id);
        Optional<CourierChannelDTO> courierChannelDTO = courierChannelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courierChannelDTO);
    }

    /**
     * DELETE  /courier-channels/:id : delete the "id" courierChannel.
     *
     * @param id the id of the courierChannelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/courier-channels/{id}")
    @Timed
    public ResponseEntity<Void> deleteCourierChannel(@PathVariable Long id) {
        log.debug("REST request to delete CourierChannel : {}", id);
        courierChannelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/courier-channels?query=:query : search for the courierChannel corresponding
     * to the query.
     *
     * @param query the query of the courierChannel search
     * @return the result of the search
     */
    @GetMapping("/_search/courier-channels")
    @Timed
    public List<CourierChannelDTO> searchCourierChannels(@RequestParam String query) {
        log.debug("REST request to search CourierChannels for query {}", query);
        return courierChannelService.search(query);
    }

}
