use geochatserver;

select * from geopoint;

select * from geouser;

select * from geouserrole;

select * from geouser_assumable_role;



select * from geopointrange;

select * from topic;

select * from subtopic;

select * from subtopicmetadiscuss;

/*6FJ42222+22*/


insert into subtopic (topic_id, sub_topic_id, sub_topic_title, sub_topic_description) values(1,1, "This subtopic 1", "subtopic description 1");

insert into subtopic (topic_id, sub_topic_id, sub_topic_title, sub_topic_description)  values(1,2, "This subtopic 2", "subtopic description 2");

insert into subtopic (topic_id, sub_topic_id, sub_topic_title, sub_topic_description)  values(1,3, "This subtopic 3", "subtopic description 3");

insert into subtopic (topic_id, sub_topic_id, sub_topic_title, sub_topic_description)  values(1,4, "This subtopic 4", "subtopic description 4");



select geouser_assumable_role.role_id, geouser_assumable_role.role_type
from geouserrole /*joint table*/
left outer join geouser_assumable_role on geouser_assumable_role.role_id = geouserrole.role_id
where geouserrole.user_id = 1
