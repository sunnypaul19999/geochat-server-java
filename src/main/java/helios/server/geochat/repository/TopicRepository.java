package helios.server.geochat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import helios.server.geochat.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

}
