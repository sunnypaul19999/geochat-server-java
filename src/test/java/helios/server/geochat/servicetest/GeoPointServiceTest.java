package helios.server.geochat.servicetest;

import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.model.GeoPoint;
import helios.server.geochat.repository.GeoPointRepository;
import helios.server.geochat.service.GeoPointService;
import helios.server.geochat.service.impl.GeoPointRangeServiceImpl;
import helios.server.geochat.service.impl.GeoPointServiceImpl;
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
class GeoPointServiceTest {

  @Mock private GeoPointRepository geoPointRepository;

  @Mock private GeoPointRangeServiceImpl geoPointRangeService;

  @InjectMocks private GeoPointServiceImpl geoPointService;

  private UserLocationDTO userLocationDTO;

  private GeoPoint geoPointEntity;

  @BeforeEach
  void init() {
    userLocationDTO = new UserLocationDTO(23.677303229900822, 86.94993375397198);
    geoPointEntity = new GeoPoint(GeoPointService.calcPlusCode(userLocationDTO), userLocationDTO);
  }

  @Test
  void addGeoPointTest() {

    GeoPointServiceImpl geoPointServiceSpy = Mockito.spy(geoPointService);

    Mockito.doReturn("").when(geoPointServiceSpy).isGeoPointRegistered(userLocationDTO);

    Assertions.assertDoesNotThrow(
        () -> {
          Mockito.doReturn("")
              .when(geoPointServiceSpy)
              .checkIfInRangeWithOtherGeoPoint(userLocationDTO);

          Mockito.when(geoPointRepository.save(Mockito.any(GeoPoint.class)))
              .thenReturn(geoPointEntity);

          Assertions.assertEquals(
              geoPointServiceSpy.registerGeoPoint(userLocationDTO), geoPointEntity.getPlusCode());
        });
  }

  @Test
  void isGeoPointRegisteredTest() {

    Mockito.when(geoPointRepository.findById(GeoPointService.calcPlusCode(userLocationDTO)))
        .thenReturn(Optional.of(geoPointEntity));

    Assertions.assertEquals(
        geoPointService.isGeoPointRegistered(userLocationDTO), geoPointEntity.getPlusCode());
  }
}
