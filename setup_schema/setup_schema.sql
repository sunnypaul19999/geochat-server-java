use geochatserver;

insert into geopointrange (id,radius) values(1,500);

select * from geopoint;

select * from geopointrange;

select * from geouser;

select * from topic;

/*6FJ42222+22*/

insert into topic values(1,"this my topic", "6FJ42222+22");

insert into topic values(2,"this my 2 second topic", "6FJ42222+22");

insert into topic values(3,"this my 3 topic", "6FJ42222+22");

insert into topic values(4,"this my 4 topic", "6FJ42222+22");

insert into topic values(5,"this my 5 topic", "6FJ42222+22");

insert into topic values(6,"this my 6 topic", "6FJ42222+22");
/*
CREATE UNIQUE INDEX un_index_geouser_username
using hash
on geouser(username);

ALTER TABLE geouser
DROP INDEX un_index_geouser_username;
*/

