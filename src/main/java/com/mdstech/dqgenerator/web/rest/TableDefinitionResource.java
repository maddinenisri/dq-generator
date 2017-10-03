package com.mdstech.dqgenerator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mdstech.dqgenerator.domain.TableDefinition;
import com.mdstech.dqgenerator.service.TableDefinitionService;
import com.mdstech.dqgenerator.web.rest.util.HeaderUtil;
import com.mdstech.dqgenerator.service.dto.TableDefinitionCriteria;
import com.mdstech.dqgenerator.service.TableDefinitionQueryService;
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
 * REST controller for managing TableDefinition.
 */
@RestController
@RequestMapping("/api")
public class TableDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(TableDefinitionResource.class);

    private static final String ENTITY_NAME = "tableDefinition";

    private final TableDefinitionService tableDefinitionService;

    private final TableDefinitionQueryService tableDefinitionQueryService;

    public TableDefinitionResource(TableDefinitionService tableDefinitionService, TableDefinitionQueryService tableDefinitionQueryService) {
        this.tableDefinitionService = tableDefinitionService;
        this.tableDefinitionQueryService = tableDefinitionQueryService;
    }

    /**
     * POST  /table-definitions : Create a new tableDefinition.
     *
     * @param tableDefinition the tableDefinition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tableDefinition, or with status 400 (Bad Request) if the tableDefinition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/table-definitions")
    @Timed
    public ResponseEntity<TableDefinition> createTableDefinition(@Valid @RequestBody TableDefinition tableDefinition) throws URISyntaxException {
        log.debug("REST request to save TableDefinition : {}", tableDefinition);
        if (tableDefinition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tableDefinition cannot already have an ID")).body(null);
        }
        TableDefinition result = tableDefinitionService.save(tableDefinition);
        return ResponseEntity.created(new URI("/api/table-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /table-definitions : Updates an existing tableDefinition.
     *
     * @param tableDefinition the tableDefinition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tableDefinition,
     * or with status 400 (Bad Request) if the tableDefinition is not valid,
     * or with status 500 (Internal Server Error) if the tableDefinition couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/table-definitions")
    @Timed
    public ResponseEntity<TableDefinition> updateTableDefinition(@Valid @RequestBody TableDefinition tableDefinition) throws URISyntaxException {
        log.debug("REST request to update TableDefinition : {}", tableDefinition);
        if (tableDefinition.getId() == null) {
            return createTableDefinition(tableDefinition);
        }
        TableDefinition result = tableDefinitionService.save(tableDefinition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tableDefinition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /table-definitions : get all the tableDefinitions.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of tableDefinitions in body
     */
    @GetMapping("/table-definitions")
    @Timed
    public ResponseEntity<List<TableDefinition>> getAllTableDefinitions(TableDefinitionCriteria criteria) {
        log.debug("REST request to get TableDefinitions by criteria: {}", criteria);
        List<TableDefinition> entityList = tableDefinitionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /table-definitions/:id : get the "id" tableDefinition.
     *
     * @param id the id of the tableDefinition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tableDefinition, or with status 404 (Not Found)
     */
    @GetMapping("/table-definitions/{id}")
    @Timed
    public ResponseEntity<TableDefinition> getTableDefinition(@PathVariable Long id) {
        log.debug("REST request to get TableDefinition : {}", id);
        TableDefinition tableDefinition = tableDefinitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tableDefinition));
    }

    /**
     * DELETE  /table-definitions/:id : delete the "id" tableDefinition.
     *
     * @param id the id of the tableDefinition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/table-definitions/{id}")
    @Timed
    public ResponseEntity<Void> deleteTableDefinition(@PathVariable Long id) {
        log.debug("REST request to delete TableDefinition : {}", id);
        tableDefinitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
