package helios.server.geochat.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import helios.server.geochat.service.SubTopicMetaDiscussionService;

import helios.server.geochat.repository.SubTopicMetaDiscussionRepository;
import helios.server.geochat.dto.request.GeoUserDTO;
import helios.server.geochat.dto.request.SubTopicMetaDiscussDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;
import helios.server.geochat.model.GeoUser;
import helios.server.geochat.model.SubTopic;
import helios.server.geochat.model.SubTopicMetaDiscussion;

public class SubTopicMetaDiscussionServiceImpl implements SubTopicMetaDiscussionService {

    @Autowired
    SubTopicServiceImpl subTopicServiceImpl;

    @Autowired
    GeoUserServiceImpl geoUserServiceImpl;

    @Autowired
    SubTopicMetaDiscussionRepository subTopicMetaDiscussionRepository;

    @Override
    public SubTopicMetaDiscussion getMessageById(int messageId) throws SubTopicMetaDiscussionException {
        try {
            Optional<SubTopicMetaDiscussion> sOptional = subTopicMetaDiscussionRepository.findById(messageId);

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
    public void addMessage(SubTopicMetaDiscussDTO subTopicMetaDiscussDTO)
            throws GeoUserNotFoundException, SubTopicNotFoundException, SubTopicMetaDiscussionException {
        try {
            GeoUserDTO geoUserReceiverDTO = new GeoUserDTO(subTopicMetaDiscussDTO.getReceiverUsername());

            GeoUser geoUser = geoUserServiceImpl.getUser(geoUserReceiverDTO);
            SubTopic subTopic = subTopicServiceImpl.getSubTopicById(subTopicMetaDiscussDTO.getSubTopicId());

            SubTopicMetaDiscussion subTopicMetaDiscussion = new SubTopicMetaDiscussion(
                    subTopicMetaDiscussDTO.getMessage(),
                    subTopic,
                    geoUser);

            subTopicMetaDiscussionRepository.save(subTopicMetaDiscussion);
        } catch (GeoUserNotFoundException | SubTopicNotFoundException e) {
            throw e;
        } catch (Exception e) {
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
