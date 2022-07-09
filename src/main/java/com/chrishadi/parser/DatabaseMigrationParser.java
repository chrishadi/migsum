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

    private final File file;
    private DatabaseMigration databaseMigration;

    /**
     * Create a new DatabaseMigrationParser instance.
     * @param file java.io.File object
     */
    public DatabaseMigrationParser(File file) {
        Objects.requireNonNull(file);
        this.file = file;
    }

    /**
     * Parse the migration file
     * @throws IOException when reading file content
     */
    public void parse() throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        String sql = new String(bytes);
        List<TableMigration> tableMigrations = parseMigration(removeComment(sql));
        databaseMigration = new DatabaseMigration(tableMigrations);
    }

    /**
     * Retrieve the result of parse.
     * @return DatabaseMigration object.
     */
    public DatabaseMigration getDatabaseMigration() {
        return databaseMigration;
    }

    /**
     * Parse the sql
     * @param sql String
     * @return List of TableMigration objects
     */
    private List<TableMigration> parseMigration(String sql) {
        List<TableMigration> migrations = new ArrayList<>();
        for (String tableSql : sql.split(";")) {
            if (tableSql == null || tableSql.isBlank()) {
                continue;
            }

            TableMigrationParser parser = chooseTableMigrationParserBySql(tableSql.trim());
            parser.parse();
            TableMigration tableMigration = parser.getTableMigration();
            if (tableMigration != null) {
                migrations.add(tableMigration);
            }
        }
        return migrations;
    }

    /**
     * Choose statement parser based on a few first keywords
     * @param sql String
     * @return TableMigrationParser
     */
    private TableMigrationParser chooseTableMigrationParserBySql(String sql) {
        if (sql.toUpperCase().startsWith("ALTER TABLE")) {
            return new AlterTableParser(sql);
        }
        return new NullTableMigrationParser();
    }

    /**
     * Remove sql comments
     * @param sql String
     * @return String
     */
    private String removeComment(String sql) {
        return sql.replaceAll("--.*\r*\n", "");
    }
}
