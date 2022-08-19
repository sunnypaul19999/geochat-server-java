package helios.server.geochat.service;

import java.util.List;

import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.exceptions.serviceexceptions.geopointserviceexception.GeoPointNotRegisteredException;
import helios.server.geochat.exceptions.serviceexceptions.topicserviceexception.TopicException;
import helios.server.geochat.model.Topic;

public interface TopicService {

  Topic getTopicEntityById(int topicId) throws TopicException;

  TopicDTO getTopicById(int id) throws TopicException;

  List<TopicDTO> getPagedTopics(int pageNumber, String geoPointPLusCode) throws TopicException;

  List<TopicDTO> getAllTopics(String geoPointPLusCode) throws TopicException;

  TopicDTO updateTopic(TopicDTO topicDTO) throws TopicException;

  TopicDTO deleteTopic(int topicId) throws TopicException;

  TopicDTO addTopic(TopicDTO topicDTO) throws TopicException, GeoPointNotRegisteredException;
}
