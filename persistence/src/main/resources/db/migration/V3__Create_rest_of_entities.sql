CREATE TABLE `engine_capacity`
(
  `id`                  INTEGER AUTO_INCREMENT,
  `capacity`            DOUBLE      NOT NULL UNIQUE,
  `unit_of_measurement` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `race`
(
  `id`         INTEGER AUTO_INCREMENT,
  `name`       VARCHAR(50) NOT NULL,
  `start_time` TIMESTAMP   NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `team`
(
  `id`   INTEGER AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL UNIQUE,
  PRIMARY KEY (`id`)
);

CREATE TABLE `contestant`
(
  `id`      INTEGER AUTO_INCREMENT,
  `name`    VARCHAR(50),
  `team_id` INTEGER REFERENCES `team` (`id`),
  `engine_capacity_id` INTEGER REFERENCES `engine_capacity`(`id`),
  PRIMARY KEY (`id`)
);

CREATE TABLE `contestant_race`
(
  `contestant_id` INTEGER REFERENCES `contestant` (`id`),
  `race_id`       INTEGER REFERENCES `race` (`id`),
  PRIMARY KEY (`contestant_id`, `race_id`)
);

INSERT INTO `engine_capacity`
(
  `id`,
  `capacity`,
  `unit_of_measurement`
) VALUES
  (1, 125, 'cc'),
  (2, 250, 'cc'),
  (3, 650, 'cc');

INSERT INTO `race`
(`name`, `start_time`)
VALUES
  ('Race 1', '2017-04-07 19:35:00'),
  ('Race 2', '2017-05-07 19:35:00'),
  ('Race 3', '2017-06-07 19:35:00'),
  ('Race 4', '2017-07-07 19:35:00'),
  ('Race 5', '2017-08-07 19:35:00');


INSERT INTO `team`
(`id`, `name`)
VALUES
  (1, 'Suzuki'),
  (2, 'Honda'),
  (3, 'Team 3'),
  (4, 'Team 4'),
  (5, 'Team 5');

INSERT INTO `contestant`
(`id`, `name`, `team_id`, `engine_capacity_id`)
VALUES
  (1, 'Contestant 1', 1, 1),
  (2, 'Contestant 2', 1, 2),
  (3, 'Contestant 3', 2, 3),
  (4, 'Contestant 4', 3, 1),
  (5, 'Contestant 5', 3, 2),
  (6, 'Contestant 6', 3, 3),
  (7, 'Contestant 7', 3, 1),
  (8, 'Contestant 8', 3, 2),
  (9, 'Contestant 9', 4, 3),
  (10, 'Contestant 10', 5, 1);

INSERT INTO `contestant_race`
(`contestant_id`, `race_id`)
VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (1, 4),
  (1, 5),
  (2, 1),
  (2, 2),
  (2, 3),
  (3, 4),
  (3, 5),
  (4, 1),
  (5, 2),
  (6, 3),
  (7, 4),
  (8, 5),
  (9, 1),
  (10, 2);