CREATE TABLE `users`
(
  `id`         INTEGER                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            AUTO_INCREMENT,
  `last_name`  VARCHAR(30),
  `first_name` VARCHAR(30),
  `is_active`  TINYINT(1)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   DEFAULT 1,
  `logged_in`  TINYINT(1)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   DEFAULT 0,
  `last_login` DATETIME                            NULL                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                DEFAULT NULL,
  `email`      VARCHAR(255) UNIQUE,
  `password`   VARCHAR(128),
  `salt`       VARCHAR(32),
  created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`)
);


INSERT INTO `users`
(`id`,
 `last_name`,
 `first_name`,
 `is_active`,
 `logged_in`,
 `last_login`,
 `email`,
 `password`,
 `salt`,
 `created_at`,
 `updated_at`)
VALUES (1,
  'Last Name 1',
  'First name 1',
  1,
  NULL,
  NULL,
  'a@b.com',
  '1456965399174b8e744879654679a01c4079529e35f748668e8f310bf6bff4e0de001c9078b056e94b070f79705de9fdd8ddd6822b8f72d9d188f9450585eb41',
  '!2?6m????>~?^?\"???n?vp????\rX',
  '2017-01-07 19:35:04',
  '2017-01-07 19:35:04');

INSERT INTO `users`
(`id`,
 `last_name`,
 `first_name`,
 `is_active`,
 `logged_in`,
 `last_login`,
 `email`,
 `password`,
 `salt`,
 `created_at`,
 `updated_at`)
VALUES (2,
  'Last Name 1',
  'First name 1',
  1,
  NULL,
  NULL,
  'user@b.com',
  '1456965399174b8e744879654679a01c4079529e35f748668e8f310bf6bff4e0de001c9078b056e94b070f79705de9fdd8ddd6822b8f72d9d188f9450585eb41',
  '!2?6m????>~?^?\"???n?vp????\rX',
  '2017-01-07 19:35:04',
  '2017-01-07 19:35:04');