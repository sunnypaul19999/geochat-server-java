package helios.server.geochat.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserLocationDTO {

    @NotNull
    @Max(value = 90, message = "longitude value must be within 180 to -180")
    @Min(value = -90, message = "longitude value must be within 180 to -180")
    private double lat;

    @NotNull
    @Max(value = 180, message = "longitude value must be within 180 to -180")
    @Min(value = -180, message = "longitude value must be within 180 to -180")
    private double lon;

    public UserLocationDTO(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}