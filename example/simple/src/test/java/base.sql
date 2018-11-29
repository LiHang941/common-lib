-- create user test with password 'test';
-- create database  test owner test;
-- psql -d test -U test -h 127.0.0.1 -p 5432


drop table if exists "user";
create table "user"
(
  user_id     varchar(40) primary key,
  user_name   varchar(40)                         not null,
  create_time timestamp default current_timestamp not null,
  user_status integer   default 0                 not null
);


drop table if exists "test";
create table "test"
(
  id     bigserial primary key,
  test_name   varchar(40)                         not null
);
