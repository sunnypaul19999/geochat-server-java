package helios.server.geochat.service.impl;

import java.util.List;
import java.util.Optional;

import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicPageNumberNotInRangeException;
import helios.server.geochat.model.GeoPoint;
import helios.server.geochat.repository.GeoPointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import helios.server.geochat.model.Topic;

import helios.server.geochat.repository.TopicRepository;

import helios.server.geochat.service.TopicService;

import helios.server.geochat.dto.request.TopicDTO;

import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicNotFoundException;

@Service
public class TopicServiceImpl implements TopicService {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired GeoPointRepository geoPointRepository;

  @Autowired TopicRepository topicRepository;

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

        return new TopicDTO(topic.get().getTopicId(), topic.get().getTopicTitle());
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
          .map(topic -> new TopicDTO(topic.getTopicId(), topic.getTopicTitle()))
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
          .map(topic -> new TopicDTO(topic.getTopicId(), topic.getTopicTitle()))
          .toList();

    } catch (Exception e) {

      throw new TopicException("GET_ALL_TOPICS", e.getMessage());
    }
  }

  @Override
  public void updateTopic(TopicDTO topicDTO) throws TopicException {

    try {

      Topic topic = getTopicEntityById(topicDTO.getId());

      topic.setTopicTitle(topicDTO.getTopicTitle());

      topicRepository.save(topic);

    } catch (TopicNotFoundException e) {

      throw e;

    } catch (TopicException e) {

      logger.error(String.format("UPDATE_TOPIC %s", topicDTO.toString()), e);

      throw new TopicException(
          String.format("UPDATE_TOPIC %s", topicDTO.toString()), e.getMessage());
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
        return new TopicDTO(topic.get().getTopicId(), topic.get().getTopicTitle());

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
  public TopicDTO addTopic(TopicDTO topicDTO) throws TopicException {

    Topic newTopic;

    try {

      newTopic = new Topic(topicDTO);

      Optional<GeoPoint> geoPoint = geoPointRepository.findById(topicDTO.getPlusCode());

      logger.info(String.format("GeoPoint from database %s", geoPoint.get().getPlusCode()));

      // todo: dangerously setting optional value of geoPoint,
      //  must throw exception if geoPoint is null i.e, not found
      newTopic.setGeoPoint(geoPoint.get());

      newTopic = topicRepository.save(newTopic);

      topicDTO.setId(newTopic.getTopicId());

      return topicDTO;

    } catch (Exception e) {

      logger.error("Exception raised while add new topic", e);

      throw new TopicException("ADD", e.getMessage());
    }
  }
}
