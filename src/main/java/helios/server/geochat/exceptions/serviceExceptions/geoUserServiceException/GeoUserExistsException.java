package helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException;

public class GeoUserExistsException extends GeoUserException {

    private static final String MSGFORMAT = "GeoUser with Id %s already exits";

    public GeoUserExistsException(String username, String operation) {
        super(operation, String.format(MSGFORMAT, username));
    }
}
