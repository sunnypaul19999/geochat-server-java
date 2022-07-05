package helios.server.geochat.service;

import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointDefaultRangeNotFoundException;
import helios.server.geochat.model.GeoPointRange;

public interface GeoPointRangeService {
    public GeoPointRange getDefaultGeoPointRange() throws GeoPointDefaultRangeNotFoundException;

}
