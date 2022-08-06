package helios.server.geochat;

import helios.server.geochat.dto.request.SubTopicDTO;
import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.dto.request.UserLocationDTO;
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

  @Test
  public void testAddSubTopic() {

    final int[] subTopicId = new int[1];
    final int[] topicId = new int[1];

    Assertions.assertDoesNotThrow(
        () -> {
          String plusCode =
              geoPointService.registerGeoPoint(
                  new UserLocationDTO(23.677303229900822, 86.94993375397198));

          TopicDTO topicDTO = new TopicDTO(plusCode);

          topicDTO.setTopicTitle("Test for adding sub-topic");

          topicDTO.setPlusCode(plusCode);

          topicId[0] = topicService.addTopic(topicDTO).getId();
        },
        "Failed to create topic");

    SubTopicDTO subTopicDTO = new SubTopicDTO();

    subTopicDTO.setTopicId(topicId[0]);

    subTopicDTO.setSubTopicTitle(
        String.format(
            "test sub-topic title topic-id: %d timestamp: %d",
            topicId[0], System.currentTimeMillis()));

    subTopicDTO.setSubTopicDescription("This test description");

    Assertions.assertDoesNotThrow(
        () -> {
          subTopicId[0] = subTopicService.addSubTopic(subTopicDTO).getSubTopicId();
        },
        "Failed to create SubTopic");

    //    return subTopicId[0];
  }
}
