package helios.server.geochat.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import helios.server.geochat.dto.request.NewGeoUserDTO;
import helios.server.geochat.dto.response.geoUserResponse.GeoUserDTOOnRegiseterFailureResponse;
import helios.server.geochat.dto.response.geoUserResponse.GeoUserDTOOnRegiseterSuccessResponse;
import helios.server.geochat.dto.response.geoUserResponse.GeoUserDTOResponse;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserConfirmPasswordMismatchException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserExitsException;
import helios.server.geochat.service.impl.GeoUserServiceImpl;

@RestController
@RequestMapping(value = "/user")
public class GeoUserController {

    @Autowired
    GeoUserServiceImpl geoUserServiceImpl;

    @PostMapping(value = "/register")
    public GeoUserDTOResponse registerGeoPoint(
            @Valid @RequestBody NewGeoUserDTO newGeoUserDTO,
            HttpServletResponse resp,
            BindingResult result) {
        try {
            geoUserServiceImpl.addUser(newGeoUserDTO);

            GeoUserDTOOnRegiseterSuccessResponse geoUserDTOOnRegiseterSuccessResponse = new GeoUserDTOOnRegiseterSuccessResponse();

            return geoUserDTOOnRegiseterSuccessResponse;

        } catch (GeoUserConfirmPasswordMismatchException e) {
            GeoUserDTOOnRegiseterFailureResponse geoUserDTOOnRegiseterFailureResponse = new GeoUserDTOOnRegiseterFailureResponse();

            geoUserDTOOnRegiseterFailureResponse.setMessage("Password and confirm password mismatch");

            return geoUserDTOOnRegiseterFailureResponse;
        } catch (GeoUserExitsException e) {
            GeoUserDTOOnRegiseterFailureResponse geoUserDTOOnRegiseterFailureResponse = new GeoUserDTOOnRegiseterFailureResponse();

            geoUserDTOOnRegiseterFailureResponse.setMessage("User already exits!");

            return geoUserDTOOnRegiseterFailureResponse;
        } catch (GeoUserException e) {
            GeoUserDTOOnRegiseterFailureResponse geoUserDTOOnRegiseterFailureResponse = new GeoUserDTOOnRegiseterFailureResponse();

            geoUserDTOOnRegiseterFailureResponse.setMessage("OOPS! please try later");

            return geoUserDTOOnRegiseterFailureResponse;
        }
    }
}
