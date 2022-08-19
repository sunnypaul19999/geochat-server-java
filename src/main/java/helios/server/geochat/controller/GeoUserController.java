package helios.server.geochat.controller;

import helios.server.geochat.dto.request.GeoUserDTO;
import helios.server.geochat.dto.request.NewGeoUserDTO;
import helios.server.geochat.dto.request.VerifyGeoUserDTO;
import helios.server.geochat.dto.response.geouserresponse.GeoUserDTOOnRegiseterFailureResponse;
import helios.server.geochat.dto.response.geouserresponse.GeoUserDTOOnRegiseterSuccessResponse;
import helios.server.geochat.dto.response.geouserresponse.GeoUserDTOResponse;
import helios.server.geochat.dto.response.globalresponse.InvalidRequestFormatGlobalResponse;
import helios.server.geochat.exceptions.dtoexception.InvalidRequestFormatException;
import helios.server.geochat.exceptions.serviceexceptions.geouserserviceexception.GeoUserConfirmPasswordMismatchException;
import helios.server.geochat.exceptions.serviceexceptions.geouserserviceexception.GeoUserException;
import helios.server.geochat.exceptions.serviceexceptions.geouserserviceexception.GeoUserExistsException;
import helios.server.geochat.exceptions.serviceexceptions.geouserserviceexception.GeoUserNotFoundException;
import helios.server.geochat.service.impl.GeoSecurityUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

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
          "Password and Confirm Password mismatch occurred while registering",
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
  public VerifyGeoUserDTO geoChatLogin(HttpServletResponse httpServletResponse)
      throws GeoUserException {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    Optional<String> jwtToken =
        geoUserServiceImpl.setJwtToken(new GeoUserDTO(authentication.getName()));

    if (jwtToken.isPresent()) {

      final Cookie cookie = new Cookie("geoChatJwtToken", jwtToken.get());

      cookie.setHttpOnly(true);

      cookie.setPath("/");

      httpServletResponse.addCookie(cookie);

      return new VerifyGeoUserDTO(authentication.getName(), jwtToken.get());
    }

    return new VerifyGeoUserDTO(authentication.getName(), (String) authentication.getCredentials());
  }

  @GetMapping(value = "/logout")
  public String geoChatLogout(HttpServletResponse httpServletResponse) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    try {

      geoUserServiceImpl.resetJwtToken(new GeoUserDTO(authentication.getName()));

      authentication.setAuthenticated(false);

      httpServletResponse.setStatus(200);

    } catch (GeoUserNotFoundException e) {

      httpServletResponse.setStatus(404);

    } catch (GeoUserException e) {

      logger.error("error occurred while logging out", e);

      httpServletResponse.setStatus(500);
    }

    return "logout";
  }
  
  @ExceptionHandler(value = GeoUserNotFoundException.class)
  public GeoUserDTOResponse handlerGeoUserNotFoundException(HttpServletResponse httpServletResponse,GeoUserNotFoundException e){
    
    httpServletResponse.setStatus(404);

    var dtoResponse = new GeoUserDTOResponse(false);
    
    dtoResponse.setMessage("user not found");
    
    return dtoResponse;
  }
  
  @ExceptionHandler(value = InvalidRequestFormatException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public InvalidRequestFormatGlobalResponse invalidDataRequest(Exception e) {
    
    logger.error("Rejecting DTO. Has binding errors.");
    
    InvalidRequestFormatGlobalResponse invalidRequestFormatGlobalResponse =
            new InvalidRequestFormatGlobalResponse();
    
    return invalidRequestFormatGlobalResponse;
  }
}
