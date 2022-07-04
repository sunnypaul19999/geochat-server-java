
drop database if exists geochatserver;

create database if not exists geochatserver;

use geochatserver;

create table if not exists geouser(
user_id int,
username varchar(50) not null,
password varchar(256) not null,
constraint user_id_pk primary key(user_id),
constraint username_un unique key(username)
);

create table if not exists geouser_assumable_role(
role_id int,
roletype varchar(250) unique not null,
constraint role_id primary key(role_id)
);

create table if not exists geouserrole(
user_id int unique,
role_id int,
constraint user_id_fk foreign key(user_id) references geouser(user_id) on delete cascade,
constraint role_id_fk foreign key(role_id) references geouser_assumable_role(role_id) on delete cascade
);

create table if not exists geopointrange(
radius int check(radius >= 100 & radius <= 10000) not null,
constraint radius_pk primary key(radius)
);

create table if not exists geopoint(
plus_code varchar(20),
lattitude numeric(16,14) not null,
longitude numeric(18,15) not null,
radius int,
constraint plus_code_pk primary key(plus_code),
constraint radius_fk foreign key(radius) references geopointrange(radius)
);

create table if not exists topic(
topic_id int,
topic_title varchar(250) not null,
plus_code varchar(20),
constraint topic_id_pk primary key(topic_id),
constraint plus_code_fk foreign key(plus_code) references geopoint(plus_code) on delete cascade
);

create table if not exists subtopic(
topic_id int,
sub_topic_id int,
sub_topic_title varchar(250) not null,
sub_topic_description varchar(4000) not null,
constraint sub_topic_id_pk primary key(sub_topic_id),
constraint topic_id_fk foreign key(topic_id) references topic(topic_id)
);

create table if not exists subtopicmetadiscuss(
meta_discuss_id int auto_increment,
message varchar(250) not null,
message_timestamp timestamp default current_timestamp(),
sub_topic_id int,
sender_id int,
constraint meta_discuss_id_pk primary key(meta_discuss_id),
constraint sub_topic_id_fk foreign key(sub_topic_id) references subtopic(sub_topic_id),
constraint sender_id_fk foreign key(sender_id) references geouser(user_id)
);

select * from geopoint;