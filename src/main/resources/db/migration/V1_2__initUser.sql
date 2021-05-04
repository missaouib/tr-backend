INSERT INTO `user`
(`role`, `id`, `date_created`, `deleted`, `date_deleted`, `date_updated`, `email`, `name`, `password`, `reset_token`, `reset_token_expiration_date`,
 `surname`, `phone_number`, `address`, `birth_date`, `registration_token`, `registration_token_expiration_date`, `registration_confirmed`, `city`, `postal_code`) VALUES
('ROLE_ADMIN', 1, '2020-02-14', false , null, '2020-02-14', 'admin@gmail.com', 'Admin', '$2a$10$gDqfRHdeFeiopzJ3MSNiLOdYsTDU/RJWhTr5OJvGHE3355C5OLDDm',
'123123' , '2025-11-11 00:00:00', 'Adminic' , '1234567' , 'adresa', '2020-02-14', '123123' ,'2011-11-11 00:00:00', 1, null, null),
('ROLE_USER', 2, '2020-02-14', false , null, '2020-02-14', 'user@gmail.com', 'User', '$2a$10$gDqfRHdeFeiopzJ3MSNiLOdYsTDU/RJWhTr5OJvGHE3355C5OLDDm',
'123123' , '2025-11-11 00:00:00', 'UserPrz' , '1234567' , 'adresa', '2020-02-14', '123123' ,'2011-11-11 00:00:00', 1, 'Novi Sad', '21000');
