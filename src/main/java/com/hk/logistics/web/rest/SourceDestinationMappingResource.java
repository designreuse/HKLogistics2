package com.hk.logistics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hk.logistics.service.SourceDestinationMappingService;
import com.hk.logistics.web.rest.errors.BadRequestAlertException;
import com.hk.logistics.web.rest.util.HeaderUtil;
import com.hk.logistics.service.dto.SourceDestinationMappingDTO;
import com.hk.logistics.service.dto.SourceDestinationMappingCriteria;
import com.hk.logistics.service.SourceDestinationMappingQueryService;
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
 * REST controller for managing SourceDestinationMapping.
 */
@RestController
@RequestMapping("/api")
public class SourceDestinationMappingResource {

    private final Logger log = LoggerFactory.getLogger(SourceDestinationMappingResource.class);

    private static final String ENTITY_NAME = "sourceDestinationMapping";

    private final SourceDestinationMappingService sourceDestinationMappingService;

    private final SourceDestinationMappingQueryService sourceDestinationMappingQueryService;

    public SourceDestinationMappingResource(SourceDestinationMappingService sourceDestinationMappingService, SourceDestinationMappingQueryService sourceDestinationMappingQueryService) {
        this.sourceDestinationMappingService = sourceDestinationMappingService;
        this.sourceDestinationMappingQueryService = sourceDestinationMappingQueryService;
    }

    /**
     * POST  /source-destination-mappings : Create a new sourceDestinationMapping.
     *
     * @param sourceDestinationMappingDTO the sourceDestinationMappingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sourceDestinationMappingDTO, or with status 400 (Bad Request) if the sourceDestinationMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/source-destination-mappings")
    @Timed
    public ResponseEntity<SourceDestinationMappingDTO> createSourceDestinationMapping(@Valid @RequestBody SourceDestinationMappingDTO sourceDestinationMappingDTO) throws URISyntaxException {
        log.debug("REST request to save SourceDestinationMapping : {}", sourceDestinationMappingDTO);
        if (sourceDestinationMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new sourceDestinationMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SourceDestinationMappingDTO result = sourceDestinationMappingService.save(sourceDestinationMappingDTO);
        return ResponseEntity.created(new URI("/api/source-destination-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /source-destination-mappings : Updates an existing sourceDestinationMapping.
     *
     * @param sourceDestinationMappingDTO the sourceDestinationMappingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sourceDestinationMappingDTO,
     * or with status 400 (Bad Request) if the sourceDestinationMappingDTO is not valid,
     * or with status 500 (Internal Server Error) if the sourceDestinationMappingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/source-destination-mappings")
    @Timed
    public ResponseEntity<SourceDestinationMappingDTO> updateSourceDestinationMapping(@Valid @RequestBody SourceDestinationMappingDTO sourceDestinationMappingDTO) throws URISyntaxException {
        log.debug("REST request to update SourceDestinationMapping : {}", sourceDestinationMappingDTO);
        if (sourceDestinationMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SourceDestinationMappingDTO result = sourceDestinationMappingService.save(sourceDestinationMappingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sourceDestinationMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /source-destination-mappings : get all the sourceDestinationMappings.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of sourceDestinationMappings in body
     */
    @GetMapping("/source-destination-mappings")
    @Timed
    public ResponseEntity<List<SourceDestinationMappingDTO>> getAllSourceDestinationMappings(SourceDestinationMappingCriteria criteria) {
        log.debug("REST request to get SourceDestinationMappings by criteria: {}", criteria);
        List<SourceDestinationMappingDTO> entityList = sourceDestinationMappingQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /source-destination-mappings/:id : get the "id" sourceDestinationMapping.
     *
     * @param id the id of the sourceDestinationMappingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sourceDestinationMappingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/source-destination-mappings/{id}")
    @Timed
    public ResponseEntity<SourceDestinationMappingDTO> getSourceDestinationMapping(@PathVariable Long id) {
        log.debug("REST request to get SourceDestinationMapping : {}", id);
        Optional<SourceDestinationMappingDTO> sourceDestinationMappingDTO = sourceDestinationMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sourceDestinationMappingDTO);
    }

    /**
     * DELETE  /source-destination-mappings/:id : delete the "id" sourceDestinationMapping.
     *
     * @param id the id of the sourceDestinationMappingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/source-destination-mappings/{id}")
    @Timed
    public ResponseEntity<Void> deleteSourceDestinationMapping(@PathVariable Long id) {
        log.debug("REST request to delete SourceDestinationMapping : {}", id);
        sourceDestinationMappingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/source-destination-mappings?query=:query : search for the sourceDestinationMapping corresponding
     * to the query.
     *
     * @param query the query of the sourceDestinationMapping search
     * @return the result of the search
     */
    @GetMapping("/_search/source-destination-mappings")
    @Timed
    public List<SourceDestinationMappingDTO> searchSourceDestinationMappings(@RequestParam String query) {
        log.debug("REST request to search SourceDestinationMappings for query {}", query);
        return sourceDestinationMappingService.search(query);
    }

}
