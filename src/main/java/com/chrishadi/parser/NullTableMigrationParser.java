package com.chrishadi.parser;

import com.chrishadi.data.TableMigration;

public class NullTableMigrationParser implements TableMigrationParser {

    public NullTableMigrationParser() {}

    public void parse() {}

    public TableMigration getTableMigration() {
        return null;
    }
    
}
