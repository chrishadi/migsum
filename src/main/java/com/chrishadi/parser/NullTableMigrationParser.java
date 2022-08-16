package com.chrishadi.parser;

import com.chrishadi.data.TableMigration;

public class NullTableMigrationParser implements TableMigrationParser {

    public NullTableMigrationParser() {
    }

    public TableMigration parse() {
        return null;
    }
}
