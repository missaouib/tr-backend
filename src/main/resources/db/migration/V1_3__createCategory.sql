DROP TABLE IF EXISTS `category`;

CREATE TABLE `category`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_by` varchar(255) DEFAULT NULL,
    `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted` bit DEFAULT 0,
    `date_deleted` datetime DEFAULT NULL,
    `last_modified_by` varchar(255) DEFAULT NULL,
    `date_updated` datetime DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `title` text DEFAULT NULL,
    `subtitle` text DEFAULT NULL,
    `content_text` text DEFAULT NULL,
    `description` text DEFAULT NULL,
    `parent_category_id`  bigint(20),
    `primary_image_url` varchar(255) DEFAULT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`parent_category_id`) REFERENCES `category`(id)
)DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;