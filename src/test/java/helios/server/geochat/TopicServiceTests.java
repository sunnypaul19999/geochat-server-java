package helios.server.geochat;

import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointNotRegisteredException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicNotFoundException;
import helios.server.geochat.service.GeoPointService;
import helios.server.geochat.service.TopicService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TopicServiceTests {

  public TopicService topicService;

  private GeoPointService geoPointService;

  @Autowired
  public TopicServiceTests(TopicService topicService, GeoPointService geoPointService) {

    this.topicService = topicService;
    this.geoPointService = geoPointService;
  }

  @Test
  @Order(1)
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

  @Test
  @Order(2)
  int addTopicWithExistentGeoPoint() throws TopicException, GeoPointNotRegisteredException {
    var topicDto = new TopicDTO("test title");

    topicDto.setPlusCode("7MM8MWGX+WX");

    return topicService.addTopic(topicDto).getId();
  }

  @Test
  @Order(3)
  void updateTopicWithExistentTopicId() throws TopicException, GeoPointNotRegisteredException {

    final String updateTopicTitle =
        String.format("updated topic title %d", System.currentTimeMillis());

    final int topicId = addTopicWithExistentGeoPoint();

    TopicDTO updateTopicDTO = new TopicDTO(topicId, updateTopicTitle);

    updateTopicDTO.setId(topicId);

    topicService.updateTopic(updateTopicDTO);

    final String updatedTitle = topicService.getTopicById(topicId).getTopicTitle();
    
    System.out.println(updatedTitle.equals(updateTopicTitle));

    Assertions.assertTrue(
        updatedTitle.equals(updateTopicTitle),
        String.format("%s %s", updatedTitle, updateTopicTitle));
  }

  @Test
  @Order(4)
  void deleteTopicWithExistentTopicId() throws TopicException, GeoPointNotRegisteredException {

    final int topicId = addTopicWithExistentGeoPoint();

    topicService.deleteTopic(topicId);
  }

  @Test
  @Order(5)
  void deleteTopicWithNonExistentTopicId() {

    Assertions.assertThrows(
        TopicNotFoundException.class,
        () -> {
          topicService.deleteTopic(0);
        },
        String.format("Topic with topic id %d does not exists could not deleted", 0));
  }
}
