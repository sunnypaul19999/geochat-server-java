package helios.server.geochat.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "geopoint")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoPoint {

    @Id
    @Column(name = "plus_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plusCode;

    @Column(name = "lattitude")
    private double lat;

    @Column(name = "longitude")
    private double lon;

    // hibernate use no-args constructor to create the object
    // use getters and setters of the fields to get and set values
    GeoPoint() {
    }

    public GeoPoint(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public int getPlusCode() {
        return plusCode;
    }

    public void setPlusCode(int plusCode) {
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
