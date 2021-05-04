DROP TABLE IF EXISTS `shopping_cart`;

CREATE TABLE `shopping_cart`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_by` varchar(255) DEFAULT NULL,
    `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted` bit DEFAULT 0,
    `date_deleted` datetime DEFAULT NULL,
    `last_modified_by` varchar(255) DEFAULT NULL,
    `date_updated` datetime DEFAULT NULL,
    `status` boolean DEFAULT TRUE,
    `user_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`user_id`) REFERENCES `user`(id)
)DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;