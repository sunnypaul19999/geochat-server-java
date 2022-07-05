package helios.server.geochat.service;

import helios.server.geochat.exceptions.serviceExceptions.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;

public interface SubTopicMetaDiscussionService {

    SubTopicMetaDiscussionService getMessageById(int messageId) throws SubTopicMetaDiscussionNotFoundException;

    void addMessage(int subTopicId, int messageId) throws SubTopicNotFoundException, SubTopicMetaDiscussionException;

    void deleteMessage(int subTopicId, int messageId);
}
