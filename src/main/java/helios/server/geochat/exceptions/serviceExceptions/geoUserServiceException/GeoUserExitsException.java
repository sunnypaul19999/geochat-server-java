package helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException;

public class GeoUserExitsException extends GeoUserException {

    private static final String MSGFORMAT = "GeoUser with Id %s already exits";

    public GeoUserExitsException(String username, String operation) {
        super(operation, String.format(MSGFORMAT, username));
    }
}
