package helios.server.geochat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import helios.server.geochat.dto.UserLocationDTO;
import helios.server.geochat.service.GeoPointService;

@RestController
@RequestMapping(value = "/geopoint")
public class GeoPointRest {

    @Autowired
    GeoPointService geoPointService;

    @PostMapping(value = "/register")
    public UserLocationDTO registerGeoPoint(@RequestBody UserLocationDTO userLocationDTO) {
        geoPointService.registerGeoPoint(userLocationDTO);
        return userLocationDTO;
    }

    @GetMapping(value = "/description")
    public String description() {
        return this.getClass().getName();
    }
}
