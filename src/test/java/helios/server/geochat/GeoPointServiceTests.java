package helios.server.geochat;

import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointException;
import helios.server.geochat.service.GeoPointService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GeoPointServiceTests {

  private GeoPointService geoPointService;

  private UserLocationDTO newUserLocationDTO;

  private String newPlusCode;

  @Autowired
  public GeoPointServiceTests(GeoPointService geoPointService) {

    this.geoPointService = geoPointService;
  }

  @BeforeEach
  public void init() {

    newUserLocationDTO = new UserLocationDTO(23.677303229900822, 86.94993375397198);
  }

  @Test
  @Order(1)
  public void testGeoPointNotExists() {

    String registeredPlusCode = geoPointService.isGeoPointRegistered(new UserLocationDTO(0.0, 0.0));

    if (!registeredPlusCode.isEmpty()) {

      Assertions.fail("Non registered GeoPoint is found");
    }
  }

  @Test
  @Order(2)
  public void testAddGeoPoint() throws GeoPointException {

    newPlusCode = geoPointService.registerGeoPoint(newUserLocationDTO);
  }

  @Test
  @Order(3)
  public void testGeoPointExists() {

    String expectedPlusCode = geoPointService.calcPlusCode(newUserLocationDTO);

    String registeredPlusCode = geoPointService.isGeoPointRegistered(newUserLocationDTO);

    if (!(expectedPlusCode.equals(registeredPlusCode))) {

      Assertions.fail(
          String.format(
              "Expected GeoPoint plus-code %s but got %s", expectedPlusCode, registeredPlusCode));
    }
  }
}
