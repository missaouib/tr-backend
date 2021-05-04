DROP TABLE IF EXISTS `product_attribute_category`;

CREATE TABLE `product_attribute_category`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_by` varchar(255) DEFAULT NULL,
    `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted` bit DEFAULT 0,
    `date_deleted` datetime DEFAULT NULL,
    `last_modified_by` varchar(255) DEFAULT NULL,
    `date_updated` datetime DEFAULT NULL,
     `name` varchar(255) NOT NULL,
    `product_id` bigint(20) NOT NULL,
    `attribute_category_id`  bigint(20) NOT NULL,
    `attribute_value_id`  bigint(20) NOT NULL,
     PRIMARY KEY(`id`),
     FOREIGN KEY(`product_id`) REFERENCES `product`(id),
     FOREIGN KEY(`attribute_category_id`) REFERENCES `attribute_category`(id),
     FOREIGN KEY(`attribute_value_id`) REFERENCES `attribute_value`(id)
)DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;