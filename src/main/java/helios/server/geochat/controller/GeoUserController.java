package helios.server.geochat.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import helios.server.geochat.dto.response.globalresponse.InvalidRequestFormatGlobalResponse;
import helios.server.geochat.exceptions.dtoException.InvalidRequestFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import helios.server.geochat.dto.request.NewGeoUserDTO;
import helios.server.geochat.dto.response.geoUserResponse.GeoUserDTOOnRegiseterFailureResponse;
import helios.server.geochat.dto.response.geoUserResponse.GeoUserDTOOnRegiseterSuccessResponse;
import helios.server.geochat.dto.response.geoUserResponse.GeoUserDTOResponse;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserConfirmPasswordMismatchException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserExistsException;
import helios.server.geochat.service.impl.GeoSecurityUserServiceImpl;

@RestController
@RequestMapping(value = "/user")
public class GeoUserController {

  @Autowired GeoSecurityUserServiceImpl geoUserServiceImpl;

  private Logger logger = LoggerFactory.getLogger(getClass());

  @PostMapping(value = "/register")
  public GeoUserDTOResponse registerGeoPoint(
      HttpServletResponse resp,
      @Valid @RequestBody NewGeoUserDTO newGeoUserDTO,
      BindingResult bindingResult)
      throws InvalidRequestFormatException {
    try {
      if (bindingResult.hasErrors()) {
        throw new InvalidRequestFormatException();
      }

      geoUserServiceImpl.addUser(newGeoUserDTO);

      resp.setStatus(200);
      GeoUserDTOOnRegiseterSuccessResponse geoUserDTOOnRegiseterSuccessResponse =
          new GeoUserDTOOnRegiseterSuccessResponse();

      return geoUserDTOOnRegiseterSuccessResponse;

    } catch (GeoUserConfirmPasswordMismatchException e) {
      logger.info(
          "Password and Confirm Password mismatch occured while registering",
          newGeoUserDTO.toString());

      GeoUserDTOOnRegiseterFailureResponse geoUserDTOOnRegiseterFailureResponse =
          new GeoUserDTOOnRegiseterFailureResponse();

      resp.setStatus(400);
      geoUserDTOOnRegiseterFailureResponse.setMessage("Password and confirm password mismatch");

      return geoUserDTOOnRegiseterFailureResponse;
    } catch (GeoUserExistsException e) {
      logger.info("User already exits %s", newGeoUserDTO.toString());

      GeoUserDTOOnRegiseterFailureResponse geoUserDTOOnRegiseterFailureResponse =
          new GeoUserDTOOnRegiseterFailureResponse();

      resp.setStatus(409);
      geoUserDTOOnRegiseterFailureResponse.setMessage("User already exits!");

      return geoUserDTOOnRegiseterFailureResponse;
    } catch (GeoUserException e) {
      logger.error("Error while registering geouser", e);

      GeoUserDTOOnRegiseterFailureResponse geoUserDTOOnRegiseterFailureResponse =
          new GeoUserDTOOnRegiseterFailureResponse();

      resp.setStatus(500);
      geoUserDTOOnRegiseterFailureResponse.setMessage("OOPS! please try later");

      return geoUserDTOOnRegiseterFailureResponse;
    }
  }

  @GetMapping(value = "/login")
  public void geoChatLogin() {}

  @GetMapping(value = "/logout")
  public void geoChatLogout() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    authentication.setAuthenticated(false);
  }
}
