package com.hk.logistics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hk.logistics.service.CourierService;
import com.hk.logistics.web.rest.errors.BadRequestAlertException;
import com.hk.logistics.web.rest.util.HeaderUtil;
import com.hk.logistics.web.rest.util.PaginationUtil;
import com.hk.logistics.service.dto.CourierDTO;
import com.hk.logistics.service.dto.AwbCriteria;
import com.hk.logistics.service.dto.CourierCriteria;
import com.hk.logistics.service.CourierQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Courier.
 */
@RestController
@RequestMapping("/api")
public class CourierResource {

    private final Logger log = LoggerFactory.getLogger(CourierResource.class);

    private static final String ENTITY_NAME = "courier";

    private final CourierService courierService;

    private final CourierQueryService courierQueryService;

    public CourierResource(CourierService courierService, CourierQueryService courierQueryService) {
        this.courierService = courierService;
        this.courierQueryService = courierQueryService;
    }

    /**
     * POST  /couriers : Create a new courier.
     *
     * @param courierDTO the courierDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new courierDTO, or with status 400 (Bad Request) if the courier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/couriers")
    @Timed
    public ResponseEntity<CourierDTO> createCourier(@Valid @RequestBody CourierDTO courierDTO) throws URISyntaxException {
        log.debug("REST request to save Courier : {}", courierDTO);
        if (courierDTO.getId() != null) {
            throw new BadRequestAlertException("A new courier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourierDTO result = courierService.save(courierDTO);
        return ResponseEntity.created(new URI("/api/couriers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /couriers : Updates an existing courier.
     *
     * @param courierDTO the courierDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated courierDTO,
     * or with status 400 (Bad Request) if the courierDTO is not valid,
     * or with status 500 (Internal Server Error) if the courierDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/couriers")
    @Timed
    public ResponseEntity<CourierDTO> updateCourier(@Valid @RequestBody CourierDTO courierDTO) throws URISyntaxException {
        log.debug("REST request to update Courier : {}", courierDTO);
        if (courierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CourierDTO result = courierService.save(courierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, courierDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /couriers : get all the couriers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of couriers in body
     */
    @GetMapping("/couriers")
    @Timed
    public ResponseEntity<List<CourierDTO>> getAllCouriers(CourierCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Couriers by criteria: {}", criteria);
        Page<CourierDTO> page = courierQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/couriers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /couriers/:id : get the "id" courier.
     *
     * @param id the id of the courierDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the courierDTO, or with status 404 (Not Found)
     */
    @GetMapping("/couriers/{id}")
    @Timed
    public ResponseEntity<CourierDTO> getCourier(@PathVariable Long id) {
        log.debug("REST request to get Courier : {}", id);
        Optional<CourierDTO> courierDTO = courierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courierDTO);
    }

    /**
     * DELETE  /couriers/:id : delete the "id" courier.
     *
     * @param id the id of the courierDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/couriers/{id}")
    @Timed
    public ResponseEntity<Void> deleteCourier(@PathVariable Long id) {
        log.debug("REST request to delete Courier : {}", id);
        courierService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * SEARCH  /_search/couriers?query=:query : search for the courier corresponding
     * to the query.
     *
     * @param query the query of the courier search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/couriers")
    @Timed
    public ResponseEntity<List<CourierDTO>> search(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Couriers for query {}", query);
        Page<CourierDTO> page = courierService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/couriers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * SEARCH  /_search/couriers?query=:query : search for the courier corresponding
     * to the query.
     *
     * @param query the query of the courier search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/couriers/name")
    @Timed
    public ResponseEntity<List<CourierDTO>> searchCouriersByName(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Couriers for query {}", query);
        Page<CourierDTO> page = courierService.searchByName(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/couriers/name");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @GetMapping("/couriers/filter")
    @Timed
    public ResponseEntity<List<CourierDTO>> filter(HttpServletResponse response, CourierCriteria criteria, Pageable pageable) {
        log.debug("REST request to filter Couriers for criteria {}", criteria);
        Page<CourierDTO> page = courierQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders("", page, "/api/_search/couriers/filter");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
