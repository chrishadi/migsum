ALTER TABLE `notifications` ADD INDEX `idx_created_at` (`created_at`);
-- or with columns as well
ALTER TABLE `inventories`
    ADD COLUMN `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ADD INDEX `idx_created_at` (`created_at`),
    ADD INDEX `idx_updated_at` (`updated_at`);
