package helios.server.geochat.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    SubTopicRepository subTopicRepository;

    @Override
    public SubTopic getSubTopicById(int subTopicId) throws SubTopicNotFoundException {
        Optional<SubTopic> sOptional = subTopicRepository.findById(subTopicId);
        if (sOptional.isPresent()) {
            return sOptional.get();
        }

        throw new SubTopicNotFoundException(subTopicId, "GET_SUBTOPIC_BY_ID");
    }

    @Override
    public Page<SubTopic> getSubTopics(int pageNumber) throws SubTopicException {
        try {
            PageRequest pageable = PageRequest.of(pageNumber, 5);
            return subTopicRepository.findAll(pageable);
        } catch (Exception e) {
            throw new SubTopicException("GETTOPICSPAGED", e.getMessage());
        }
    }

    @Override
    public List<SubTopic> getSubAllTopics() throws SubTopicException {
        try {
            return subTopicRepository.findAll();
        } catch (Exception e) {
            throw new SubTopicException("GETALLTOPICS", e.getMessage());
        }
    }

    @Override
    public SubTopic deleteSubTopic(int subTopicId) throws SubTopicException {

        try {
            SubTopic subTopic = getSubTopicById(subTopicId);

            subTopicRepository.delete(subTopic);

            // returns the topic entity object after persisting the deleted entity
            return subTopic;

        } catch (SubTopicNotFoundException e) {
            // not rethrowing to change the operation type
            throw new SubTopicNotFoundException(subTopicId, "DELETE");
        } catch (Exception e) {
            throw new SubTopicException("DELETE", e.getMessage());
        }
    }

    @Override
    public SubTopic addSubTopic(SubTopicDTO subTopicDTO) throws SubTopicException {
        SubTopic subTopic = null;
        try {
            subTopic = new SubTopic(subTopicDTO);
            subTopic = subTopicRepository.save(subTopic);
            return subTopic;
        } catch (Exception e) {
            throw new SubTopicException("ADD", e.getMessage());
        }
    }
}
