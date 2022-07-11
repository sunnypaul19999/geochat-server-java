package helios.server.geochat.service;

import java.util.List;

import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.model.Topic;

public interface TopicService {

  Topic getTopicEntityById(int topicId) throws TopicException;

  TopicDTO getTopic(int id) throws TopicException;

  List<TopicDTO> getTopicsByPage(int pageNumber) throws TopicException;

  List<TopicDTO> getAllTopics() throws TopicException;

  TopicDTO deleteTopic(int topicId) throws TopicException;

  TopicDTO addTopic(TopicDTO topicDTO) throws TopicException;
}
