MigSum
======

Build DDL migration summaries.

For example:

Given this DDL:
```mysql
ALTER TABLE `notifications` ADD INDEX `idx_created_at` (`created_at`);
-- or with columns as well
ALTER TABLE `inventories`
    ADD COLUMN `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ADD INDEX `idx_created_at` (`created_at`),
    ADD INDEX `idx_updated_at` (`updated_at`);
```
Produce:
```text
Add idx_created_at index to notifications
Add created_at column and updated_at column and idx_created_at index and idx_updated_at index to inventories 
```

Requirements
------------

- JDK 11
- Maven 3.8.0