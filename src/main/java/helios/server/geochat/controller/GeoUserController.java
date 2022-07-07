package helios.server.geochat.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import helios.server.geochat.dto.request.NewGeoUserDTO;
import helios.server.geochat.dto.response.geoUserResponse.GeoUserDTOOnRegiseterFailureResponse;
import helios.server.geochat.dto.response.geoUserResponse.GeoUserDTOOnRegiseterSuccessResponse;
import helios.server.geochat.dto.response.geoUserResponse.GeoUserDTOResponse;
import helios.server.geochat.exceptions.dtoException.InvalidDTOFieldValueException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserConfirmPasswordMismatchException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserExistsException;
import helios.server.geochat.service.impl.GeoUserServiceImpl;

@RestController
@RequestMapping(value = "/user")
public class GeoUserController {

    @Autowired
    GeoUserServiceImpl geoUserServiceImpl;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping(value = "/register")
    public GeoUserDTOResponse registerGeoPoint(
            HttpServletResponse resp,
            @Valid @RequestBody NewGeoUserDTO newGeoUserDTO,
            BindingResult bindingResult) {
        logger.error("*******************");
        try {
            if (bindingResult.hasErrors()) {
                throw new InvalidDTOFieldValueException();
            }

            geoUserServiceImpl.addUser(newGeoUserDTO);

            resp.setStatus(200);
            GeoUserDTOOnRegiseterSuccessResponse geoUserDTOOnRegiseterSuccessResponse = new GeoUserDTOOnRegiseterSuccessResponse();

            return geoUserDTOOnRegiseterSuccessResponse;

        } catch (InvalidDTOFieldValueException e) {
            logger.info("rejecting userLocationDTO values sent by user. Not valid has binding errors %s",
                    newGeoUserDTO.toString());

            GeoUserDTOOnRegiseterFailureResponse onRegisterDataBindfailure = new GeoUserDTOOnRegiseterFailureResponse();

            onRegisterDataBindfailure.setHasDataBindingfieldError(true);

            resp.setStatus(406);
            onRegisterDataBindfailure.setMessage("Data binding error");

            return onRegisterDataBindfailure;
        } catch (GeoUserConfirmPasswordMismatchException e) {
            logger.info("Password and Confirm Password mismatch occured while registering", newGeoUserDTO.toString());

            GeoUserDTOOnRegiseterFailureResponse geoUserDTOOnRegiseterFailureResponse = new GeoUserDTOOnRegiseterFailureResponse();

            resp.setStatus(400);
            geoUserDTOOnRegiseterFailureResponse.setMessage("Password and confirm password mismatch");

            return geoUserDTOOnRegiseterFailureResponse;
        } catch (GeoUserExistsException e) {
            logger.info("User already exits %s", newGeoUserDTO.toString());

            GeoUserDTOOnRegiseterFailureResponse geoUserDTOOnRegiseterFailureResponse = new GeoUserDTOOnRegiseterFailureResponse();

            resp.setStatus(409);
            geoUserDTOOnRegiseterFailureResponse.setMessage("User already exits!");

            return geoUserDTOOnRegiseterFailureResponse;
        } catch (GeoUserException e) {
            logger.error("Error while registering geouser", e);

            GeoUserDTOOnRegiseterFailureResponse geoUserDTOOnRegiseterFailureResponse = new GeoUserDTOOnRegiseterFailureResponse();

            resp.setStatus(500);
            geoUserDTOOnRegiseterFailureResponse.setMessage("OOPS! please try later");

            return geoUserDTOOnRegiseterFailureResponse;
        }
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public GeoUserDTOOnRegiseterFailureResponse invalidDataRequest(HttpMessageNotReadableException e) {
        logger.info("Rejecting userLocationDTO values sent by user. Not valid has binding errors.");

        GeoUserDTOOnRegiseterFailureResponse onRegisterDataBindfailure = new GeoUserDTOOnRegiseterFailureResponse();

        onRegisterDataBindfailure.setHasDataBindingfieldError(true);

        onRegisterDataBindfailure.setMessage("Data binding error");

        return onRegisterDataBindfailure;
    }
}
