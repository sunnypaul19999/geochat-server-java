package helios.server.geochat.model;

import java.util.List;

import javax.persistence.*;

import helios.server.geochat.dto.request.UserLocationDTO;

@Entity
@Table(name = "geopoint")
public class GeoPoint {

    @Id
    @Column(name = "plus_code", updatable = false, nullable = false)
    private String plusCode;

    @Column(name = "lattitude", precision = 16, scale = 14, updatable = false, nullable = false)
    private double lat;

    @Column(name = "longitude", precision = 18, scale = 15, updatable = false, nullable = false)
    private double lon;

    @OneToMany(mappedBy = "geoPoint", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    List<Topic> topic;

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

    public List<Topic> getTopic() {
        return topic;
    }

    public void setTopic(List<Topic> topic) {
        this.topic = topic;
    }
}
