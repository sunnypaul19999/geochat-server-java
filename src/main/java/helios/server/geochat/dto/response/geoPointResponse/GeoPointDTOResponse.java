package helios.server.geochat.dto.response.geoPointResponse;

import javax.validation.constraints.NotNull;

public class GeoPointDTOResponse {

    @NotNull
    private boolean executionStatus;

    private String plusCode;

    public GeoPointDTOResponse(@NotNull boolean executionStatus, String plusCode) {
        this.executionStatus = executionStatus;
        this.plusCode = plusCode;
    }

    public boolean isExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(boolean executionStatus) {
        this.executionStatus = executionStatus;
    }

    public String getPlusCode() {
        return plusCode;
    }

    public void setPlusCode(String plusCode) {
        this.plusCode = plusCode;
    }
}
