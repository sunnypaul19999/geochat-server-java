package helios.server.geochat.service;

import java.util.List;

import org.springframework.data.domain.Page;

import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.model.Topic;

public interface TopicService {

    Page<Topic> getTopics(int pageNumber) throws TopicException;

    List<Topic> getAllTopics() throws TopicException;

    Topic deleteTopic(int topicId) throws TopicException;

    Topic addTopic(TopicDTO topicDTO) throws TopicException;
}
