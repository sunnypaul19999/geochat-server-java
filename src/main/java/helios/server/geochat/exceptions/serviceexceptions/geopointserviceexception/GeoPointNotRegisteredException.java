package helios.server.geochat.exceptions.serviceexceptions.geopointserviceexception;

public class GeoPointNotRegisteredException extends GeoPointException {

  private static final String MESSAGE_FORMAT = "GeoPoint with plusCode = %s is not registered!";

  public GeoPointNotRegisteredException(String plusCode) {
    super("CHECK_IF_GEOPOINT_REGISTERED", String.format(MESSAGE_FORMAT, plusCode));
  }
}
