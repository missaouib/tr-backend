DROP TABLE IF EXISTS `shopping_cart_item`;

CREATE TABLE `shopping_cart_item`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_by` varchar(255) DEFAULT NULL,
    `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted` bit DEFAULT 0,
    `date_deleted` datetime DEFAULT NULL,
    `last_modified_by` varchar(255) DEFAULT NULL,
    `date_updated` datetime DEFAULT NULL,
    `quantity` integer DEFAULT 0,
    `shopping_cart_id` bigint (20),
    `variation_id` bigint (20),
    `product_id` bigint (20),
    PRIMARY KEY(`id`),
    FOREIGN KEY(`product_id`) REFERENCES `product`(id),
    INDEX product_ind (product_id),
    FOREIGN KEY(`variation_id`) REFERENCES `variation`(id),
    INDEX variation_ind (variation_id),
    FOREIGN KEY(`shopping_cart_id`) REFERENCES `shopping_cart`(id),
    INDEX shopping_cart_ind (shopping_cart_id)
)DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;