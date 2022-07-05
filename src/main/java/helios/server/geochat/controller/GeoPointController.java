package helios.server.geochat.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import helios.server.geochat.dto.InvalidDTOFieldValueException;
import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.dto.response.geoPointResponse.GeoPointDTOOnRegisterFailureResponse;
import helios.server.geochat.dto.response.geoPointResponse.GeoPointDTOOnRegisterSuccessResponse;
import helios.server.geochat.dto.response.geoPointResponse.GeoPointDTOResponse;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointException;
import helios.server.geochat.service.GeoPointService;

@RestController
@RequestMapping(value = "/geopoint")
public class GeoPointController {

    @Autowired
    GeoPointService geoPointService;

    @PostMapping(value = "/register")
    public GeoPointDTOResponse registerGeoPoint(@Valid @RequestBody UserLocationDTO userLocationDTO,
            HttpServletResponse resp,
            BindingResult result)
            throws InvalidDTOFieldValueException {

        if (result.hasErrors()) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "rejecting values sent by user. not valid");

            throw new InvalidDTOFieldValueException();
        } else {
            try {
                String plusCode = geoPointService.registerGeoPoint(userLocationDTO);

                resp.setStatus(200);

                return new GeoPointDTOOnRegisterSuccessResponse(plusCode);

            } catch (GeoPointException e) {
                resp.setStatus(500);

            } catch (Exception e) {
                resp.setStatus(500);
                Logger.getLogger(getClass().getName())
                        .log(java.util.logging.Level.SEVERE, "error while saving geopoint", e);
            }
        }

        return new GeoPointDTOOnRegisterFailureResponse();
    }

    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(value = InvalidDTOFieldValueException.class)
    public InvalidDTOFieldValueException.ErrorView invalidAttributeValues(InvalidDTOFieldValueException e) {
        return e.getErrorView();
    }

    @GetMapping(value = "/description")
    public String description() {
        return this.getClass().getName();
    }
}
