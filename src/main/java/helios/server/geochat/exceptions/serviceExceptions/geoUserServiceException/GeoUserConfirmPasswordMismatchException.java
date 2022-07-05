package helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException;

public class GeoUserConfirmPasswordMismatchException extends GeoUserException {

    private static final String MSGFORMAT = "GeoUser pasword and confirm password mismatch";

    public GeoUserConfirmPasswordMismatchException(String operation) {
        super(operation, MSGFORMAT);
    }
}
