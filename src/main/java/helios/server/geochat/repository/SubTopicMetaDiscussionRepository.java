package helios.server.geochat.repository;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import helios.server.geochat.model.SubTopicMetaDiscussion;

import java.util.List;

public interface SubTopicMetaDiscussionRepository
    extends JpaRepository<SubTopicMetaDiscussion, Integer> {

  Page<SubTopicMetaDiscussion> findByTopicTopicIdAndSubTopicId(
      int topicId, int subTopicId, Pageable pageable);

  List<SubTopicMetaDiscussion> findByTopicTopicIdAndSubTopicId(
      int topicId, int subTopicId, Sort sort);
}
