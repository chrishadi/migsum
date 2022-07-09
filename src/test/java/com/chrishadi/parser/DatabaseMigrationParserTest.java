package com.chrishadi.parser;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import com.chrishadi.data.TableMigration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseMigrationParserTest {

    @Test
    public void testParse() throws URISyntaxException, IOException {
        var testFileUrl = Thread.currentThread().getContextClassLoader()
                .getResource("sample-ddl.sql");
        assertNotNull(testFileUrl);

        File testFile = new File(testFileUrl.toURI());
        DatabaseMigrationParser parser = new DatabaseMigrationParser(testFile);

        parser.parse();

        var migration = parser.getDatabaseMigration();
        assertNotNull(migration);

        var tableMigrations = migration.getTableMigrations();
        assertEquals(2, tableMigrations.size());

        var firstTableMigration = tableMigrations.get(0);
        assertEquals("ALTER TABLE `notifications` ADD INDEX `idx_created_at` (`created_at`)",
                firstTableMigration.getSql());
        assertEquals("notifications", firstTableMigration.getTableName());
        assertEquals("Add idx_created_at index to notifications",
                firstTableMigration.getTitle());

        var secondTableMigration = tableMigrations.get(1);
        assertEquals("ALTER TABLE `inventories`\n"+
                "    ADD COLUMN `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"+
                "    ADD COLUMN `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n"+
                "    ADD INDEX `idx_created_at` (`created_at`),\n"+
                "    ADD INDEX `idx_updated_at` (`updated_at`)",
                secondTableMigration.getSql().replaceAll("\\r", ""));
        assertEquals("inventories", secondTableMigration.getTableName());
        assertEquals("Add created_at column and updated_at column and idx_created_at index and idx_updated_at index to inventories",
                secondTableMigration.getTitle());
    }

}
