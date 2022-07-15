package helios.server.geochat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import helios.server.geochat.model.SubTopic;

import java.util.List;
import java.util.Optional;

public interface SubTopicRepository extends JpaRepository<SubTopic, Integer> {

  Page<SubTopic> findByTopicTopicId(int topicId, Pageable pageable);

  List<SubTopic> findAllByTopicTopicId(int topicId, Sort sort);

  Optional<SubTopic> findByTopicTopicIdAndId(int topicId, int subTopicId);
}
