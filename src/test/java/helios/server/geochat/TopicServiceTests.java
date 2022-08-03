package helios.server.geochat;

import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointNotRegisteredException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.model.Topic;
import helios.server.geochat.repository.TopicRepository;
import helios.server.geochat.service.TopicService;
import helios.server.geochat.service.impl.TopicServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class TopicServiceTests {
    
    @Mock
    private TopicRepository topicRepository;
    
    @InjectMocks
    private TopicServiceImpl topicService;

    private int topicId;

    @BeforeEach
    void init(){

        topicService = new TopicServiceImpl();
    }

    @Test
    @Order(1)
    void addTopicWithVerifiedGeoPoint() throws TopicException, GeoPointNotRegisteredException {

        var topicDto = new TopicDTO("test title");

        topicDto.setPlusCode("7MM8MWGX+WX");

        topicService.addTopic(topicDto);

    }

    @Test
    void addTopicWithNonExistentGeoPoint() throws TopicException {

        var topicDto = new TopicDTO("test title");

        topicDto.setPlusCode("7MdddM8MWGX+WX");

        try{
            topicService.addTopic(topicDto);
        }catch (GeoPointNotRegisteredException e){

            Assert.isTrue(true, e.getMessage());
        }

    }

    @Test
    @Order(2)
    void removeTopic(){


    }
}
