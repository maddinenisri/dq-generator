package com.mdstech.dqgenerator.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the ColumnDefinition entity. This class is used in ColumnDefinitionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /column-definitions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ColumnDefinitionCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter columnName;

    private StringFilter columnType;

    private IntegerFilter pkColumnOrder;

    private LongFilter tableDefinitionId;

    public ColumnDefinitionCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getColumnName() {
        return columnName;
    }

    public void setColumnName(StringFilter columnName) {
        this.columnName = columnName;
    }

    public StringFilter getColumnType() {
        return columnType;
    }

    public void setColumnType(StringFilter columnType) {
        this.columnType = columnType;
    }

    public IntegerFilter getPkColumnOrder() {
        return pkColumnOrder;
    }

    public void setPkColumnOrder(IntegerFilter pkColumnOrder) {
        this.pkColumnOrder = pkColumnOrder;
    }

    public LongFilter getTableDefinitionId() {
        return tableDefinitionId;
    }

    public void setTableDefinitionId(LongFilter tableDefinitionId) {
        this.tableDefinitionId = tableDefinitionId;
    }

    @Override
    public String toString() {
        return "ColumnDefinitionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (columnName != null ? "columnName=" + columnName + ", " : "") +
                (columnType != null ? "columnType=" + columnType + ", " : "") +
                (pkColumnOrder != null ? "pkColumnOrder=" + pkColumnOrder + ", " : "") +
                (tableDefinitionId != null ? "tableDefinitionId=" + tableDefinitionId + ", " : "") +
            "}";
    }

}
