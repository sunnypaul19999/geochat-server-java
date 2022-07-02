
drop database geochatserver;

create database if not exists geochatserver;

use geochatserver;

create table if not exists geopoint(
plus_code int auto_increment,
lattitude numeric(17,14) not null,
longitude numeric(17,14) not null,
constraint plus_code_pk primary key(plus_code)
);

create table if not exists topic(
topic_id int auto_increment,
topic_title varchar(250) not null,
plus_code int,
constraint topic_id_pk primary key(topic_id),
constraint plus_code_fk foreign key(plus_code) references GEOPOINT(plus_code)
);

/*
create table if not exists geopoint(
plus_code int auto_increment,
lattitude float not null,
longitude float not null,
constraint plus_code_pk primary key(plus_code)
);
*/
