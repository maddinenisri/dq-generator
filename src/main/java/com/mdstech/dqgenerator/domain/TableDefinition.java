package com.mdstech.dqgenerator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TableDefinition.
 */
@Entity
@Table(name = "table_definition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TableDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "schema_name")
    private String schemaName;

    @OneToMany(mappedBy = "tableDefinition")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ColumnDefinition> columnDefinitions = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TableDefinition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public TableDefinition schemaName(String schemaName) {
        this.schemaName = schemaName;
        return this;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public Set<ColumnDefinition> getColumnDefinitions() {
        return columnDefinitions;
    }

    public TableDefinition columnDefinitions(Set<ColumnDefinition> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
        return this;
    }

    public TableDefinition addColumnDefinitions(ColumnDefinition columnDefinition) {
        this.columnDefinitions.add(columnDefinition);
        columnDefinition.setTableDefinition(this);
        return this;
    }

    public TableDefinition removeColumnDefinitions(ColumnDefinition columnDefinition) {
        this.columnDefinitions.remove(columnDefinition);
        columnDefinition.setTableDefinition(null);
        return this;
    }

    public TableDefinition addColumnDefinition(ColumnDefinition columnDefinition) {
        this.columnDefinitions.add(columnDefinition);
        columnDefinition.setTableDefinition(this);
        return this;
    }

    public TableDefinition removeColumnDefinition(ColumnDefinition columnDefinition) {
        this.columnDefinitions.remove(columnDefinition);
        columnDefinition.setTableDefinition(null);
        return this;
    }

    public void setColumnDefinitions(Set<ColumnDefinition> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TableDefinition tableDefinition = (TableDefinition) o;
        if (tableDefinition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tableDefinition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TableDefinition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", schemaName='" + getSchemaName() + "'" +
            "}";
    }
}
