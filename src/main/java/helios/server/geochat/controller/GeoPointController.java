package helios.server.geochat.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import helios.server.geochat.dto.response.globalresponse.InvalidRequestFormatGlobalResponse;
import helios.server.geochat.exceptions.dtoexception.InvalidRequestFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.dto.response.geopointresponse.GeoPointDTOOnRegisterFailureResponse;
import helios.server.geochat.dto.response.geopointresponse.GeoPointDTOOnRegisterSuccessResponse;
import helios.server.geochat.dto.response.geopointresponse.GeoPointDTOResponse;
import helios.server.geochat.exceptions.serviceexceptions.geopointserviceexception.GeoPointException;
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

      Cookie cookie = new Cookie("geoPointPlusCode", plusCode);

      cookie.setHttpOnly(false);
      cookie.setPath("/");
      cookie.setHttpOnly(false);
      httpServletResponse.addCookie(cookie);

      return new GeoPointDTOOnRegisterSuccessResponse(plusCode);

    } catch (GeoPointException e) {

      logger.error("Error while saving geopoint", e);

      httpServletResponse.setStatus(500);
    }

    return new GeoPointDTOOnRegisterFailureResponse();
  }

  @PostMapping(value = "/plusCode")
  public String getPlusCode(@CookieValue(name = "geoPointPlusCode") String geoPointPlusCode) {
    // @CookieValue(value = "geoPointPlusCode") String plusCode
    // Cookie[] cookies = request.getCookies();

    //    ArrayList<Cookie> cookieList = new ArrayList<>();
    //
    //    Collections.addAll(cookieList, cookies);
    //
    //    logger.error(String.format("Cookies in request is %s ", cookies.length));

    // return String.format("Cookies in request is %s ", (cookies == null) ? "null" :
    // cookies[1].getValue());
    return geoPointPlusCode;
  }

  @GetMapping(path = "/setPlusCode")
  public String setPlusCode(HttpSession session) {
    session.setAttribute("geoPointPlusCode", "plusCode");
    return session.getAttribute("geoPointPlusCode").toString();
  }


  @PostMapping(path = "/auth-header-test")
  public void authHeaderTest(){}


  @ExceptionHandler(value = InvalidRequestFormatException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public InvalidRequestFormatGlobalResponse invalidDataRequest(HttpMessageNotReadableException e) {

    logger.error("Rejecting DTO. Has binding errors.");

    InvalidRequestFormatGlobalResponse invalidRequestFormatGlobalResponse =
        new InvalidRequestFormatGlobalResponse();

    return invalidRequestFormatGlobalResponse;
  }
}
