package helios.server.geochat.exceptions.serviceexceptions.geouserserviceexception;

public class GeoUserException extends Exception {

    private static final String MSGFORMAT = "Failure to perform operation %s as %s";

    public GeoUserException(String operation, String message) {
        super(String.format(MSGFORMAT, operation.toUpperCase(), message));
    }
}
