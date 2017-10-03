package com.mdstech.dqgenerator.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ColumnDefinition.
 */
@Entity
@Table(name = "column_definition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ColumnDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "column_name", nullable = false)
    private String columnName;

    @NotNull
    @Column(name = "column_type", nullable = false)
    private String columnType;

    @Column(name = "pk_column_order")
    private Integer pkColumnOrder;

    @ManyToOne
    private TableDefinition tableDefinition;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public ColumnDefinition columnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public ColumnDefinition columnType(String columnType) {
        this.columnType = columnType;
        return this;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public Integer getPkColumnOrder() {
        return pkColumnOrder;
    }

    public ColumnDefinition pkColumnOrder(Integer pkColumnOrder) {
        this.pkColumnOrder = pkColumnOrder;
        return this;
    }

    public void setPkColumnOrder(Integer pkColumnOrder) {
        this.pkColumnOrder = pkColumnOrder;
    }

    public TableDefinition getTableDefinition() {
        return tableDefinition;
    }

    public ColumnDefinition tableDefinition(TableDefinition tableDefinition) {
        this.tableDefinition = tableDefinition;
        return this;
    }

    public void setTableDefinition(TableDefinition tableDefinition) {
        this.tableDefinition = tableDefinition;
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
        ColumnDefinition columnDefinition = (ColumnDefinition) o;
        if (columnDefinition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), columnDefinition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ColumnDefinition{" +
            "id=" + getId() +
            ", columnName='" + getColumnName() + "'" +
            ", columnType='" + getColumnType() + "'" +
            ", pkColumnOrder='" + getPkColumnOrder() + "'" +
            "}";
    }
}
