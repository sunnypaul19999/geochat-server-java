create table geouser_id_seqgenerator(
next_val bigint
)engine = MyISAM;

insert into geouser_id_seqgenerator (next_val) values(1)