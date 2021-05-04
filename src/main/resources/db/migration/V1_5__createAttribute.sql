DROP TABLE IF EXISTS `attribute`;

CREATE TABLE `attribute`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_by` varchar(255) DEFAULT NULL,
    `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted` bit DEFAULT 0,
    `date_deleted` datetime DEFAULT NULL,
    `last_modified_by` varchar(255) DEFAULT NULL,
    `date_updated` datetime DEFAULT NULL,
    `participates_in_variation` boolean DEFAULT FALSE,
    `entered_manually` boolean DEFAULT FALSE,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY(`id`)
)DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;