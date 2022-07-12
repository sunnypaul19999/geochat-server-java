package helios.server.geochat.service.impl;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import helios.server.geochat.dto.request.SubTopicDTO;
import helios.server.geochat.service.SubTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import helios.server.geochat.service.SubTopicMetaDiscussionService;

import helios.server.geochat.repository.SubTopicMetaDiscussionRepository;
import helios.server.geochat.dto.request.GeoUserDTO;
import helios.server.geochat.dto.request.SubTopicMetaDiscussionDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;
import helios.server.geochat.model.GeoUser;
import helios.server.geochat.model.SubTopic;
import helios.server.geochat.model.SubTopicMetaDiscussion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SubTopicMetaDiscussionServiceImpl implements SubTopicMetaDiscussionService {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired SubTopicService subTopicService;

  @Autowired GeoSecurityUserServiceImpl geoUserServiceImpl;

  @Autowired SubTopicMetaDiscussionRepository subTopicMetaDiscussionRepository;
  private SubTopicNotFoundException e;

  @Override
  public SubTopicMetaDiscussion getMessageById(int messageId)
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
  public List<SubTopicMetaDiscussionDTO> getAllMessages(int subTopicId)
      throws SubTopicNotFoundException {

    subTopicService.getSubTopicById(subTopicId);

    List<SubTopicMetaDiscussion> subTopicMeta =
        subTopicMetaDiscussionRepository.findBySubTopicId(subTopicId);

    return subTopicMeta.stream()
        .map(
            meta ->
                new SubTopicMetaDiscussionDTO(
                    meta.getGeoUser().getUsername(), subTopicId, meta.getMessage()))
        .toList();
  }

  @Override
  public List<SubTopicMetaDiscussionDTO> getPagedMessages(int subTopicId)
      throws SubTopicNotFoundException {

    /*SubTopicDTO subTopicDTO = subTopicService.getSubTopicById(subTopicId);

    Page<SubTopicMetaDiscussion> subTopicMetaDiscussionPaged =
        subTopicMetaDiscussionRepository.findBySubTopicId(
            subTopicDTO.getSubTopicId(), (Pageable) PageRequest.of(0, 5));

    return subTopicMetaDiscussionPaged
        .map(
            meta ->
                new SubTopicMetaDiscussionDTO(
                    meta.getGeoUser().getUsername(), subTopicId, meta.getMessage()))
        .stream()
        .toList();*/
    return null;
  }

  @Override
  public int addMessage(SubTopicMetaDiscussionDTO subTopicMetaDiscussDTO)
      throws GeoUserNotFoundException, SubTopicNotFoundException, SubTopicMetaDiscussionException {
    try {
      GeoUserDTO geoUserReceiverDTO = new GeoUserDTO(subTopicMetaDiscussDTO.getSenderUsername());

      GeoUser geoUser = geoUserServiceImpl.getUser(geoUserReceiverDTO);

      SubTopic subTopic =
          subTopicService.getSubTopicEntityById(subTopicMetaDiscussDTO.getSubTopicId());

      SubTopicMetaDiscussion subTopicMetaDiscussion =
          new SubTopicMetaDiscussion(subTopicMetaDiscussDTO.getMessage(), subTopic, geoUser);

      subTopicMetaDiscussion = subTopicMetaDiscussionRepository.save(subTopicMetaDiscussion);

      return subTopicMetaDiscussion.getId();

    } catch (GeoUserNotFoundException | SubTopicNotFoundException e) {

      logger.error(e.getMessage(), e);

      throw e;

    } catch (Exception e) {

      logger.error(String.format("ADD_MESSAGE %s", e.getMessage()), e);

      throw new SubTopicMetaDiscussionException("ADD_MESSAGE", e.getMessage());
    }
  }

  @Override
  public void deleteMessage(int messageId) throws SubTopicMetaDiscussionException {
    try {
      SubTopicMetaDiscussion subTopicMetaDiscussion = getMessageById(messageId);

      subTopicMetaDiscussionRepository.delete(subTopicMetaDiscussion);

    } catch (SubTopicMetaDiscussionNotFoundException e) {

      throw e;
    } catch (Exception e) {

      throw new SubTopicMetaDiscussionException("DELETE_MESSAGE", e.getMessage());
    }
  }
}
