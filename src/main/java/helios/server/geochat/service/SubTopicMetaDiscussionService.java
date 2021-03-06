package helios.server.geochat.service;

import helios.server.geochat.dto.request.SubTopicMetaDiscussionDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionPageNumberNotInRangeException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
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
