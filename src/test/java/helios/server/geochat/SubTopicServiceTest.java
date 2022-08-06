package helios.server.geochat;

import helios.server.geochat.dto.request.SubTopicDTO;
import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.service.GeoPointService;
import helios.server.geochat.service.SubTopicService;
import helios.server.geochat.service.TopicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SubTopicServiceTest {

  private SubTopicService subTopicService;

  private TopicService topicService;
  
  private  GeoPointService geoPointService;

  @Autowired
  public SubTopicServiceTest(SubTopicService subTopicService, TopicService topicService, GeoPointService geoPointService) {

    this.subTopicService = subTopicService;

    this.topicService = topicService;
    
    this.geoPointService = geoPointService;
  }

  @Test
  public int addSubTopic() {

        final int[] subTopicId = new int[1];
        final int[] topicId = new int[1];

        Assertions.assertDoesNotThrow(
            () -> {
              
              TopicService.
              
              TopicDTO topicDTO = new TopicDTO();
              topicDTO.setTopicTitle("Test for adding sub-topic");
              topicDTO.setPlusCode(GeoPointService.);
              topicId[0] = topicService.addTopic(new TopicDTO());
            });

        SubTopicDTO subTopicDTO = new SubTopicDTO();

        subTopicDTO.setTopicId(topicId[0]);

        subTopicDTO.setSubTopicTitle(
            String.format(
                "test sub-topic title topic-id: %d timestamp: %d",
                topicId, System.currentTimeMillis()));

        subTopicDTO.setSubTopicDescription("This test description");

        Assertions.assertDoesNotThrow(
            () -> {
              subTopicId[0] = subTopicService.addSubTopic(subTopicDTO).getSubTopicId();
            });

        return subTopicId[0];

    return 0;
  }
}
