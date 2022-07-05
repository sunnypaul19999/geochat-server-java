package helios.server.geochat.dto.response.geoUserResponse;

import javax.validation.constraints.NotNull;

public class GeoUserDTOResponse {
    @NotNull
    private boolean executionStatus;

    @NotNull
    private String message;

    public GeoUserDTOResponse(boolean executionStatus) {
        this.executionStatus = executionStatus;
    }

    public boolean getExecutionStatus() {
        return executionStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
