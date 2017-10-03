package com.mdstech.dqgenerator.repository;

import com.mdstech.dqgenerator.domain.ColumnDefinition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ColumnDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColumnDefinitionRepository extends JpaRepository<ColumnDefinition, Long>, JpaSpecificationExecutor<ColumnDefinition> {

}
