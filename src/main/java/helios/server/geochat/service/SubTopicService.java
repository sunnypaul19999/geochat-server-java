package helios.server.geochat.service;

import java.util.List;

import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import org.springframework.data.domain.Page;

import helios.server.geochat.model.SubTopic;

import helios.server.geochat.dto.request.SubTopicDTO;

import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;

public interface SubTopicService {

  SubTopic getSubTopicEntityById(int subTopicId) throws SubTopicNotFoundException;

  SubTopicDTO getSubTopicById(int subTopicId) throws SubTopicNotFoundException;

  List<SubTopicDTO> getSubTopicsByPaged(int pageNumber) throws SubTopicException;

  List<SubTopicDTO> getSubAllTopics() throws SubTopicException;

  SubTopicDTO deleteSubTopic(int topicId) throws SubTopicException;

  SubTopicDTO addSubTopic(SubTopicDTO topicDTO) throws SubTopicException, TopicException;
}
