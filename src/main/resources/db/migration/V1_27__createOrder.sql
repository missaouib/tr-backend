DROP TABLE IF EXISTS `customer_order`;

CREATE TABLE `customer_order`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_by` varchar(255) DEFAULT NULL,
    `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted` bit DEFAULT 0,
    `date_deleted` datetime DEFAULT NULL,
    `last_modified_by` varchar(255) DEFAULT NULL,
    `date_updated` datetime DEFAULT NULL,
    `status` varchar(255) DEFAULT NULL,
    `payment_process_type` varchar(255) DEFAULT NULL,
    `delivery_type` varchar(255) DEFAULT NULL,
    `buyer_name` varchar(255) DEFAULT NULL,
    `buyer_surname` varchar(255) DEFAULT NULL,
    `buyer_email` varchar(255) DEFAULT NULL,
    `buyer_address` varchar(255) DEFAULT NULL,
    `buyer_city` varchar(255) DEFAULT NULL,
    `city_postal_code` varchar(255) DEFAULT NULL,
    `buyer_phone` varchar(255) DEFAULT NULL,
    `total_price` integer DEFAULT 0,
    `note` varchar(255) DEFAULT NULL,
    `user_id` bigint(20) DEFAULT NULL,
    `shop` varchar(255) DEFAULT NULL,
    PRIMARY KEY(`id`)
)DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;