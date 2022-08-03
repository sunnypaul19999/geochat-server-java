use geochatservertest;

/*inserting the starting value for sequence generators*/
insert into geouser_id_seq_generator (next_val) values(1);
insert into topic_id_seq_generator (next_val) values(1);
insert into sub_topic_id_seq_generator (next_val) values(1);
insert into sub_topic_meta_discuss_id_seq_generator (next_val) values(1);

insert into geouser_assumable_role values (1, "USER"), (2, "ADMIN");
insert into geopointrange (id,radius) values(1,500);
