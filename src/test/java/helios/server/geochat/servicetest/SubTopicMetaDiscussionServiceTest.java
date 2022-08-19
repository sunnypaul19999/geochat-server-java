package helios.server.geochat.servicetest;

import helios.server.geochat.dto.request.*;
import helios.server.geochat.model.*;
import helios.server.geochat.repository.SubTopicMetaDiscussionRepository;
import helios.server.geochat.service.GeoPointService;
import helios.server.geochat.service.impl.GeoSecurityUserServiceImpl;
import helios.server.geochat.service.impl.SubTopicMetaDiscussionServiceImpl;
import helios.server.geochat.service.impl.SubTopicServiceImpl;
import helios.server.geochat.service.impl.TopicServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SubTopicMetaDiscussionServiceTest {

  @Mock private TopicServiceImpl topicService;

  @Mock private SubTopicServiceImpl subTopicService;

  @Mock private GeoSecurityUserServiceImpl geoSecurityUserService;

  @Mock private SubTopicMetaDiscussionRepository subTopicMetaDiscussionRepository;

  @InjectMocks private SubTopicMetaDiscussionServiceImpl subTopicMetaDiscussionService;

  private GeoUserDTO geoUserDTO;

  private GeoUser geoUserEntity;

  private UserLocationDTO userLocationDTO;

  private TopicDTO topicDTO;

  private SubTopicDTO subTopicDTO;

  private GeoPoint geoPointEntity;

  private Topic topicEntity;

  private SubTopic subTopicEntity;

  private SubTopicMetaDiscussionDTO subTopicMetaDiscussionDTO;

  private SubTopicMetaDiscussion subTopicMetaDiscussionEntity;

  @BeforeEach
  public void init() {
    geoUserDTO = new GeoUserDTO("mock-user");
    geoUserEntity = new GeoUser();
    geoUserEntity.setUsername(geoUserDTO.getUsername());

    userLocationDTO = new UserLocationDTO(23.677303229900822, 86.94993375397198);
    geoPointEntity = new GeoPoint(GeoPointService.calcPlusCode(userLocationDTO), userLocationDTO);

    topicDTO = new TopicDTO(geoPointEntity.getPlusCode(), 1, "mock topic title");
    topicEntity = new Topic(topicDTO);
    topicEntity.setTopicId(topicDTO.getId());
    topicEntity.setGeoPoint(geoPointEntity);

    subTopicDTO =
        new SubTopicDTO(topicDTO.getId(), 1, "mock sub-topic title", "mock sub-topic description");
    subTopicDTO.setGeoPointPlusCode(geoPointEntity.getPlusCode());
    subTopicEntity = new SubTopic(subTopicDTO);
    subTopicEntity.setId(subTopicDTO.getSubTopicId());
    subTopicEntity.setTopic(topicEntity);

    subTopicMetaDiscussionDTO =
        new SubTopicMetaDiscussionDTO()
            .setGeoPointPlusCode(geoPointEntity.getPlusCode())
            .setTopicId(subTopicDTO.getTopicId())
            .setSubTopicId(subTopicDTO.getSubTopicId())
            .setMessageId(1)
            .setMessage("mock message")
            .setSenderUsername(geoUserDTO.getUsername());
    subTopicMetaDiscussionEntity =
        new SubTopicMetaDiscussion(
            subTopicMetaDiscussionDTO.getMessage(), subTopicEntity, geoUserEntity);
  }

  @Test
  public void addMessage() {

    Assertions.assertDoesNotThrow(
        () -> {
          Mockito.when(geoSecurityUserService.getUser(Mockito.any(GeoUserDTO.class)))
              .thenReturn(geoUserEntity);

          Mockito.when(topicService.getTopicEntityById(topicDTO.getId())).thenReturn(topicEntity);

          Mockito.when(
                  subTopicService.getSubTopicEntityById(
                      subTopicDTO.getTopicId(), subTopicDTO.getSubTopicId()))
              .thenReturn(subTopicEntity);

          Mockito.when(
                  subTopicMetaDiscussionRepository.save(Mockito.any(SubTopicMetaDiscussion.class)))
              .thenReturn(
                  new SubTopicMetaDiscussion(
                      subTopicMetaDiscussionDTO.getMessage(), subTopicEntity, geoUserEntity));

          int addedSubTopicMetaDiscussId =
              subTopicMetaDiscussionService.addMessage(subTopicMetaDiscussionDTO);

          Assertions.assertTrue(addedSubTopicMetaDiscussId == subTopicMetaDiscussionEntity.getId());
        });
  }

  @Test
  public void deleteMessage() {

    Assertions.assertDoesNotThrow(
        () -> {
          Mockito.when(
                  subTopicMetaDiscussionRepository.findById(
                      subTopicMetaDiscussionDTO.getMessageId()))
              .thenReturn(Optional.of(subTopicMetaDiscussionEntity));

          Mockito.doNothing()
              .when(subTopicMetaDiscussionRepository)
              .delete(Mockito.any(SubTopicMetaDiscussion.class));

          subTopicMetaDiscussionService.deleteMessage(
              subTopicMetaDiscussionDTO.getTopicId(),
              subTopicMetaDiscussionDTO.getSubTopicId(),
              subTopicMetaDiscussionDTO.getMessageId());
        });
  }
}
