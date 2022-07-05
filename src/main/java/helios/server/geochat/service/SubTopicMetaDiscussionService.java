package helios.server.geochat.service;

import helios.server.geochat.dto.request.SubTopicMetaDiscussDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;
import helios.server.geochat.model.SubTopicMetaDiscussion;

public interface SubTopicMetaDiscussionService {

    SubTopicMetaDiscussion getMessageById(int messageId)
            throws SubTopicMetaDiscussionNotFoundException, SubTopicMetaDiscussionException;

    void addMessage(SubTopicMetaDiscussDTO subTopicMetaDiscussDTO)
            throws SubTopicNotFoundException, SubTopicMetaDiscussionException, GeoUserNotFoundException;

    void deleteMessage(int messageId) throws SubTopicMetaDiscussionException;
}
