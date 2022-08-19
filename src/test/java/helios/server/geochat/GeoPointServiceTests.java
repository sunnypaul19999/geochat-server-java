package helios.server.geochat;

import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.exceptions.serviceexceptions.geopointserviceexception.GeoPointException;
import helios.server.geochat.service.GeoPointService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GeoPointServiceTests {

  private final GeoPointService geoPointService;

  private final UserLocationDTO newUserLocationDTO;

  private String newPlusCode;

  @Autowired
  public GeoPointServiceTests(GeoPointService geoPointService) {

    this.geoPointService = geoPointService;

    newUserLocationDTO = new UserLocationDTO(23.677303229900822, 86.94993375397198);
  }

  @Test
  public void testGeoPointNotExists() {

    String registeredPlusCode = geoPointService.isGeoPointRegistered(new UserLocationDTO(0.0, 0.0));

    if (!registeredPlusCode.isEmpty()) {

      Assertions.fail("Non registered GeoPoint is found");
    }
  }

  @Test
  public void testAddGeoPoint() throws GeoPointException {

    newPlusCode = geoPointService.registerGeoPoint(newUserLocationDTO);
  }

  @Test
  public void testGeoPointExists() {

    String expectedPlusCode = GeoPointService.calcPlusCode(newUserLocationDTO);

    String registeredPlusCode = geoPointService.isGeoPointRegistered(newUserLocationDTO);

    if (!(expectedPlusCode.equals(registeredPlusCode))) {

      Assertions.fail(
          String.format(
              "Expected GeoPoint plus-code %s but got %s", expectedPlusCode, registeredPlusCode));
    }
  }
}
