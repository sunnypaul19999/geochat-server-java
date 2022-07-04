package helios.server.geochat.model;

import javax.persistence.*;

@Entity
@Table(name = "geopointrange")
public class GeoPointRange {

    @Id
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private int id;

    @Column(name = "radius", nullable = false)
    private int radius;

    public GeoPointRange() {
    }

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
