package helios.server.geochat.service.impl;

import java.util.List;
import java.util.Optional;

import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicPageNumberNotInRangeException;

import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicNotFoundException;
import helios.server.geochat.model.Topic;
import helios.server.geochat.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
  private Logger logger = LoggerFactory.getLogger(getClass());
  private Exception e;

  @Override
  public SubTopic getSubTopicEntityById(int topicId, int subTopicId)
      throws SubTopicNotFoundException {

    Optional<SubTopic> subTopic = subTopicRepository.findByTopicTopicIdAndId(topicId, subTopicId);

    if (subTopic.isPresent()) {

      return subTopic.get();
    }

    throw new SubTopicNotFoundException(subTopicId, "GET_SUBTOPIC_BY_ID");
  }

  @Override
  public SubTopicDTO getSubTopic(int topicId, int subTopicId) throws SubTopicNotFoundException {
    // todo: check if topic exits

    Optional<SubTopic> subTopic = subTopicRepository.findByTopicTopicIdAndId(topicId, subTopicId);

    if (subTopic.isPresent()) {

      return new SubTopicDTO(
          subTopic.get().getId(), subTopic.get().getTitle(), subTopic.get().getDescription());
    }

    throw new SubTopicNotFoundException(subTopicId, "GET_SUBTOPIC_BY_ID");
  }

  @Override
  public List<SubTopicDTO> getPagedSubTopics(int topicId, int subTopicPageNumber)
      throws SubTopicException, TopicNotFoundException {

    if (subTopicPageNumber <= 0) {

      throw new SubTopicPageNumberNotInRangeException();
    }

    try {
      topicService.getTopicEntityById(topicId);

      PageRequest pageable =
          PageRequest.of(subTopicPageNumber - 1, 5, Sort.by(Sort.Order.asc("id")));

      return subTopicRepository
          .findByTopicTopicId(topicId, pageable)
          .map(
              subTopic ->
                  new SubTopicDTO(
                      subTopic.getTopic().getTopicId(),
                      subTopic.getId(),
                      subTopic.getTitle(),
                      subTopic.getDescription()))
          .stream()
          .toList();

    } catch (TopicNotFoundException e) {

      throw e;

    } catch (Exception e) {

      throw new SubTopicException("GET_SUB_TOPICS_PAGED", e.getMessage());
    }
  }

  @Override
  public List<SubTopicDTO> getAllSubTopics(int topicId)
      throws SubTopicException, TopicNotFoundException {
    try {

      topicService.getTopicEntityById(topicId);

      return subTopicRepository
          .findAllByTopicTopicId(topicId, Sort.by(Sort.Order.asc("id")))
          .stream()
          .map(
              subTopic ->
                  new SubTopicDTO(
                      subTopic.getTopic().getTopicId(),
                      subTopic.getId(),
                      subTopic.getTitle(),
                      subTopic.getDescription()))
          .toList();

    } catch (TopicNotFoundException e) {

      throw e;

    } catch (Exception e) {

      throw new SubTopicException("GET_ALL_PAGED", e.getMessage());
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

  @Override
  public void updateSubTopic(SubTopicDTO subTopicDTO) throws SubTopicException {
    try {

      SubTopic subTopic =
          getSubTopicEntityById(subTopicDTO.getTopicId(), subTopicDTO.getSubTopicId());

      subTopic.setTitle(subTopicDTO.getSubTopicTitle());

      subTopic.setDescription(subTopicDTO.getSubTopicDescription());

      subTopicRepository.save(subTopic);

    } catch (SubTopicNotFoundException e) {

      throw e;

    } catch (Exception e) {

      logger.error(String.format("UPDATE_SUBTOPIC %s", subTopicDTO.toString()), e);

      throw new SubTopicException(
          String.format("UPDATE_SUBTOPIC %s", subTopicDTO.toString()), e.getMessage());
    }
  }

  @Override
  public SubTopicDTO deleteSubTopic(int topicId, int subTopicId) throws SubTopicException {

    try {

      SubTopic subTopic = getSubTopicEntityById(topicId, subTopicId);

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
}
