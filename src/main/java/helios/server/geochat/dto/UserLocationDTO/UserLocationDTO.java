package helios.server.geochat.dto.UserLocationDTO;

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

    @NotNull
    @Max(value = 10000)
    @Min(value = 100)
    private int radius;

    public UserLocationDTO(double lat, double lon, int radius) {
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}