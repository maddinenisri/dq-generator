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

import com.mdstech.dqgenerator.domain.ColumnDefinition;
import com.mdstech.dqgenerator.domain.*; // for static metamodels
import com.mdstech.dqgenerator.repository.ColumnDefinitionRepository;
import com.mdstech.dqgenerator.service.dto.ColumnDefinitionCriteria;


/**
 * Service for executing complex queries for ColumnDefinition entities in the database.
 * The main input is a {@link ColumnDefinitionCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ColumnDefinition} or a {@link Page} of {%link ColumnDefinition} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ColumnDefinitionQueryService extends QueryService<ColumnDefinition> {

    private final Logger log = LoggerFactory.getLogger(ColumnDefinitionQueryService.class);


    private final ColumnDefinitionRepository columnDefinitionRepository;

    public ColumnDefinitionQueryService(ColumnDefinitionRepository columnDefinitionRepository) {
        this.columnDefinitionRepository = columnDefinitionRepository;
    }

    /**
     * Return a {@link List} of {%link ColumnDefinition} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ColumnDefinition> findByCriteria(ColumnDefinitionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ColumnDefinition> specification = createSpecification(criteria);
        return columnDefinitionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ColumnDefinition} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ColumnDefinition> findByCriteria(ColumnDefinitionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ColumnDefinition> specification = createSpecification(criteria);
        return columnDefinitionRepository.findAll(specification, page);
    }

    /**
     * Function to convert ColumnDefinitionCriteria to a {@link Specifications}
     */
    private Specifications<ColumnDefinition> createSpecification(ColumnDefinitionCriteria criteria) {
        Specifications<ColumnDefinition> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ColumnDefinition_.id));
            }
            if (criteria.getColumnName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColumnName(), ColumnDefinition_.columnName));
            }
            if (criteria.getColumnType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColumnType(), ColumnDefinition_.columnType));
            }
            if (criteria.getPkColumnOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPkColumnOrder(), ColumnDefinition_.pkColumnOrder));
            }
            if (criteria.getTableDefinitionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTableDefinitionId(), ColumnDefinition_.tableDefinition, TableDefinition_.id));
            }
        }
        return specification;
    }

}
