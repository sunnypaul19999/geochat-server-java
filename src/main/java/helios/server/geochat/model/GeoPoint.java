package helios.server.geochat.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import helios.server.geochat.dto.UserLocationDTO.UserLocationDTO;

@Entity
@Table(name = "geopoint")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoPoint {

    @Id
    @Column(name = "plus_code")
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String plusCode;

    @Column(name = "lattitude")
    private double lat;

    @Column(name = "longitude")
    private double lon;

    // hibernate use no-args constructor to create the object
    // use getters and setters of the fields to get and set values
    GeoPoint() {
    }

    public GeoPoint(String plusCode, UserLocationDTO userLocationDTO) {
        this.plusCode = plusCode;
        this.lat = userLocationDTO.getLat();
        this.lon = userLocationDTO.getLon();
    }

    public String getPlusCode() {
        return plusCode;
    }

    public void setPlusCode(String plusCode) {
        this.plusCode = plusCode;
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
