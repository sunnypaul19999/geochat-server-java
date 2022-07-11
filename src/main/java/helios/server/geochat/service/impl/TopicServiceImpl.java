package helios.server.geochat.service.impl;

import java.util.List;
import java.util.Optional;

import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicPageNumberNotInRangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

  @Autowired TopicRepository topicRepository;

  @Override
  public TopicDTO getTopic(int id) throws TopicException {

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
  public List<TopicDTO> getTopicsByPage(int pageNumber) throws TopicException {

    if (pageNumber <= 0) {

      throw new TopicPageNumberNotInRangeException();
    }

    try {

      Pageable pageable = PageRequest.of(pageNumber - 1, 5);

      List<TopicDTO> list =
          topicRepository
              .findAll(pageable)
              .map(topic -> new TopicDTO(topic.getTopicId(), topic.getTopicTitle()))
              .stream()
              .toList();

      return list;

    } catch (Exception e) {

      logger.error(e.getMessage(), e);

      throw new TopicException("GET_TOPICS_PAGED", e.getMessage());
    }
  }

  @Override
  public List<TopicDTO> getAllTopics() throws TopicException {

    try {

      return topicRepository.findAll().stream()
          .map(topic -> new TopicDTO(topic.getTopicId(), topic.getTopicTitle()))
          .toList();

    } catch (Exception e) {

      throw new TopicException("GET_ALL_TOPICS", e.getMessage());
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

    Topic topic;

    try {

      topic = new Topic(topicDTO);

      topic = topicRepository.save(topic);

      return new TopicDTO(topic.getTopicId(), topic.getTopicTitle());

    } catch (Exception e) {

      throw new TopicException("ADD", e.getMessage());
    }
  }
}
