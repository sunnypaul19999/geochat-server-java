package helios.server.geochat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import helios.server.geochat.model.SubTopicMetaDiscussion;

import java.awt.print.Pageable;
import java.util.List;

public interface SubTopicMetaDiscussionRepository
    extends JpaRepository<SubTopicMetaDiscussion, Integer> {

  List<SubTopicMetaDiscussion> findBySubTopicId(int subTopicId);

  // Page<SubTopicMetaDiscussion> findBySubTopicId(int subTopicId, Pageable pageable);
}
