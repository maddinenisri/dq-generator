package com.mdstech.dqgenerator.repository;

import com.mdstech.dqgenerator.domain.TableDefinition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TableDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TableDefinitionRepository extends JpaRepository<TableDefinition, Long>, JpaSpecificationExecutor<TableDefinition> {

}
