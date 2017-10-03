package com.mdstech.dqgenerator.service.impl;

import com.mdstech.dqgenerator.service.ColumnDefinitionService;
import com.mdstech.dqgenerator.domain.ColumnDefinition;
import com.mdstech.dqgenerator.repository.ColumnDefinitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ColumnDefinition.
 */
@Service
@Transactional
public class ColumnDefinitionServiceImpl implements ColumnDefinitionService{

    private final Logger log = LoggerFactory.getLogger(ColumnDefinitionServiceImpl.class);

    private final ColumnDefinitionRepository columnDefinitionRepository;

    public ColumnDefinitionServiceImpl(ColumnDefinitionRepository columnDefinitionRepository) {
        this.columnDefinitionRepository = columnDefinitionRepository;
    }

    /**
     * Save a columnDefinition.
     *
     * @param columnDefinition the entity to save
     * @return the persisted entity
     */
    @Override
    public ColumnDefinition save(ColumnDefinition columnDefinition) {
        log.debug("Request to save ColumnDefinition : {}", columnDefinition);
        return columnDefinitionRepository.save(columnDefinition);
    }

    /**
     *  Get all the columnDefinitions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ColumnDefinition> findAll() {
        log.debug("Request to get all ColumnDefinitions");
        return columnDefinitionRepository.findAll();
    }

    /**
     *  Get one columnDefinition by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ColumnDefinition findOne(Long id) {
        log.debug("Request to get ColumnDefinition : {}", id);
        return columnDefinitionRepository.findOne(id);
    }

    /**
     *  Delete the  columnDefinition by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ColumnDefinition : {}", id);
        columnDefinitionRepository.delete(id);
    }
}
