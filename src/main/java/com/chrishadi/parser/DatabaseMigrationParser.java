package com.chrishadi.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.chrishadi.data.DatabaseMigration;
import com.chrishadi.data.TableMigration;

public class DatabaseMigrationParser {

    /**
     * Parse a migration file
     *
     * @param file java.io.File object
     * @return DatabaseMigration object
     * @throws IOException when reading file content
     */
    public DatabaseMigration parse(File file) throws IOException {
        Objects.requireNonNull(file);
        byte[] bytes = Files.readAllBytes(file.toPath());
        String sql = new String(bytes);
        List<TableMigration> tableMigrations = parseMigration(removeComment(sql));
        return new DatabaseMigration(tableMigrations);
    }

    /**
     * Remove sql comments
     *
     * @param sql String
     * @return String
     */
    private String removeComment(String sql) {
        return sql.replaceAll("--.*\n", "");
    }

    /**
     * Parse the sql
     *
     * @param sql String
     * @return List of TableMigration object
     */
    private List<TableMigration> parseMigration(String sql) {
        List<TableMigration> migrations = new ArrayList<>();
        for (String tableSql : sql.split(";")) {
            if (tableSql == null || tableSql.isBlank()) {
                continue;
            }

            TableMigrationParser parser = chooseTableMigrationParserBySql(tableSql.trim());
            TableMigration tableMigration = parser.parse();
            if (tableMigration != null) {
                migrations.add(tableMigration);
            }
        }
        return migrations;
    }

    /**
     * Choose statement parser based on a few first keywords
     *
     * @param sql String
     * @return TableMigrationParser object
     */
    private TableMigrationParser chooseTableMigrationParserBySql(String sql) {
        if (sql.toUpperCase().startsWith("ALTER TABLE")) {
            return new AlterTableParser(sql);
        }
        return new NullTableMigrationParser();
    }
}
