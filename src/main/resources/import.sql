use bank_token_issue_system;

CREATE DATABASE IF NOT EXISTS bank_token_issue_system;
USE bank_token_issue_system;

drop table if exists token_service_mapping;
drop table if exists token;
drop table if exists service_counter_mapping;
drop table if exists counter;
drop table if exists service;
drop table if exists customer;
drop table if exists address;
commit;

CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address_line1` varchar(255) NOT NULL,
  `address_line2` varchar(255) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `state` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `zip_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `counter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `queue_size` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `mobile` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `address_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`mobile`),
   FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `next_service_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`name`)
) ENGINE=InnoDB;

CREATE TABLE `service_counter_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL,
  `counter_id` int(11) NOT NULL,
  `service_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`counter_id`) REFERENCES `counter` (`id`),
  FOREIGN KEY (`service_id`) REFERENCES `service` (`id`)
) ENGINE=InnoDB ;

CREATE TABLE `token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime NOT NULL,
  `number` int(11) NOT NULL,
  `status_code` varchar(255) NOT NULL,
  `current_counter_id` int(11) DEFAULT NULL,
  `current_service_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  FOREIGN KEY (`current_service_id`) REFERENCES `service` (`id`),
  FOREIGN KEY (`current_counter_id`) REFERENCES `counter` (`id`)
) ENGINE=InnoDB ;

CREATE TABLE `token_service_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comments` varchar(255) DEFAULT NULL,
  `service_id` bigint(20) NOT NULL,
  `token_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`service_id`) REFERENCES `service` (`id`),
  FOREIGN KEY (`token_id`) REFERENCES `token` (`id`)
) ENGINE=InnoDB;


insert into counter(`number`,`priority`,`queue_size`) values (1, 'HIGH', 0);
insert into counter(`number`,`priority`,`queue_size`) values (2, 'HIGH', 0);
insert into counter(`number`,`priority`,`queue_size`) values (3, 'HIGH', 0);
insert into counter(`number`,`priority`,`queue_size`) values (4, 'NORMAL', 0);
insert into counter(`number`,`priority`,`queue_size`) values (5, 'NORMAL', 0);
insert into counter(`number`,`priority`,`queue_size`) values (6, 'NORMAL', 0);

insert into service(`name`,`type`,`next_service_id`) values ('A', 'PREMIUM', null);
insert into service(`name`,`type`,`next_service_id`) values ('B', 'PREMIUM', null);
insert into service(`name`,`type`,`next_service_id`) values ('C', 'REGULAR', null);
insert into service(`name`,`type`,`next_service_id`) values ('D', 'REGULAR', null);
insert into service(`name`,`type`,`next_service_id`) values ('E', 'REGULAR', null);
insert into service(`name`,`type`,`next_service_id`) values ('F', 'REGULAR', null);

update service set next_service_id = 3 where id = 2;










insert into service_counter_mapping(`type`,`counter_id`,`service_id`) values ('PREMIUM', 1, 1);
insert into service_counter_mapping(`type`,`counter_id`,`service_id`) values ('PREMIUM', 2, 2);
insert into service_counter_mapping(`type`,`counter_id`,`service_id`) values ('PREMIUM', 2, 5);
insert into service_counter_mapping(`type`,`counter_id`,`service_id`) values ('PREMIUM', 3, 3);

insert into service_counter_mapping(`type`,`counter_id`,`service_id`) values ('REGULAR', 4, 1);
insert into service_counter_mapping(`type`,`counter_id`,`service_id`) values ('REGULAR', 5, 2);
insert into service_counter_mapping(`type`,`counter_id`,`service_id`) values ('REGULAR', 5, 6);
insert into service_counter_mapping(`type`,`counter_id`,`service_id`) values ('REGULAR', 6, 3);
