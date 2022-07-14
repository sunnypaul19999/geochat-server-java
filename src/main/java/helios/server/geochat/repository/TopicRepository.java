package helios.server.geochat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import helios.server.geochat.model.Topic;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

  Page<Topic> findByGeoPointPlusCode(String geoPointPLusCode, Pageable pageable);

  List<Topic> findAllByGeoPointPlusCode(String geoPointPLusCode, Sort sort);
}
