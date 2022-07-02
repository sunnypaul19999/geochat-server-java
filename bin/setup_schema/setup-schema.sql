
drop database geochatserver;

create database if not exists geochatserver;

use geochatserver;

create table if not exists geopoint(
plus_code varchar(20),
lattitude numeric(17,14) not null,
longitude numeric(17,14) not null,
constraint plus_code_pk primary key(plus_code)
);
