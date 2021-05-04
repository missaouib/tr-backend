DROP TABLE IF EXISTS `product_attributes`;

CREATE TABLE `product_attributes`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_by` varchar(255) DEFAULT NULL,
    `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted` bit DEFAULT 0,
    `date_deleted` datetime DEFAULT NULL,
    `last_modified_by` varchar(255) DEFAULT NULL,
    `date_updated` datetime DEFAULT NULL,
    `product_id` bigint(20) DEFAULT NULL,
    `attribute_id` bigint(20) DEFAULT NULL,
    `attribute_value_id` bigint(20) DEFAULT NULL,
    `prominent` boolean DEFAULT FALSE,
    PRIMARY KEY(`id`)
)DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;