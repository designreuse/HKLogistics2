package com.hk.logistics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hk.logistics.service.ShipmentServiceTypeService;
import com.hk.logistics.web.rest.errors.BadRequestAlertException;
import com.hk.logistics.web.rest.util.HeaderUtil;
import com.hk.logistics.service.dto.ShipmentServiceTypeDTO;
import com.hk.logistics.service.dto.ShipmentServiceTypeCriteria;
import com.hk.logistics.service.ShipmentServiceTypeQueryService;
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
 * REST controller for managing ShipmentServiceType.
 */
@RestController
@RequestMapping("/api")
public class ShipmentServiceTypeResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentServiceTypeResource.class);

    private static final String ENTITY_NAME = "shipmentServiceType";

    private final ShipmentServiceTypeService shipmentServiceTypeService;

    private final ShipmentServiceTypeQueryService shipmentServiceTypeQueryService;

    public ShipmentServiceTypeResource(ShipmentServiceTypeService shipmentServiceTypeService, ShipmentServiceTypeQueryService shipmentServiceTypeQueryService) {
        this.shipmentServiceTypeService = shipmentServiceTypeService;
        this.shipmentServiceTypeQueryService = shipmentServiceTypeQueryService;
    }

    /**
     * POST  /shipment-service-types : Create a new shipmentServiceType.
     *
     * @param shipmentServiceTypeDTO the shipmentServiceTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentServiceTypeDTO, or with status 400 (Bad Request) if the shipmentServiceType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipment-service-types")
    @Timed
    public ResponseEntity<ShipmentServiceTypeDTO> createShipmentServiceType(@Valid @RequestBody ShipmentServiceTypeDTO shipmentServiceTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ShipmentServiceType : {}", shipmentServiceTypeDTO);
        if (shipmentServiceTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipmentServiceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipmentServiceTypeDTO result = shipmentServiceTypeService.save(shipmentServiceTypeDTO);
        return ResponseEntity.created(new URI("/api/shipment-service-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipment-service-types : Updates an existing shipmentServiceType.
     *
     * @param shipmentServiceTypeDTO the shipmentServiceTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentServiceTypeDTO,
     * or with status 400 (Bad Request) if the shipmentServiceTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the shipmentServiceTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipment-service-types")
    @Timed
    public ResponseEntity<ShipmentServiceTypeDTO> updateShipmentServiceType(@Valid @RequestBody ShipmentServiceTypeDTO shipmentServiceTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ShipmentServiceType : {}", shipmentServiceTypeDTO);
        if (shipmentServiceTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShipmentServiceTypeDTO result = shipmentServiceTypeService.save(shipmentServiceTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentServiceTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipment-service-types : get all the shipmentServiceTypes.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of shipmentServiceTypes in body
     */
    @GetMapping("/shipment-service-types")
    @Timed
    public ResponseEntity<List<ShipmentServiceTypeDTO>> getAllShipmentServiceTypes(ShipmentServiceTypeCriteria criteria) {
        log.debug("REST request to get ShipmentServiceTypes by criteria: {}", criteria);
        List<ShipmentServiceTypeDTO> entityList = shipmentServiceTypeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /shipment-service-types/:id : get the "id" shipmentServiceType.
     *
     * @param id the id of the shipmentServiceTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentServiceTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shipment-service-types/{id}")
    @Timed
    public ResponseEntity<ShipmentServiceTypeDTO> getShipmentServiceType(@PathVariable Long id) {
        log.debug("REST request to get ShipmentServiceType : {}", id);
        Optional<ShipmentServiceTypeDTO> shipmentServiceTypeDTO = shipmentServiceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipmentServiceTypeDTO);
    }

    /**
     * DELETE  /shipment-service-types/:id : delete the "id" shipmentServiceType.
     *
     * @param id the id of the shipmentServiceTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipment-service-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteShipmentServiceType(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentServiceType : {}", id);
        shipmentServiceTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/shipment-service-types?query=:query : search for the shipmentServiceType corresponding
     * to the query.
     *
     * @param query the query of the shipmentServiceType search
     * @return the result of the search
     */
    @GetMapping("/_search/shipment-service-types")
    @Timed
    public List<ShipmentServiceTypeDTO> searchShipmentServiceTypes(@RequestParam String query) {
        log.debug("REST request to search ShipmentServiceTypes for query {}", query);
        return shipmentServiceTypeService.search(query);
    }

}
