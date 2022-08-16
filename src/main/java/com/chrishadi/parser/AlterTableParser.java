package com.chrishadi.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.chrishadi.data.TableMigration;

public class AlterTableParser implements TableMigrationParser {

    private final String sql;

    private TableMigration tableMigration;

    /**
     * Create a new AlterTableParser instance
     *
     * @param sql String
     */
    public AlterTableParser(String sql) {
        Objects.requireNonNull(sql);
        this.sql = sql;
    }

    /**
     * Summarize the sql
     *
     * @return TableMigration object
     */
    public TableMigration parse() {
        String[] splits = sql.split("\\s", 4);
        String tableName = splits[2].replaceAll("^`|`$", "");
        String actions = splits[3];
        String title = makeUpTitle(actions, tableName);
        return new TableMigration(sql, tableName, title);
    }

    /**
     * Make the title based on the sql actions and the table name
     *
     * @param actions   String
     * @param tableName String
     * @return String
     */
    private String makeUpTitle(String actions, String tableName) {
        List<String> actionSummaries = new ArrayList<>();
        String lastCmd = null;

        for (var action : actions.split(",")) {
            String[] splits = action.trim().split("\\s", 4);
            if (splits.length >= 3) {
                String cmd = splits[0].toLowerCase();
                String typeArg = splits[1].toLowerCase();
                String nameArg = splits[2].toLowerCase().replaceAll("^[`'\"]|[`'\"]$", "");
                StringBuilder builder = new StringBuilder();
                if (!cmd.equals(lastCmd)) {
                    builder.append(cmd).append(" ");
                    lastCmd = cmd;
                }
                builder.append(nameArg).append(" ").append(typeArg);
                actionSummaries.add(builder.toString());
            }
        }

        if (actionSummaries.isEmpty()) {
            return "Alter table " + tableName;
        }
        String preposition = choosePrepositionByCmd(lastCmd);
        return capitalize(String.join(" and ", actionSummaries)) + " " + preposition + " " + tableName;
    }

    /**
     * Choose the preposition based on command
     *
     * @param cmd String
     * @return String
     */
    private String choosePrepositionByCmd(String cmd) {
        if (cmd.equals("add")) {
            return "to";
        }
        if (cmd.equals("drop")) {
            return "from";
        }
        return "on";
    }

    /**
     * Capitalize a string (should be in different package or just use Apache string library).
     *
     * @param str String
     * @return String
     */
    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
