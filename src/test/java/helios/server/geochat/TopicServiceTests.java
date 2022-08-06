package helios.server.geochat;

import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicNotFoundException;
import helios.server.geochat.service.GeoPointService;
import helios.server.geochat.service.TopicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TopicServiceTests {

  private final GeoPointService geoPointService;

  public TopicService topicService;

  @Autowired
  public TopicServiceTests(TopicService topicService, GeoPointService geoPointService) {

    this.topicService = topicService;
    this.geoPointService = geoPointService;
  }

  String registerPlusCode() throws GeoPointException {

    return geoPointService.registerGeoPoint(
        new UserLocationDTO(23.677303229900822, 86.94993375397198));
  }

  TopicDTO addTopic() throws GeoPointException, TopicException {
    final String plusCode = registerPlusCode();

    TopicDTO topicDTO = new TopicDTO(plusCode);

    topicDTO.setTopicTitle(
        String.format("Test topic for adding sub-topic timestamp: %d", System.currentTimeMillis()));

    topicDTO.setPlusCode(plusCode);

    return topicService.addTopic(topicDTO);
  }

  @Test
  void testAddTopic() {

    Assertions.assertDoesNotThrow(
        () -> {
          addTopic();
        });
  }

  @Test
  void testUpdateTopic() throws TopicException, GeoPointException {
    final TopicDTO newTopic = addTopic();

    newTopic.setTopicTitle(
        String.format("updated topic title timestamp: %d", System.currentTimeMillis()));

    Assertions.assertDoesNotThrow(
        () -> {
          topicService.updateTopic(newTopic);
        });

    Assertions.assertTrue(
        newTopic
            .getTopicTitle()
            .equals(topicService.getTopicById(newTopic.getId()).getTopicTitle()),
        "Update topic title does not match");
  }

  @Test
  void testDeleteTopic() throws TopicException, GeoPointException {
    final TopicDTO newTopic = addTopic();

    Assertions.assertThrows(
        TopicNotFoundException.class,
        () -> {
          topicService.deleteTopic(newTopic.getId());

          topicService.getTopicById(newTopic.getId());
        });
  }
}
