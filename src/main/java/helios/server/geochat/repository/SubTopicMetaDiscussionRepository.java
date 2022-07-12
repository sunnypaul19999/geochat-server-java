package helios.server.geochat.repository;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import helios.server.geochat.model.SubTopicMetaDiscussion;

import java.util.List;

public interface SubTopicMetaDiscussionRepository
    extends JpaRepository<SubTopicMetaDiscussion, Integer> {

  List<SubTopicMetaDiscussion> findBySubTopicId(int subTopicId, Sort sort);

  Page<SubTopicMetaDiscussion> findBySubTopicId(int subTopicId, Pageable pageable);
}
