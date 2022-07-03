package helios.server.geochat.service;

import helios.server.geochat.dto.UserLocationDTO.UserLocationDTO;

public interface GeoPointService {

    public boolean registerGeoPoint(UserLocationDTO userLocationDTO);
}
