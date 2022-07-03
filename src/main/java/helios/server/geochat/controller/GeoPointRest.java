package helios.server.geochat.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;

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
import helios.server.geochat.dto.UserLocationDTO.UserLocationDTO;
import helios.server.geochat.service.GeoPointService;

@RestController
@RequestMapping(value = "/geopoint")
public class GeoPointRest {

    @Autowired
    GeoPointService geoPointService;

    @PostMapping(value = "/register")
    public UserLocationDTO registerGeoPoint(@Valid @RequestBody UserLocationDTO userLocationDTO, BindingResult result)
            throws InvalidDTOFieldValueException {

        if (result.hasErrors()) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "rejecting values sent by user. not valid");
            throw new InvalidDTOFieldValueException();
        } else {
            try {
                geoPointService.registerGeoPoint(userLocationDTO);
            } catch (Exception e) {
                Logger.getLogger(getClass().getName())
                        .log(java.util.logging.Level.SEVERE, "error while saving geopoint", e);
            }
        }
        return userLocationDTO;
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
