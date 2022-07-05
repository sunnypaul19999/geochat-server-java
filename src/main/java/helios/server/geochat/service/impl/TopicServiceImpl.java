package helios.server.geochat.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import helios.server.geochat.model.Topic;

import helios.server.geochat.repository.TopicRepository;

import helios.server.geochat.service.TopicService;

import helios.server.geochat.dto.request.TopicDTO;

import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicNotFoundException;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Override
    public Page<Topic> getTopics(int pageNumber) throws TopicException {
        try {
            PageRequest pageable = PageRequest.of(pageNumber, 5);
            return topicRepository.findAll(pageable);
        } catch (Exception e) {
            throw new TopicException("GETTOPICSPAGED", e.getMessage());
        }
    }

    @Override
    public List<Topic> getAllTopics() throws TopicException {
        try {
            return topicRepository.findAll();
        } catch (Exception e) {
            throw new TopicException("GETALLTOPICS", e.getMessage());
        }
    }

    @Override
    public Topic deleteTopic(int topicId) throws TopicException {
        Optional<Topic> topic;
        try {
            topic = topicRepository.findById(topicId);
            if (topic.isPresent()) {
                topicRepository.delete(topic.get());

                // returns the topic entity object after persisting the deleted entity
                return topic.get();
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
    public Topic addTopic(TopicDTO topicDTO) throws TopicException {
        Topic topic = null;
        try {
            topic = new Topic(topicDTO);
            topic = topicRepository.save(topic);
            return topic;
        } catch (Exception e) {
            throw new TopicException("ADD", e.getMessage());
        }
    }
}
