package helios.server.geochat.servicetest;

import helios.server.geochat.dto.request.SubTopicDTO;
import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.model.GeoPoint;
import helios.server.geochat.model.SubTopic;
import helios.server.geochat.model.Topic;
import helios.server.geochat.repository.SubTopicRepository;
import helios.server.geochat.service.GeoPointService;
import helios.server.geochat.service.TopicService;
import helios.server.geochat.service.impl.SubTopicServiceImpl;
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
class SubTopicServiceTest {

  @Mock private TopicService topicService;

  @Mock private SubTopicRepository subTopicRepository;

  @InjectMocks private SubTopicServiceImpl subTopicService;

  private UserLocationDTO userLocationDTO;

  private TopicDTO topicDTO;

  private SubTopicDTO subTopicDTO;

  private GeoPoint geoPointEntity;

  private Topic topicEntity;

  private SubTopic subTopicEntity;

  @BeforeEach
  public void init() {
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
  }

  @Test
  void addSubTopicTest() {

    Assertions.assertDoesNotThrow(
        () -> {
          Mockito.when(topicService.getTopicEntityById(topicDTO.getId())).thenReturn(topicEntity);

          Mockito.when(subTopicRepository.save(Mockito.any(SubTopic.class)))
              .thenReturn(subTopicEntity);

          SubTopicDTO addedSubTopicDTO = subTopicService.addSubTopic(subTopicDTO);

          Assertions.assertTrue(
              () -> {
                if (addedSubTopicDTO.compareTo(subTopicDTO) == 0) {

                  return true;
                }

                return false;
              });
        });
  }

  @Test
  void updateSubTopicTest() {

    Mockito.when(
            subTopicRepository.findByTopicTopicIdAndId(
                subTopicDTO.getTopicId(), subTopicDTO.getSubTopicId()))
        .thenReturn(Optional.of(subTopicEntity));

    subTopicEntity.setTopic(topicEntity);
    Mockito.when(subTopicRepository.save(subTopicEntity)).thenReturn(subTopicEntity);

    Assertions.assertDoesNotThrow(
        () -> {
          SubTopicDTO updatedSubTopicDTO =
              subTopicService.updateSubTopic(
                  new SubTopicDTO(
                      subTopicDTO.getGeoPointPlusCode(),
                      subTopicDTO.getTopicId(),
                      subTopicDTO.getSubTopicId(),
                      "update sub-topic title",
                      "update sub-topic description"));

          Assertions.assertTrue(
              () -> {
                if (updatedSubTopicDTO.getTopicId() == subTopicEntity.getTopic().getTopicId()
                    && updatedSubTopicDTO.getSubTopicId() == subTopicEntity.getId()
                    && updatedSubTopicDTO.getSubTopicTitle().equals(subTopicEntity.getTitle())
                    && updatedSubTopicDTO
                        .getSubTopicDescription()
                        .equals(subTopicEntity.getDescription())) {

                  return true;
                }

                return false;
              });
        });
  }

  @Test
  void deleteSubTopicTest() {

    Mockito.when(
            subTopicRepository.findByTopicTopicIdAndId(
                subTopicDTO.getTopicId(), subTopicDTO.getSubTopicId()))
        .thenReturn(Optional.of(subTopicEntity));

    subTopicEntity.setTopic(topicEntity);
    Mockito.doNothing().when(subTopicRepository).delete(subTopicEntity);

    Assertions.assertDoesNotThrow(
        () -> {
          SubTopicDTO delSubTopicDTO =
              subTopicService.deleteSubTopic(subTopicDTO.getTopicId(), subTopicDTO.getSubTopicId());

          Assertions.assertTrue(
              () -> {
                if (delSubTopicDTO.compareTo(subTopicDTO) == 0) {

                  return true;
                }

                return false;
              });
        });
  }
}
