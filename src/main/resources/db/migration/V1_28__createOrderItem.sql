DROP TABLE IF EXISTS `order_item`;

CREATE TABLE `order_item`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_by` varchar(255) DEFAULT NULL,
    `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted` bit DEFAULT 0,
    `date_deleted` datetime DEFAULT NULL,
    `last_modified_by` varchar(255) DEFAULT NULL,
    `date_updated` datetime DEFAULT NULL,
    `variation_id` bigint(20) DEFAULT NULL,
    `product_id` bigint(20) DEFAULT NULL,
    `quantity` integer DEFAULT NULL,
    `customer_order_id` bigint(20) DEFAULT NULL,
    FOREIGN KEY(`variation_id`) REFERENCES `variation`(id),
    INDEX variation_ind (variation_id),
    FOREIGN KEY(`product_id`) REFERENCES `product`(id),
    INDEX product_ind (product_id),
    PRIMARY KEY(`id`)
)DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;