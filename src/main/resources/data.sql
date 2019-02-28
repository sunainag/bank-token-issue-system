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
