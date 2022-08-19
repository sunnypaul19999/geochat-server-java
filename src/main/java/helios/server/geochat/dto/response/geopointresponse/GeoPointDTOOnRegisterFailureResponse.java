package helios.server.geochat.dto.response.geopointresponse;

public class GeoPointDTOOnRegisterFailureResponse extends GeoPointDTOResponse {

    private boolean hasDataBindingfieldError = false;

    private String message = "OOPS! GeoPoint could not be registered";

    public GeoPointDTOOnRegisterFailureResponse() {
        super(false, null);
    }

    public GeoPointDTOOnRegisterFailureResponse(String message) {
        this();
        this.message = message;
    }

    public boolean getHasDataBindingfieldError() {
        return hasDataBindingfieldError;
    }

    public void setHasDataBindingfieldError(boolean hasDataBindingfieldError) {
        this.hasDataBindingfieldError = hasDataBindingfieldError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
