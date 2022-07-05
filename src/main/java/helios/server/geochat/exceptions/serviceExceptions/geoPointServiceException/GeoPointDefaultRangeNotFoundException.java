package helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException;

public class GeoPointDefaultRangeNotFoundException extends GeoPointException {

    private static final String MSGFORMAT = "Default range for GeoPoints does not exists";

    public GeoPointDefaultRangeNotFoundException(String operation) {
        super(operation, MSGFORMAT);
    }
}
