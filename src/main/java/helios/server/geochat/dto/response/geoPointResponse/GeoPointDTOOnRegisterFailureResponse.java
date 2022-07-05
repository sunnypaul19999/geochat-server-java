package helios.server.geochat.dto.response.geoPointResponse;

public class GeoPointDTOOnRegisterFailureResponse extends GeoPointDTOResponse {

    private static final String MESSAGE = "OOPS! GeoPoint could not be registered";

    public GeoPointDTOOnRegisterFailureResponse() {
        super(false, null);
    }

    public String getMSG() {
        return MESSAGE;
    }
}
