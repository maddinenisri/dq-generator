package com.mdstech.dqgenerator.service.impl;

import com.mdstech.dqgenerator.service.TableDefinitionService;
import com.mdstech.dqgenerator.domain.TableDefinition;
import com.mdstech.dqgenerator.repository.TableDefinitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing TableDefinition.
 */
@Service
@Transactional
public class TableDefinitionServiceImpl implements TableDefinitionService{

    private final Logger log = LoggerFactory.getLogger(TableDefinitionServiceImpl.class);

    private final TableDefinitionRepository tableDefinitionRepository;

    public TableDefinitionServiceImpl(TableDefinitionRepository tableDefinitionRepository) {
        this.tableDefinitionRepository = tableDefinitionRepository;
    }

    /**
     * Save a tableDefinition.
     *
     * @param tableDefinition the entity to save
     * @return the persisted entity
     */
    @Override
    public TableDefinition save(TableDefinition tableDefinition) {
        log.debug("Request to save TableDefinition : {}", tableDefinition);
        return tableDefinitionRepository.save(tableDefinition);
    }

    /**
     *  Get all the tableDefinitions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TableDefinition> findAll() {
        log.debug("Request to get all TableDefinitions");
        return tableDefinitionRepository.findAll();
    }

    /**
     *  Get one tableDefinition by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TableDefinition findOne(Long id) {
        log.debug("Request to get TableDefinition : {}", id);
        return tableDefinitionRepository.findOne(id);
    }

    /**
     *  Delete the  tableDefinition by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TableDefinition : {}", id);
        tableDefinitionRepository.delete(id);
    }
}
