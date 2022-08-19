package helios.server.geochat.service.impl;

import helios.server.geochat.dto.request.GeoUserDTO;
import helios.server.geochat.dto.request.SubTopicDTO;
import helios.server.geochat.dto.request.SubTopicMetaDiscussionDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionPageNumberNotInRangeException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicNotFoundException;
import helios.server.geochat.model.GeoUser;
import helios.server.geochat.model.SubTopic;
import helios.server.geochat.model.SubTopicMetaDiscussion;
import helios.server.geochat.model.Topic;
import helios.server.geochat.repository.SubTopicMetaDiscussionRepository;
import helios.server.geochat.service.SubTopicMetaDiscussionService;
import helios.server.geochat.service.SubTopicService;
import helios.server.geochat.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubTopicMetaDiscussionServiceImpl implements SubTopicMetaDiscussionService {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired TopicService topicService;

  @Autowired SubTopicService subTopicService;

  @Autowired GeoSecurityUserServiceImpl geoUserServiceImpl;

  @Autowired SubTopicMetaDiscussionRepository subTopicMetaDiscussionRepository;

  @Override
  public SubTopicMetaDiscussion getMessageById(int topicId, int subtopicId, int messageId)
      throws SubTopicMetaDiscussionException {
    try {
      Optional<SubTopicMetaDiscussion> sOptional =
          subTopicMetaDiscussionRepository.findById(messageId);

      if (sOptional.isPresent()) {
        return sOptional.get();
      }

      throw new SubTopicMetaDiscussionNotFoundException(messageId, "GET_MESSAGE");

    } catch (SubTopicMetaDiscussionNotFoundException e) {
      throw e;
    } catch (Exception e) {
      throw new SubTopicMetaDiscussionException("GET_MESSAGE", e.getMessage());
    }
  }

  @Override
  public List<SubTopicMetaDiscussionDTO> getAllMessages(int topicId, int subTopicId)
      throws SubTopicNotFoundException {

    subTopicService.getSubTopic(topicId, subTopicId);

    List<SubTopicMetaDiscussion> subTopicMeta =
        subTopicMetaDiscussionRepository.findByTopicTopicIdAndSubTopicId(
            topicId, subTopicId, Sort.by(Sort.Order.asc("id")));

    return subTopicMeta.stream()
        .map(
            meta ->
                new SubTopicMetaDiscussionDTO(
                    topicId,
                    subTopicId,
                    meta.getId(),
                    meta.getGeoUser().getUsername(),
                    meta.getMessage()))
        .toList();
  }

  @Override
  public List<SubTopicMetaDiscussionDTO> getPagedMessages(
      int topicId, int subTopicId, int pageNumber)
      throws SubTopicNotFoundException, SubTopicMetaDiscussionPageNumberNotInRangeException {

    if (pageNumber <= 0) {
      throw new SubTopicMetaDiscussionPageNumberNotInRangeException();
    }

    SubTopicDTO subTopicDTO = subTopicService.getSubTopic(topicId, subTopicId);

    Page<SubTopicMetaDiscussion> subTopicMetaDiscussionPaged =
        subTopicMetaDiscussionRepository.findByTopicTopicIdAndSubTopicId(
            topicId,
            subTopicDTO.getSubTopicId(),
            PageRequest.of(pageNumber - 1, 5, Sort.by(Sort.Order.desc("id"))));

    return subTopicMetaDiscussionPaged
        .map(
            meta ->
                new SubTopicMetaDiscussionDTO(
                    topicId,
                    subTopicId,
                    meta.getId(),
                    meta.getGeoUser().getUsername(),
                    meta.getMessage()))
        .stream()
        .toList();
  }

  @Override
  public int addMessage(SubTopicMetaDiscussionDTO subTopicMetaDiscussDTO)
      throws GeoUserNotFoundException, SubTopicNotFoundException, SubTopicMetaDiscussionException,
          TopicException {
    try {
      GeoUserDTO geoUserReceiverDTO = new GeoUserDTO(subTopicMetaDiscussDTO.getSenderUsername());

      GeoUser geoUser = geoUserServiceImpl.getUser(geoUserReceiverDTO);

      Topic topic = topicService.getTopicEntityById(subTopicMetaDiscussDTO.getTopicId());

      SubTopic subTopic =
          subTopicService.getSubTopicEntityById(
              subTopicMetaDiscussDTO.getTopicId(), subTopicMetaDiscussDTO.getSubTopicId());

      SubTopicMetaDiscussion subTopicMetaDiscussion =
          new SubTopicMetaDiscussion(subTopicMetaDiscussDTO.getMessage(), subTopic, geoUser);

      subTopicMetaDiscussion.setGeoUser(geoUser);

      subTopicMetaDiscussion.setTopic(topic);

      subTopicMetaDiscussion.setSubTopic(subTopic);

      subTopicMetaDiscussion = subTopicMetaDiscussionRepository.save(subTopicMetaDiscussion);

      return subTopicMetaDiscussion.getId();

    } catch (GeoUserNotFoundException | SubTopicNotFoundException | TopicNotFoundException e) {

      logger.error(e.getMessage(), e);

      throw e;

    } catch (Exception e) {

      logger.error(String.format("ADD_MESSAGE %s", e.getMessage()), e);

      throw new SubTopicMetaDiscussionException("ADD_MESSAGE", e.getMessage());
    }
  }

  @Override
  public void deleteMessage(int topicId, int subtopicId, int messageId)
      throws SubTopicMetaDiscussionException {
    try {
      SubTopicMetaDiscussion subTopicMetaDiscussion =
          getMessageById(topicId, subtopicId, messageId);

      subTopicMetaDiscussionRepository.delete(subTopicMetaDiscussion);

    } catch (SubTopicMetaDiscussionNotFoundException e) {

      throw e;
    } catch (Exception e) {

      throw new SubTopicMetaDiscussionException("DELETE_MESSAGE", e.getMessage());
    }
  }
}
