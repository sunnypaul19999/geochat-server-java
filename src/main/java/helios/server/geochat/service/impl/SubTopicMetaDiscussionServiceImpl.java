package helios.server.geochat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import helios.server.geochat.service.SubTopicMetaDiscussionService;

import helios.server.geochat.model.SubTopic;

import helios.server.geochat.repository.SubTopicMetaDiscussionRepository;

import helios.server.geochat.exceptions.serviceExceptions.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;

public class SubTopicMetaDiscussionServiceImpl implements SubTopicMetaDiscussionService {

    @Autowired
    SubTopicServiceImpl subTopicServiceImpl;

    @Autowired
    SubTopicMetaDiscussionRepository subTopicMetaDiscussionRepository;

    @Override
    public SubTopicMetaDiscussionService getMessageById(int messageId) throws SubTopicMetaDiscussionNotFoundException {

        return null;
    }

    @Override
    public void addMessage(int subTopicId, int messageId)
            throws SubTopicNotFoundException, SubTopicMetaDiscussionException {
        try {
            SubTopic subTopic = subTopicServiceImpl.getSubTopicById(subTopicId);

        } catch (SubTopicNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SubTopicMetaDiscussionException("Add_MESSAGE", e.getMessage());
        }
    }

    @Override
    public void deleteMessage(int subTopicId, int messageId) {

        return;
    }
}
