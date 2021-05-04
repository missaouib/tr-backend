DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `date_created` datetime NOT NULL,
  `deleted` bit DEFAULT 0,
  `date_deleted` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `date_updated` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NUll,
  `city` varchar(255) DEFAULT NUll,
  `postal_code` varchar(255) DEFAULT NUll,
  `phone_number` varchar(255) NOT NULL,
  `birth_date` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `registration_token` varchar(255) DEFAULT NULL,
  `registration_token_expiration_date` datetime DEFAULT NULL,
  `registration_confirmed` bit DEFAULT 0,
  `reset_token` varchar(255) DEFAULT NULL,
  `reset_token_expiration_date` datetime DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `surname` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

