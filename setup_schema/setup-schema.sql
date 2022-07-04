
drop database geochatserver;

create database if not exists geochatserver;

use geochatserver;

create table if not exists geopoint(
plus_code varchar(20),
lattitude numeric(16,14) not null,
longitude numeric(18,15) not null,
constraint plus_code_pk primary key(plus_code)
);

create table if not exists topic(
topic_id int,
topic_title varchar(250) not null,
plus_code varchar(20),
constraint topic_id_pk primary key(topic_id),
constraint plus_code_fk foreign key(plus_code) references GEOPOINT(plus_code) on delete cascade
);

create table if not exists subtopic(
topic_id int,
sub_topic_id int,
sub_topic_title varchar(250) not null,
sub_topic_description varchar(4000) not null,
constraint sub_topic_id_pk primary key(sub_topic_id),
constraint topic_id_fk foreign key(topic_id) references TOPIC(topic_id)
);

select * from geopoint;