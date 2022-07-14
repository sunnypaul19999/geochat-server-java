package helios.server.geochat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helios.server.geochat.exceptions.dtoException.InvalidRequestFormatException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
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
      @Valid @RequestBody UserLocationDTO userLocationDTO,
      BindingResult bindingResult,
      HttpSession session,
      HttpServletResponse httpServletResponse)
      throws InvalidRequestFormatException {
    try {

      if (bindingResult.hasErrors()) {

        throw new InvalidRequestFormatException();
      }

      String plusCode = geoPointService.registerGeoPoint(userLocationDTO);

      session.setAttribute("geoPointPlusCode", "plusCode");

      httpServletResponse.setStatus(200);

      return new GeoPointDTOOnRegisterSuccessResponse(plusCode);

    } catch (GeoPointException e) {

      logger.error("Error while saving geopoint", e);

      httpServletResponse.setStatus(500);
    }

    return new GeoPointDTOOnRegisterFailureResponse();
  }

  @GetMapping(value = "/plusCode")
  public String getPlusCode(HttpSession session) {

    return session.getAttribute("geoPointPlusCode").toString();
  }

  @GetMapping(path = "/setPlusCode")
  public String setPlusCode(HttpSession session) {
    session.setAttribute("geoPointPlusCode", "plusCode");
    return session.getAttribute("geoPointPlusCode").toString();
  }
}
