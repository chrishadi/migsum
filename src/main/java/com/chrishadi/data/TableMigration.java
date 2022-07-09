package com.chrishadi.data;

import java.util.Objects;

public final class TableMigration {

    private final String sql;

    private final String title; 
    
    private final String tableName;

    public TableMigration(String sql, String tableName, String title) {
        this.sql = sql;
        this.tableName = tableName;
        this.title = title;
    }

    public String getSql() {
        return sql;
    }

    public String getTableName() {
        return tableName;
    }
    
    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof TableMigration)) {
            return false;
        }

        TableMigration tm = (TableMigration) o;
        return Objects.equals(tm.getSql(), sql) &&
            Objects.equals(tm.getTableName(), tableName) &&
            Objects.equals(tm.getTitle(), title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sql, tableName, title);
    }
}
