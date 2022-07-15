package helios.server.geochat.service;

import java.util.List;

import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;

import helios.server.geochat.model.SubTopic;

import helios.server.geochat.dto.request.SubTopicDTO;

import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;

public interface SubTopicService {

  SubTopic getSubTopicEntityById(int topicId, int subTopicId) throws SubTopicNotFoundException;

  SubTopicDTO getSubTopic(int topicId, int subTopicId) throws SubTopicNotFoundException;

  List<SubTopicDTO> getPagedSubTopics(int topicId, int subTopicPageNumber) throws SubTopicException;

  List<SubTopicDTO> getAllSubTopics(int topicId) throws SubTopicException;

  SubTopicDTO addSubTopic(SubTopicDTO topicDTO) throws SubTopicException, TopicException;

  void updateSubTopic(SubTopicDTO subTopicDTO) throws SubTopicException;

  SubTopicDTO deleteSubTopic(int topicId, int subTopicId) throws SubTopicException;
}
