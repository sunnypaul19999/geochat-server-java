package helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException;

public class GeoUserNotFoundException extends GeoUserException {

    private static final String MSGFORMAT = "GeoUser with username %s does not exits";

    public GeoUserNotFoundException(String username, String operation) {
        super(operation, String.format(MSGFORMAT, username));
    }
}
