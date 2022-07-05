package helios.server.geochat.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.dto.response.geoPointResponse.GeoPointDTOOnRegisterFailureResponse;
import helios.server.geochat.dto.response.geoPointResponse.GeoPointDTOOnRegisterSuccessResponse;
import helios.server.geochat.dto.response.geoPointResponse.GeoPointDTOResponse;
import helios.server.geochat.exceptions.dtoException.InvalidDTOFieldValueException;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointException;
import helios.server.geochat.service.GeoPointService;

@RestController
@RequestMapping(value = "/geopoint")
public class GeoPointController {

    @Autowired
    GeoPointService geoPointService;

    @PostMapping(value = "/register")
    public GeoPointDTOResponse registerGeoPoint(
            HttpServletResponse resp,
            @Valid @RequestBody UserLocationDTO userLocationDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                Logger.getLogger(getClass().getName()).log(Level.INFO, "rejecting values sent by user. not valid");

                throw new InvalidDTOFieldValueException();
            }

            String plusCode = geoPointService.registerGeoPoint(userLocationDTO);

            resp.setStatus(200);

            return new GeoPointDTOOnRegisterSuccessResponse(plusCode);

        } catch (InvalidDTOFieldValueException e) {
            GeoPointDTOOnRegisterFailureResponse onRegisterDataBindfailure = new GeoPointDTOOnRegisterFailureResponse();

            onRegisterDataBindfailure.setHasDataBindingfieldError(true);

            resp.setStatus(406);
            onRegisterDataBindfailure.setMessage("Data binding error");

            return onRegisterDataBindfailure;
        } catch (GeoPointException e) {
            resp.setStatus(500);

        } catch (Exception e) {
            resp.setStatus(500);
            Logger.getLogger(getClass().getName())
                    .log(java.util.logging.Level.SEVERE, "error while saving geopoint", e);
        }

        return new GeoPointDTOOnRegisterFailureResponse();
    }

    @GetMapping(value = "/description")
    public String description() {
        return this.getClass().getName();
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public GeoPointDTOOnRegisterFailureResponse invalidDataRequest(HttpMessageNotReadableException e) {
        GeoPointDTOOnRegisterFailureResponse onRegisterDataBindfailure = new GeoPointDTOOnRegisterFailureResponse();

        onRegisterDataBindfailure.setHasDataBindingfieldError(true);

        onRegisterDataBindfailure.setMessage("Data binding error");

        return onRegisterDataBindfailure;
    }
}
