package helios.server.geochat.service.impl;

import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.exceptions.serviceexceptions.geopointserviceexception.GeoPointNotRegisteredException;
import helios.server.geochat.exceptions.serviceexceptions.topicserviceexception.TopicException;
import helios.server.geochat.exceptions.serviceexceptions.topicserviceexception.TopicNotFoundException;
import helios.server.geochat.exceptions.serviceexceptions.topicserviceexception.TopicPageNumberNotInRangeException;
import helios.server.geochat.model.GeoPoint;
import helios.server.geochat.model.Topic;
import helios.server.geochat.repository.TopicRepository;
import helios.server.geochat.service.GeoPointService;
import helios.server.geochat.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final GeoPointService geoPointService;

  private final TopicRepository topicRepository;

  public TopicServiceImpl(GeoPointService geoPointService, TopicRepository topicRepository) {

    this.geoPointService = geoPointService;
    this.topicRepository = topicRepository;
  }

  @Override
  public Topic getTopicEntityById(int topicId) throws TopicException {

    try {
      Optional<Topic> topic = topicRepository.findById(topicId);

      if (topic.isPresent()) {

        return topic.get();
      }

      throw new TopicNotFoundException(topicId, "GET_TOPIC_ENTITY_BY_ID");

    } catch (TopicNotFoundException e) {

      throw e;

    } catch (Exception e) {

      logger.error(e.getMessage(), e);

      throw new TopicException("GET_TOPIC_ENTITY_BY_ID", e.getMessage());
    }
  }

  @Override
  public TopicDTO getTopicById(int id) throws TopicException {

    try {

      Optional<Topic> topic = topicRepository.findById(id);

      if (topic.isPresent()) {

        return new TopicDTO(
            topic.get().getGeoPoint().getPlusCode(),
            topic.get().getTopicId(),
            topic.get().getTopicTitle());
      }

    } catch (Exception e) {

      throw new TopicException("GET_TOPIC_BY_ID", e.getMessage());
    }

    throw new TopicNotFoundException(id, "GET_TOPIC_BY_ID");
  }

  @Override
  public List<TopicDTO> getPagedTopics(int pageNumber, String geoPointPLusCode)
      throws TopicException {

    if (pageNumber <= 0) {

      throw new TopicPageNumberNotInRangeException();
    }

    try {

      Pageable pageable = PageRequest.of(pageNumber - 1, 5, Sort.by(Sort.Order.asc("topicId")));

      return topicRepository
          .findByGeoPointPlusCode(geoPointPLusCode, pageable)
          .map(topic -> new TopicDTO(geoPointPLusCode, topic.getTopicId(), topic.getTopicTitle()))
          .stream()
          .toList();

    } catch (Exception e) {

      logger.error(e.getMessage(), e);

      throw new TopicException("GET_TOPICS_PAGED", e.getMessage());
    }
  }

  @Override
  public List<TopicDTO> getAllTopics(String geoPointPLusCode) throws TopicException {

    try {

      return topicRepository
          .findAllByGeoPointPlusCode(geoPointPLusCode, Sort.by(Sort.Order.asc("topicId")))
          .stream()
          .map(topic -> new TopicDTO(geoPointPLusCode, topic.getTopicId(), topic.getTopicTitle()))
          .toList();

    } catch (Exception e) {

      throw new TopicException("GET_ALL_TOPICS", e.getMessage());
    }
  }

  @Override
  public TopicDTO updateTopic(TopicDTO topicDTO) throws TopicException {

    try {

      Topic topic = getTopicEntityById(topicDTO.getId());

      topic.setTopicTitle(topicDTO.getTopicTitle());

      Topic updatedTopic = topicRepository.save(topic);

      return new TopicDTO(
          updatedTopic.getGeoPoint().getPlusCode(),
          updatedTopic.getTopicId(),
          updatedTopic.getTopicTitle());

    } catch (TopicNotFoundException e) {

      throw e;

    } catch (TopicException e) {

      logger.error(String.format("UPDATE_TOPIC %s", topicDTO), e);

      throw new TopicException(String.format("UPDATE_TOPIC %s", topicDTO), e.getMessage());
    }
  }

  @Override
  public TopicDTO deleteTopic(int topicId) throws TopicException {

    Optional<Topic> topic;

    try {

      topic = topicRepository.findById(topicId);

      if (topic.isPresent()) {

        topicRepository.delete(topic.get());

        // returns the topic entity object after persisting the deleted entity
        return new TopicDTO(
            topic.get().getGeoPoint().getPlusCode(),
            topic.get().getTopicId(),
            topic.get().getTopicTitle());

      } else {

        throw new TopicNotFoundException(topicId, "DELETE");
      }
    } catch (TopicException e) {

      throw e;

    } catch (Exception e) {

      throw new TopicException("DELETE", e.getMessage());
    }
  }

  @Override
  public TopicDTO addTopic(TopicDTO topicDTO)
      throws TopicException, GeoPointNotRegisteredException {

    Topic newTopic;

    try {

      newTopic = new Topic(topicDTO);

      GeoPoint geoPoint = geoPointService.isGeoPointRegistered(topicDTO.getPlusCode());

      newTopic.setGeoPoint(geoPoint);

      newTopic = topicRepository.save(newTopic);

      topicDTO.setId(newTopic.getTopicId());

      return topicDTO;

    } catch (GeoPointNotRegisteredException e) {

      throw e;

    } catch (Exception e) {

      logger.error("Exception raised while add new topic", e);

      throw new TopicException("ADD", e.getMessage());
    }
  }
}
