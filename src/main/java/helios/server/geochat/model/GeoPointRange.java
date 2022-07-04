package helios.server.geochat.model;

import javax.persistence.*;

@Entity
@Table(name = "geopointrange")
public class GeoPointRange {

    @Id
    @Column(name = "radius", nullable = false)
    private int radius;

    public GeoPointRange(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
