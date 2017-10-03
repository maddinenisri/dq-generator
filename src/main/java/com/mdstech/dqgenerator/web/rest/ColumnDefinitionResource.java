package com.mdstech.dqgenerator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mdstech.dqgenerator.domain.ColumnDefinition;
import com.mdstech.dqgenerator.service.ColumnDefinitionService;
import com.mdstech.dqgenerator.web.rest.util.HeaderUtil;
import com.mdstech.dqgenerator.service.dto.ColumnDefinitionCriteria;
import com.mdstech.dqgenerator.service.ColumnDefinitionQueryService;
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

/**
 * REST controller for managing ColumnDefinition.
 */
@RestController
@RequestMapping("/api")
public class ColumnDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(ColumnDefinitionResource.class);

    private static final String ENTITY_NAME = "columnDefinition";

    private final ColumnDefinitionService columnDefinitionService;

    private final ColumnDefinitionQueryService columnDefinitionQueryService;

    public ColumnDefinitionResource(ColumnDefinitionService columnDefinitionService, ColumnDefinitionQueryService columnDefinitionQueryService) {
        this.columnDefinitionService = columnDefinitionService;
        this.columnDefinitionQueryService = columnDefinitionQueryService;
    }

    /**
     * POST  /column-definitions : Create a new columnDefinition.
     *
     * @param columnDefinition the columnDefinition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new columnDefinition, or with status 400 (Bad Request) if the columnDefinition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/column-definitions")
    @Timed
    public ResponseEntity<ColumnDefinition> createColumnDefinition(@Valid @RequestBody ColumnDefinition columnDefinition) throws URISyntaxException {
        log.debug("REST request to save ColumnDefinition : {}", columnDefinition);
        if (columnDefinition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new columnDefinition cannot already have an ID")).body(null);
        }
        ColumnDefinition result = columnDefinitionService.save(columnDefinition);
        return ResponseEntity.created(new URI("/api/column-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /column-definitions : Updates an existing columnDefinition.
     *
     * @param columnDefinition the columnDefinition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated columnDefinition,
     * or with status 400 (Bad Request) if the columnDefinition is not valid,
     * or with status 500 (Internal Server Error) if the columnDefinition couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/column-definitions")
    @Timed
    public ResponseEntity<ColumnDefinition> updateColumnDefinition(@Valid @RequestBody ColumnDefinition columnDefinition) throws URISyntaxException {
        log.debug("REST request to update ColumnDefinition : {}", columnDefinition);
        if (columnDefinition.getId() == null) {
            return createColumnDefinition(columnDefinition);
        }
        ColumnDefinition result = columnDefinitionService.save(columnDefinition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, columnDefinition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /column-definitions : get all the columnDefinitions.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of columnDefinitions in body
     */
    @GetMapping("/column-definitions")
    @Timed
    public ResponseEntity<List<ColumnDefinition>> getAllColumnDefinitions(ColumnDefinitionCriteria criteria) {
        log.debug("REST request to get ColumnDefinitions by criteria: {}", criteria);
        List<ColumnDefinition> entityList = columnDefinitionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /column-definitions/:id : get the "id" columnDefinition.
     *
     * @param id the id of the columnDefinition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the columnDefinition, or with status 404 (Not Found)
     */
    @GetMapping("/column-definitions/{id}")
    @Timed
    public ResponseEntity<ColumnDefinition> getColumnDefinition(@PathVariable Long id) {
        log.debug("REST request to get ColumnDefinition : {}", id);
        ColumnDefinition columnDefinition = columnDefinitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(columnDefinition));
    }

    /**
     * DELETE  /column-definitions/:id : delete the "id" columnDefinition.
     *
     * @param id the id of the columnDefinition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/column-definitions/{id}")
    @Timed
    public ResponseEntity<Void> deleteColumnDefinition(@PathVariable Long id) {
        log.debug("REST request to delete ColumnDefinition : {}", id);
        columnDefinitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
