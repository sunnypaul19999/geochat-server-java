package helios.server.geochat.exceptions.serviceexceptions.geouserserviceexception;

public class GeoUserConfirmPasswordMismatchException extends GeoUserException {

    private static final String MSGFORMAT = "GeoUser pasword and confirm password mismatch";

    public GeoUserConfirmPasswordMismatchException(String operation) {
        super(operation, MSGFORMAT);
    }
}
