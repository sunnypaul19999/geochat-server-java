package helios.server.geochat;

import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointException;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointNotRegisteredException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.service.GeoPointService;
import helios.server.geochat.service.TopicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TopicServiceTests {
  @Autowired public TopicService topicService;

  @Autowired public GeoPointService geoPointService;

  @BeforeEach
  void assertIfTopicServiceIsNull() {}

  @Test
  void contextLoads() {

    // Assert.notNull(topicService, "TopicService is null cannot proceed");
  }
//
//  @Test
//  void addGeoPoint() throws GeoPointException {
//    geoPointService.registerGeoPoint(new UserLocationDTO(23.677303229900822, 86.94993375397198));
//  }

  @Test
  void addTopicWithExistentGeoPoint() throws TopicException, GeoPointNotRegisteredException {
    var topicDto = new TopicDTO("test title");

    topicDto.setPlusCode("7MM8MWGX+WX");

    topicService.addTopic(topicDto);
  }

  @Test
  void addTopicWithNonExistentGeoPoint() {

    var topicDto = new TopicDTO("test title");

    // setting non existent plus code
    topicDto.setPlusCode("9NM8MWGX+WX");

    Assertions.assertThrows(
        GeoPointNotRegisteredException.class,
        () -> {
          topicService.addTopic(topicDto);
        },
        "Expected to throw GeoPointNotRegisteredException for plus-code 9NM8MWGX+WX");
  }
}
