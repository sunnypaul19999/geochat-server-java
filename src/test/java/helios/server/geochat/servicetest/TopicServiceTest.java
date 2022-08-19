package helios.server.geochat.servicetest;

import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.model.GeoPoint;
import helios.server.geochat.model.Topic;
import helios.server.geochat.repository.TopicRepository;
import helios.server.geochat.service.GeoPointService;
import helios.server.geochat.service.impl.GeoPointServiceImpl;
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
class TopicServiceTest {

  @Mock GeoPointServiceImpl geoPointService;

  @Mock TopicRepository topicRepository;

  @InjectMocks TopicServiceImpl topicService;

  TopicDTO topicDTO;

  Topic topicEntity;

  UserLocationDTO userLocationDTO;

  GeoPoint geoPointEntity;

  @BeforeEach
  void init() {
    userLocationDTO = new UserLocationDTO(23.677303229900822, 86.94993375397198);
    geoPointEntity = new GeoPoint(GeoPointService.calcPlusCode(userLocationDTO), userLocationDTO);

    topicDTO = new TopicDTO(geoPointEntity.getPlusCode(), 1, "mock topic title");
    topicEntity = new Topic(topicDTO);
    topicEntity.setTopicId(topicDTO.getId());
    topicEntity.setGeoPoint(geoPointEntity);
  }

  @Test
  void addTopicTest() {

    Assertions.assertDoesNotThrow(
        () -> {
          Mockito.lenient()
              .when(
                  geoPointService.isGeoPointRegistered(Mockito.mock(TopicDTO.class).getPlusCode()))
              .thenReturn(geoPointEntity);

          Mockito.lenient()
              .when(topicRepository.save(Mockito.any(Topic.class)))
              .thenReturn(topicEntity);

          TopicDTO addedTopicDTO = topicService.addTopic(topicDTO);

          Assertions.assertTrue(
              addedTopicDTO.getId() == topicDTO.getId()
                  && addedTopicDTO.getTopicTitle().equals(topicDTO.getTopicTitle()));
        });
  }

  @Test
  void updateTopicTest() {

    Assertions.assertDoesNotThrow(
        () -> {
          Mockito.when(topicRepository.findById(topicDTO.getId()))
              .thenReturn(Optional.of(topicEntity));

          Mockito.when(topicRepository.save(topicEntity)).thenReturn(topicEntity);

          TopicDTO updatedTopicDTO = topicService.updateTopic(topicDTO);

          Assertions.assertTrue(
              () -> {
                if (updatedTopicDTO.getId() == topicEntity.getTopicId()
                    && updatedTopicDTO.getTopicTitle().equals(topicEntity.getTopicTitle())) {

                  return true;
                }

                return false;
              });
        });
  }

  @Test
  void deleteTopic() {

    Mockito.when(topicRepository.findById(topicDTO.getId())).thenReturn(Optional.of(topicEntity));

    Mockito.doNothing().when(topicRepository).delete(topicEntity);

    Assertions.assertDoesNotThrow(
        () -> {
          TopicDTO deletedTopicDTO = topicService.deleteTopic(topicDTO.getId());

          Assertions.assertEquals(deletedTopicDTO.getId(), topicDTO.getId());
        });
  }
}
