use geochatserver;

insert into geopointrange (id,radius) values(1,500);

select * from geopoint;

select * from geopointrange;

/*
CREATE UNIQUE INDEX un_index_geouser_username
using hash
on geouser(username);

ALTER TABLE geouser
DROP INDEX un_index_geouser_username;
*/

create table geouser_id_seqgenerator(
next_val bigint
)engine = MyISAM;

insert into geouser_id_seqgenerator (next_val) values(1)