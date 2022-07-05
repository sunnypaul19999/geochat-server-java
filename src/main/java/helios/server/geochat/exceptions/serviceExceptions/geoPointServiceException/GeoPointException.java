package helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException;

public class GeoPointException extends Exception {

    private static final String MSGFORMAT = "Failure to perform operation %s as %s";

    public GeoPointException(String operation, String message) {
        super(String.format(MSGFORMAT, operation.toUpperCase(), message));
    }
}
