package helios.server.geochat.service;

import java.util.List;

import org.springframework.data.domain.Page;

import helios.server.geochat.model.SubTopic;

import helios.server.geochat.dto.request.SubTopicDTO;

import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicException;

public interface SubTopicService {
    Page<SubTopic> getSubTopics(int pageNumber) throws SubTopicException;

    List<SubTopic> getSubAllTopics() throws SubTopicException;

    SubTopic deleteSubTopic(int topicId) throws SubTopicException;

    SubTopic addSubTopic(SubTopicDTO topicDTO) throws SubTopicException;
}
