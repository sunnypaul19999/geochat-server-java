package helios.server.geochat.service;

import helios.server.geochat.dto.request.SubTopicMetaDiscussionDTO;
import helios.server.geochat.exceptions.serviceexceptions.geouserserviceexception.GeoUserNotFoundException;
import helios.server.geochat.exceptions.serviceexceptions.geouserserviceexception.subtopicmetadiscussionserviceexception.SubTopicMetaDiscussionException;
import helios.server.geochat.exceptions.serviceexceptions.geouserserviceexception.subtopicmetadiscussionserviceexception.SubTopicMetaDiscussionNotFoundException;
import helios.server.geochat.exceptions.serviceexceptions.geouserserviceexception.subtopicmetadiscussionserviceexception.SubTopicMetaDiscussionPageNumberNotInRangeException;
import helios.server.geochat.exceptions.serviceexceptions.subtopicserviceexception.SubTopicNotFoundException;
import helios.server.geochat.exceptions.serviceexceptions.topicserviceexception.TopicException;
import helios.server.geochat.model.SubTopicMetaDiscussion;

import java.util.List;

public interface SubTopicMetaDiscussionService {

  List<SubTopicMetaDiscussionDTO> getAllMessages(int topicId, int subtopicId)
      throws SubTopicNotFoundException;

  List<SubTopicMetaDiscussionDTO> getPagedMessages(
      int topicId, int subtopicId, int messagePageNumber)
      throws SubTopicNotFoundException, SubTopicMetaDiscussionPageNumberNotInRangeException;

  SubTopicMetaDiscussion getMessageById(int topicId, int subtopicId, int messageId)
      throws SubTopicMetaDiscussionNotFoundException, SubTopicMetaDiscussionException;

  int addMessage(SubTopicMetaDiscussionDTO subTopicMetaDiscussDTO)
      throws SubTopicNotFoundException, SubTopicMetaDiscussionException, GeoUserNotFoundException, TopicException;

  void deleteMessage(int topicId, int subtopicId, int messageId) throws SubTopicMetaDiscussionException;
}
