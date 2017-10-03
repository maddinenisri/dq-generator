package com.mdstech.dqgenerator.service;

import com.mdstech.dqgenerator.domain.ColumnDefinition;
import java.util.List;

/**
 * Service Interface for managing ColumnDefinition.
 */
public interface ColumnDefinitionService {

    /**
     * Save a columnDefinition.
     *
     * @param columnDefinition the entity to save
     * @return the persisted entity
     */
    ColumnDefinition save(ColumnDefinition columnDefinition);

    /**
     *  Get all the columnDefinitions.
     *
     *  @return the list of entities
     */
    List<ColumnDefinition> findAll();

    /**
     *  Get the "id" columnDefinition.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ColumnDefinition findOne(Long id);

    /**
     *  Delete the "id" columnDefinition.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
