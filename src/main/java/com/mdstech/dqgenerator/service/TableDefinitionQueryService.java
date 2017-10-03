package com.mdstech.dqgenerator.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mdstech.dqgenerator.domain.TableDefinition;
import com.mdstech.dqgenerator.domain.*; // for static metamodels
import com.mdstech.dqgenerator.repository.TableDefinitionRepository;
import com.mdstech.dqgenerator.service.dto.TableDefinitionCriteria;


/**
 * Service for executing complex queries for TableDefinition entities in the database.
 * The main input is a {@link TableDefinitionCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link TableDefinition} or a {@link Page} of {%link TableDefinition} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class TableDefinitionQueryService extends QueryService<TableDefinition> {

    private final Logger log = LoggerFactory.getLogger(TableDefinitionQueryService.class);


    private final TableDefinitionRepository tableDefinitionRepository;

    public TableDefinitionQueryService(TableDefinitionRepository tableDefinitionRepository) {
        this.tableDefinitionRepository = tableDefinitionRepository;
    }

    /**
     * Return a {@link List} of {%link TableDefinition} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TableDefinition> findByCriteria(TableDefinitionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<TableDefinition> specification = createSpecification(criteria);
        return tableDefinitionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link TableDefinition} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TableDefinition> findByCriteria(TableDefinitionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<TableDefinition> specification = createSpecification(criteria);
        return tableDefinitionRepository.findAll(specification, page);
    }

    /**
     * Function to convert TableDefinitionCriteria to a {@link Specifications}
     */
    private Specifications<TableDefinition> createSpecification(TableDefinitionCriteria criteria) {
        Specifications<TableDefinition> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TableDefinition_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TableDefinition_.name));
            }
            if (criteria.getSchemaName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSchemaName(), TableDefinition_.schemaName));
            }
        }
        return specification;
    }

}
