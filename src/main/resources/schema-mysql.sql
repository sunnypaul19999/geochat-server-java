
drop database if exists geochatserver;

create database if not exists geochatserver;

use geochatserver;

create table if not exists geouser(
user_id int auto_increment,
username varchar(50) not null,
password varchar(256) not null,
constraint user_id_pk primary key(user_id),
constraint username_un unique key(username)
)engine = MEMORY;


create table if not exists geouser_assumable_role(
role_id int auto_increment not null,
role_type varchar(250) unique not null,
constraint role_id primary key(role_id)
)engine = MEMORY;

create table if not exists geouserrole(
user_role_id int auto_increment not null,
user_id int unique not null,
role_id int,
constraint user_role_id primary key(user_role_id),
constraint user_id_fk foreign key(user_id) references geouser(user_id) on delete cascade,
constraint role_id_fk foreign key(role_id) references geouser_assumable_role(role_id) on delete cascade
)engine = MEMORY;

create table if not exists geopointrange(
id int,
radius int check(radius >= 100 & radius <= 10000) not null,
constraint id primary key(id)
)engine = MEMORY;
/*
create table if not exists geopointrangeconfig(
id int,
max_radius int,
min_radius int,
default_radius int not null,
constraint id primary key(id)
);*/

create table if not exists geopoint(
plus_code varchar(20),
lattitude numeric(16,14) not null,
longitude numeric(18,15) not null,
constraint plus_code_pk primary key(plus_code)
)engine = MEMORY;

create table if not exists topic(
topic_id int,
topic_title varchar(250) not null,
plus_code varchar(20),
constraint topic_id_pk primary key(topic_id),
constraint plus_code_fk foreign key(plus_code) references geopoint(plus_code) on delete cascade
)engine = MEMORY;

create table if not exists subtopic(
topic_id int,
sub_topic_id int,
sub_topic_title varchar(250) not null,
sub_topic_description varchar(4000) not null,
constraint sub_topic_id_pk primary key(sub_topic_id),
constraint topic_id_fk foreign key(topic_id) references topic(topic_id)
)engine = MEMORY;

create table if not exists subtopicmetadiscuss(
meta_discuss_id int auto_increment,
message varchar(250) not null,
message_timestamp timestamp default current_timestamp(),
sub_topic_id int,
sender_id int,
constraint meta_discuss_id_pk primary key(meta_discuss_id),
constraint sub_topic_id_fk foreign key(sub_topic_id) references subtopic(sub_topic_id),
constraint sender_id_fk foreign key(sender_id) references geouser(user_id)
)engine = MEMORY;

create unique index un_index_geouser_username
using btree
on geouserrole(user_id, role_id);

create table if not exists geouser_id_seq_generator(
next_val bigint
)engine = MyISAM;

create table if not exists sub_topic_id_seq_generator(
next_val bigint
)engine = MyISAM;

create table if not exists sub_topic_meta_discuss_id_seq_generator(
next_val bigint
)engine = MyISAM;





