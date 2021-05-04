DROP TABLE IF EXISTS `counter_slug`;

CREATE TABLE `counter_slug` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` bit DEFAULT 0,
  `date_deleted` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `date_updated` datetime DEFAULT NULL,
  `entity_name` varchar(255) DEFAULT NULL,
  `current_count` integer DEFAULT 0,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

