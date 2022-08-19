package helios.server.geochat.exceptions.serviceexceptions.geouserserviceexception;

public class GeoUserNotFoundException extends GeoUserException {

    private static final String MSGFORMAT = "GeoUser with username %s does not exits";

    public GeoUserNotFoundException(String username, String operation) {
        super(operation, String.format(MSGFORMAT, username));
    }
}
