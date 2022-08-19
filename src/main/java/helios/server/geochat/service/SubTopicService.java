package helios.server.geochat.service;

import java.util.List;

import helios.server.geochat.exceptions.serviceexceptions.topicserviceexception.TopicException;

import helios.server.geochat.exceptions.serviceexceptions.topicserviceexception.TopicNotFoundException;
import helios.server.geochat.model.SubTopic;

import helios.server.geochat.dto.request.SubTopicDTO;

import helios.server.geochat.exceptions.serviceexceptions.subtopicserviceexception.SubTopicException;
import helios.server.geochat.exceptions.serviceexceptions.subtopicserviceexception.SubTopicNotFoundException;

public interface SubTopicService {

  SubTopic getSubTopicEntityById(int topicId, int subTopicId) throws SubTopicNotFoundException;

  SubTopicDTO getSubTopic(int topicId, int subTopicId) throws SubTopicNotFoundException;

  List<SubTopicDTO> getPagedSubTopics(int topicId, int subTopicPageNumber) throws SubTopicException, TopicNotFoundException;

  List<SubTopicDTO> getAllSubTopics(int topicId) throws SubTopicException, TopicNotFoundException;

  SubTopicDTO addSubTopic(SubTopicDTO topicDTO) throws SubTopicException, TopicException;

  SubTopicDTO updateSubTopic(SubTopicDTO subTopicDTO) throws SubTopicException;

  SubTopicDTO deleteSubTopic(int topicId, int subTopicId) throws SubTopicException;
}
