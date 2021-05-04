DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` bit DEFAULT 0,
  `date_deleted` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `date_updated` datetime DEFAULT NULL,
  `variation_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `verified` boolean DEFAULT TRUE,
  `comment_description` text DEFAULT NULL,
  FOREIGN KEY(`variation_id`) REFERENCES `variation`(id),
  INDEX variation_ind (variation_id),
  FOREIGN KEY(`user_id`) REFERENCES `user`(id),
  INDEX user_ind (user_id),
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

