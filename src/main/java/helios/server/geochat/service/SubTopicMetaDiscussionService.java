package helios.server.geochat.service;

import helios.server.geochat.dto.request.SubTopicMetaDiscussionDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;
import helios.server.geochat.model.SubTopicMetaDiscussion;

import java.util.List;

public interface SubTopicMetaDiscussionService {

  List<SubTopicMetaDiscussionDTO> getAllMessages(int subtopicId) throws SubTopicNotFoundException;

  List<SubTopicMetaDiscussionDTO> getPagedMessages(int subtopicId) throws SubTopicNotFoundException;

  SubTopicMetaDiscussion getMessageById(int messageId)
      throws SubTopicMetaDiscussionNotFoundException, SubTopicMetaDiscussionException;

  int addMessage(SubTopicMetaDiscussionDTO subTopicMetaDiscussDTO)
      throws SubTopicNotFoundException, SubTopicMetaDiscussionException, GeoUserNotFoundException;

  void deleteMessage(int messageId) throws SubTopicMetaDiscussionException;
}
