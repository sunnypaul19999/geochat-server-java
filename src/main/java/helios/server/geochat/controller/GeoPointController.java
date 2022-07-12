package helios.server.geochat.controller;

import javax.servlet.http.HttpServletResponse;

import helios.server.geochat.exceptions.dtoException.InvalidRequestFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.dto.response.geoPointResponse.GeoPointDTOOnRegisterFailureResponse;
import helios.server.geochat.dto.response.geoPointResponse.GeoPointDTOOnRegisterSuccessResponse;
import helios.server.geochat.dto.response.geoPointResponse.GeoPointDTOResponse;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointException;
import helios.server.geochat.service.GeoPointService;

@RestController
@RequestMapping(value = "/geopoint")
public class GeoPointController {

  @Autowired GeoPointService geoPointService;

  private Logger logger = LoggerFactory.getLogger(getClass());

  @PostMapping(value = "/register")
  public GeoPointDTOResponse registerGeoPoint(
      HttpServletResponse resp,
      @Valid @RequestBody UserLocationDTO userLocationDTO,
      BindingResult bindingResult)
      throws InvalidRequestFormatException {
    try {
      if (bindingResult.hasErrors()) {
        throw new InvalidRequestFormatException();
      }

      String plusCode = geoPointService.registerGeoPoint(userLocationDTO);

      resp.setStatus(200);

      return new GeoPointDTOOnRegisterSuccessResponse(plusCode);

    } catch (GeoPointException e) {
      logger.error("Error while saving geopoint", e);

      resp.setStatus(500);
    }

    return new GeoPointDTOOnRegisterFailureResponse();
  }

  @GetMapping(value = "/description")
  public String description() {
    return this.getClass().getName();
  }
}
