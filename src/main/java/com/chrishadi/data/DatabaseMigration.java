package com.chrishadi.data;

import java.util.Collections;
import java.util.List;

public final class DatabaseMigration {

    private final List<TableMigration> tableMigrations;

    public DatabaseMigration(List<TableMigration> tableMigrations) {
        this.tableMigrations = Collections.unmodifiableList(tableMigrations);
    }

    public List<TableMigration> getTableMigrations() {
        return tableMigrations;
    }
}
