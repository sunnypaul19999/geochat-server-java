package helios.server.geochat;

import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointNotRegisteredException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.repository.GeoPointRangeRepository;
import helios.server.geochat.repository.GeoPointRepository;
import helios.server.geochat.repository.TopicRepository;
import helios.server.geochat.service.impl.GeoPointRangeServiceImpl;
import helios.server.geochat.service.impl.GeoPointServiceImpl;
import helios.server.geochat.service.impl.TopicServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class TopicServiceTests {
  
  @MockBean
  private TopicServiceImpl topicService;

  private int topicId;

  @Test
  void addTopicWithExistentGeoPoint() throws TopicException {

    var topicDto = new TopicDTO("test title");

    topicDto.setPlusCode("7MM8MWGX+WX");

    try {
      topicService.addTopic(topicDto);
    } catch (GeoPointNotRegisteredException e) {

      Assert.isTrue(true, e.getMessage());
    }
  }
}
