package helios.server.geochat.service;

import helios.server.geochat.dto.request.GeoUserDTO;
import helios.server.geochat.dto.request.NewGeoUserDTO;
import helios.server.geochat.dto.request.VerifyGeoUserDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserNotFoundException;
import helios.server.geochat.model.GeoUser;

public interface GeoUserService {

    boolean verifyUser(VerifyGeoUserDTO geoUserDTO) throws GeoUserException;

    GeoUser getUser(GeoUserDTO geoUserDTO) throws GeoUserNotFoundException;

    void addUser(NewGeoUserDTO newGeoUserDTO) throws GeoUserException;

    void addUserAsAdmin(NewGeoUserDTO newGeoUserDTO) throws GeoUserException;

    void deleteUser(GeoUserDTO geoUserDTO) throws GeoUserException;
}
