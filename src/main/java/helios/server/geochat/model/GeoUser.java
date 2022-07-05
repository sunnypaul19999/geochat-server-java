package helios.server.geochat.model;

import javax.persistence.*;

import helios.server.geochat.dto.request.NewGeoUserDTO;

@Entity
@Table(name = "geouser")
public class GeoUser {

    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geo_user_id_seqgenerator")
    @SequenceGenerator(name = "geo_user_id_seqgenerator", allocationSize = 1)
    private int id;

    @Column(name = "message", length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 256, nullable = false)
    private String password;

    public GeoUser() {
    }

    public GeoUser(NewGeoUserDTO newGeoUserDTO) {
        this.username = newGeoUserDTO.getUsername();
        this.password = newGeoUserDTO.getPassword();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
