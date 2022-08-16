package com.chrishadi.parser;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.chrishadi.data.TableMigration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlterTableParserTest {

    private static Stream<Arguments> provideParseScenarios() {
        return Stream.of(
                Arguments.of(
                        "ALTER TABLE `my_table` MODIFY COLUMN address VARCHAR(255) NOT NULL DEFAULT ''",
                        "my_table",
                        "Modify address column on my_table"
                ),
                Arguments.of(
                        "ALTER TABLE `my_table` ADD COLUMN `created_at` DATETIME, ADD COLUMN `updated_at` DATETIME",
                        "my_table",
                        "Add created_at column and updated_at column to my_table"
                ),
                Arguments.of(
                        "ALTER TABLE `my_table` ADD COLUMN `created_at` DATETIME, ADD INDEX `index_created_at` (created_at)",
                        "my_table",
                        "Add created_at column and index_created_at index to my_table"
                ),
                Arguments.of(
                        "ALTER TABLE `my_table` DROP COLUMN `created_at`, DROP INDEX `index_created_at`",
                        "my_table",
                        "Drop created_at column and index_created_at index from my_table"
                ),
                Arguments.of(
                        "ALTER TABLE `my_table` ADD COLUMN `created_at`, DROP INDEX `index_created_at`",
                        "my_table",
                        "Add created_at column and drop index_created_at index from my_table"
                )
        );
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("provideParseScenarios")
    public void testParse(String sql, String expectedTableName, String expectedTitle) {
        TableMigrationParser parser = new AlterTableParser(sql);
        TableMigration migration = parser.parse();

        assertEquals(sql, migration.getSql());
        assertEquals(expectedTableName, migration.getTableName());
        assertEquals(expectedTitle, migration.getTitle());
    }
}
