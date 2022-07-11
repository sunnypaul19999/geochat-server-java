package helios.server.geochat.service.impl;

import java.util.List;
import java.util.Optional;

import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicPageNumberNotInRangeException;

import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.model.Topic;
import helios.server.geochat.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import helios.server.geochat.service.SubTopicService;

import helios.server.geochat.model.SubTopic;
import helios.server.geochat.repository.SubTopicRepository;
import helios.server.geochat.dto.request.SubTopicDTO;

import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;

@Service
public class SubTopicServiceImpl implements SubTopicService {

  @Autowired TopicService topicService;

  @Autowired SubTopicRepository subTopicRepository;

  @Override
  public SubTopic getSubTopicEntityById(int subTopicId) throws SubTopicNotFoundException {

    Optional<SubTopic> subTopic = subTopicRepository.findById(subTopicId);

    if (subTopic.isPresent()) {

      return subTopic.get();
    }

    throw new SubTopicNotFoundException(subTopicId, "GET_SUBTOPIC_BY_ID");
  }

  @Override
  public SubTopicDTO getSubTopicById(int subTopicId) throws SubTopicNotFoundException {

    Optional<SubTopic> subTopic = subTopicRepository.findById(subTopicId);

    if (subTopic.isPresent()) {

      return new SubTopicDTO(
          subTopic.get().getId(), subTopic.get().getTitle(), subTopic.get().getDescription());
    }

    throw new SubTopicNotFoundException(subTopicId, "GET_SUBTOPIC_BY_ID");
  }

  @Override
  public List<SubTopicDTO> getSubTopicsByPaged(int pageNumber) throws SubTopicException {

    if (pageNumber <= 0) {

      throw new SubTopicPageNumberNotInRangeException();
    }

    try {

      PageRequest pageable = PageRequest.of(pageNumber, 5);

      return subTopicRepository
          .findAll(pageable)
          .map(
              subTopic ->
                  new SubTopicDTO(subTopic.getId(), subTopic.getTitle(), subTopic.getDescription()))
          .stream()
          .toList();

    } catch (Exception e) {

      throw new SubTopicException("GET_SUB_TOPICS_PAGED", e.getMessage());
    }
  }

  @Override
  public List<SubTopicDTO> getSubAllTopics() throws SubTopicException {
    try {

      return subTopicRepository.findAll().stream()
          .map(
              subTopic ->
                  new SubTopicDTO(subTopic.getId(), subTopic.getTitle(), subTopic.getDescription()))
          .toList();

    } catch (Exception e) {

      throw new SubTopicException("GET_ALL_PAGED", e.getMessage());
    }
  }

  @Override
  public SubTopicDTO deleteSubTopic(int subTopicId) throws SubTopicException {

    try {

      SubTopic subTopic = getSubTopicEntityById(subTopicId);

      subTopicRepository.delete(subTopic);

      // returns the topic entity object after persisting the deleted entity
      return new SubTopicDTO(subTopic.getId(), subTopic.getTitle(), subTopic.getDescription());

    } catch (SubTopicNotFoundException e) {

      // not rethrowing to change the operation type
      throw new SubTopicNotFoundException(subTopicId, "DELETE");

    } catch (Exception e) {

      throw new SubTopicException("DELETE", e.getMessage());
    }
  }

  @Override
  public SubTopicDTO addSubTopic(SubTopicDTO subTopicDTO) throws SubTopicException, TopicException {

    SubTopic subTopic;

    try {

      subTopic = new SubTopic(subTopicDTO);

      Topic topic = topicService.getTopicEntityById(subTopicDTO.getTopicId());

      subTopic.setTopic(topic);

      subTopic = subTopicRepository.save(subTopic);

      return new SubTopicDTO(subTopic.getId(), subTopic.getTitle(), subTopic.getDescription());

    } catch (TopicException e) {
      throw e;
    } catch (Exception e) {

      throw new SubTopicException("ADD", e.getMessage());
    }
  }
}
