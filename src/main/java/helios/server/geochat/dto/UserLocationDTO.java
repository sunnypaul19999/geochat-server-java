package helios.server.geochat.dto;

import org.springframework.lang.NonNull;

public class UserLocationDTO {

    @NonNull
    private double lat;

    @NonNull
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
