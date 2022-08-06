package helios.server.geochat;

import helios.server.geochat.dto.request.SubTopicDTO;
import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.service.GeoPointService;
import helios.server.geochat.service.SubTopicService;
import helios.server.geochat.service.TopicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SubTopicServiceTest {

  private final SubTopicService subTopicService;

  private final TopicService topicService;

  private final GeoPointService geoPointService;

  @Autowired
  public SubTopicServiceTest(
      SubTopicService subTopicService, TopicService topicService, GeoPointService geoPointService) {

    this.subTopicService = subTopicService;

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

  SubTopicDTO addSubTopic(int topicId) throws SubTopicException, TopicException {

    SubTopicDTO subTopicDTO = new SubTopicDTO();

    subTopicDTO.setTopicId(topicId);

    subTopicDTO.setSubTopicTitle(
        String.format(
            "test sub-topic title topic-id: %d timestamp: %d",
            topicId, System.currentTimeMillis()));

    subTopicDTO.setSubTopicDescription("This test description");

    return subTopicService.addSubTopic(subTopicDTO);
  }

  @Test
  public void testAddSubTopic() throws TopicException, GeoPointException {

    TopicDTO newTopic = addTopic();

    Assertions.assertDoesNotThrow(
        () -> {
          final SubTopicDTO newSubTopic = addSubTopic(newTopic.getId());

          subTopicService.getSubTopic(newTopic.getId(), newSubTopic.getSubTopicId());
        },
        "Failed to create SubTopic");
  }

  @Test
  public void testUpdateSubTopic() throws SubTopicException, TopicException, GeoPointException {

    final TopicDTO newTopic = addTopic();

    final SubTopicDTO newSubTopic = addSubTopic(newTopic.getId());

    SubTopicDTO updateSubTopic =
        subTopicService.getSubTopic(newTopic.getId(), newSubTopic.getSubTopicId());

    updateSubTopic.setSubTopicTitle(
        String.format("updated sub-topic-tile %d", System.currentTimeMillis()));

    Assertions.assertDoesNotThrow(
        () -> {
          subTopicService.updateSubTopic(updateSubTopic);
        },
        "Failed to update sub-topic");

    Assertions.assertTrue(
        subTopicService
            .getSubTopic(newTopic.getId(), newSubTopic.getSubTopicId())
            .getSubTopicTitle()
            .equals(updateSubTopic.getSubTopicTitle()));
  }

  @Test
  public void testDeleteSubTopic() throws TopicException, GeoPointException, SubTopicException {

    final TopicDTO newTopic = addTopic();

    final SubTopicDTO newSubTopic = addSubTopic(newTopic.getId());

    Assertions.assertDoesNotThrow(
        () -> {
          subTopicService.deleteSubTopic(newTopic.getId(), newSubTopic.getSubTopicId());
        });

    Assertions.assertThrows(
        SubTopicNotFoundException.class,
        () -> {
          subTopicService.getSubTopic(newTopic.getId(), newSubTopic.getSubTopicId());
        });
  }
}
