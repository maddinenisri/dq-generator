package com.mdstech.dqgenerator.service;

import com.mdstech.dqgenerator.domain.TableDefinition;
import java.util.List;

/**
 * Service Interface for managing TableDefinition.
 */
public interface TableDefinitionService {

    /**
     * Save a tableDefinition.
     *
     * @param tableDefinition the entity to save
     * @return the persisted entity
     */
    TableDefinition save(TableDefinition tableDefinition);

    /**
     *  Get all the tableDefinitions.
     *
     *  @return the list of entities
     */
    List<TableDefinition> findAll();

    /**
     *  Get the "id" tableDefinition.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TableDefinition findOne(Long id);

    /**
     *  Delete the "id" tableDefinition.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
