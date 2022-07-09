package com.chrishadi.parser;

import com.chrishadi.data.TableMigration;

public interface TableMigrationParser {

    public void parse();

    public TableMigration getTableMigration();
}
