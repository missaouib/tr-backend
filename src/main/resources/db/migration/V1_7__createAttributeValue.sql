DROP TABLE IF EXISTS `attribute_value`;

CREATE TABLE `attribute_value`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_by` varchar(255) DEFAULT NULL,
    `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted` bit DEFAULT 0,
    `date_deleted` datetime DEFAULT NULL,
    `last_modified_by` varchar(255) DEFAULT NULL,
    `date_updated` datetime DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `attribute_id` bigint(20) NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`attribute_id`) REFERENCES `attribute`(id)
)DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;