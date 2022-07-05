package helios.server.geochat.dto.response.geoPointResponse;

import javax.validation.constraints.NotNull;

public class GeoPointDTOOnRegisterSuccessResponse extends GeoPointDTOResponse {

    private static final String MESSAGE = "GeoPoint registered!";

    public GeoPointDTOOnRegisterSuccessResponse(@NotNull String plusCode) {
        super(true, plusCode);
    }

    public static String getMessage() {
        return MESSAGE;
    }
}
