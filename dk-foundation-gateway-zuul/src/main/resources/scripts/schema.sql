DROP SCHEMA IF EXISTS `gateway` ;
CREATE SCHEMA IF NOT EXISTS `gateway` DEFAULT CHARACTER SET utf8 ;
USE `gateway`;
CREATE TABLE `gateway`.`routes` (
  `id` varchar(50) not null,
  `path` varchar(255) not null,
  `service_id` varchar(50) default null,
  `url` varchar(255) default null,
  `retryable` tinyint(1) default null,
  `enabled` tinyint(1) not null,
  `strip_prefix` int(11) default null,
  `api_name` varchar(255) default null,
  primary key (`id`)
) engine=innodb default charset=utf8;