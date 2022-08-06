package helios.server.geochat;

import helios.server.geochat.dto.request.SubTopicDTO;
import helios.server.geochat.dto.request.SubTopicMetaDiscussionDTO;
import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.service.GeoPointService;
import helios.server.geochat.service.SubTopicMetaDiscussionService;
import helios.server.geochat.service.SubTopicService;
import helios.server.geochat.service.TopicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SubTopicMetaDiscussionServiceTest {
  private final GeoPointService geoPointService;

  private final TopicService topicService;

  private final SubTopicService subTopicService;

  private final SubTopicMetaDiscussionService subTopicMetaDiscussionService;

  @Autowired
  public SubTopicMetaDiscussionServiceTest(
      SubTopicMetaDiscussionService subTopicMetaDiscussionService,
      SubTopicService subTopicService,
      TopicService topicService,
      GeoPointService geoPointService) {

    this.geoPointService = geoPointService;

    this.topicService = topicService;

    this.subTopicService = subTopicService;

    this.subTopicMetaDiscussionService = subTopicMetaDiscussionService;
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

  SubTopicMetaDiscussionDTO addSubTopicMeta(int topicId, int subTopicId)
      throws SubTopicNotFoundException, TopicException, GeoUserNotFoundException,
          SubTopicMetaDiscussionException {

    SubTopicMetaDiscussionDTO newSubTopicMeta = new SubTopicMetaDiscussionDTO();

    newSubTopicMeta.setTopicId(topicId);
    newSubTopicMeta.setSubTopicId(subTopicId);
    newSubTopicMeta.setSenderUsername("tester");
    newSubTopicMeta.setMessage(
        String.format(
            "test message with test user username:test timestamp: %d", System.currentTimeMillis()));

    final int metaId = subTopicMetaDiscussionService.addMessage(newSubTopicMeta);

    newSubTopicMeta.setMessageId(metaId);

    return newSubTopicMeta;
  }

  @Test
  public void testAddSubTopicMeta() throws TopicException, GeoPointException, SubTopicException {

    TopicDTO newTopic = addTopic();

    SubTopicDTO newSubTopic = addSubTopic(newTopic.getId());

    Assertions.assertDoesNotThrow(
        () -> {
          addSubTopicMeta(newTopic.getId(), newSubTopic.getSubTopicId());
        });
  }

  @Test
  public void testDeleteSubTopicMeta()
      throws TopicException, GeoPointException, SubTopicException, GeoUserNotFoundException,
          SubTopicMetaDiscussionException {

    TopicDTO newTopic = addTopic();

    SubTopicDTO newSubTopic = addSubTopic(newTopic.getId());

    SubTopicMetaDiscussionDTO newSubTopicMetaDiscussion =
        addSubTopicMeta(newTopic.getId(), newSubTopic.getSubTopicId());

    Assertions.assertDoesNotThrow(
        () -> {
          subTopicMetaDiscussionService.deleteMessage(
              newTopic.getId(),
              newSubTopic.getSubTopicId(),
              newSubTopicMetaDiscussion.getMessageId());
        });
  }
}
