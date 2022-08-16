package com.chrishadi.parser;

import org.junit.jupiter.api.Test;

import com.chrishadi.data.TableMigration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlterTableParserTest {

    @Test
    public void testParseTableMigration() {
        String sql = "ALTER TABLE `my_table` MODIFY COLUMN address VARCHAR(255) NOT NULL DEFAULT ''";

        TableMigrationParser parser = new AlterTableParser(sql);
        TableMigration migration = parser.parse();

        assertEquals(sql, migration.getSql());
        assertEquals("my_table", migration.getTableName());
        assertEquals("Modify address column on my_table", migration.getTitle());
    }

    @Test
    public void testParseTableMigrationWithMultipleAddColumns() {
        String sql = "ALTER TABLE `my_table` ADD COLUMN `created_at` DATETIME, ADD COLUMN `updated_at` DATETIME";

        TableMigrationParser parser = new AlterTableParser(sql);
        TableMigration migration = parser.parse();

        assertEquals(sql, migration.getSql());
        assertEquals("my_table", migration.getTableName());
        assertEquals("Add created_at column and updated_at column to my_table", migration.getTitle());
    }

    @Test
    public void testParseTableMigrationWithAddColumnAndIndex() {
        String sql = "ALTER TABLE `my_table` ADD COLUMN `created_at` DATETIME, ADD INDEX `index_created_at` (created_at)";

        TableMigrationParser parser = new AlterTableParser(sql);
        TableMigration migration = parser.parse();

        assertEquals(sql, migration.getSql());
        assertEquals("my_table", migration.getTableName());
        assertEquals("Add created_at column and index_created_at index to my_table", migration.getTitle());
    }

    @Test
    public void testParseTableMigrationWithMultiDropColumnAndIndex() {
        String sql = "ALTER TABLE `my_table` DROP COLUMN `created_at`, DROP INDEX `index_created_at`";

        TableMigrationParser parser = new AlterTableParser(sql);
        TableMigration migration = parser.parse();

        assertEquals(sql, migration.getSql());
        assertEquals("my_table", migration.getTableName());
        assertEquals("Drop created_at column and index_created_at index from my_table", migration.getTitle());
    }

    @Test
    public void testParseTableMigrationWithAddColumnAndDropIndex() {
        String sql = "ALTER TABLE `my_table` ADD COLUMN `created_at`, DROP INDEX `index_created_at`";

        TableMigrationParser parser = new AlterTableParser(sql);
        TableMigration migration = parser.parse();

        assertEquals(sql, migration.getSql());
        assertEquals("my_table", migration.getTableName());
        assertEquals("Add created_at column and drop index_created_at index from my_table", migration.getTitle());
    }
}
