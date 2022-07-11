create table geouser_id_seqgenerator(
next_val bigint
)engine = MyISAM;

create table sub_topic_id_seqgenerator(
next_val bigint
)engine = MyISAM;

create table sub_topic_meta_discuss_id_seqgenerator(
next_val bigint
)engine = MyISAM;

/*inserting the starting value for sequence generators*/
insert into geouser_id_seqgenerator (next_val) values(1);

insert into sub_topic_id_seqgenerator (next_val) values(1);

insert into sub_topic_meta_discuss_id_seqgenerator (next_val) values(1);