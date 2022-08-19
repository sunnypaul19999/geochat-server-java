package helios.server.geochat.dto.response.geouserresponse;

public class GeoUserDTOOnRegiseterFailureResponse extends GeoUserDTOResponse {

    private boolean hasDataBindingfieldError;

    public GeoUserDTOOnRegiseterFailureResponse() {
        super(false);
        this.hasDataBindingfieldError = false;
    }

    public boolean getHasDataBindingfieldError() {
        return hasDataBindingfieldError;
    }

    public void setHasDataBindingfieldError(boolean hasDataBindingfieldError) {
        this.hasDataBindingfieldError = hasDataBindingfieldError;
    }
}
