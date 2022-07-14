package helios.server.geochat.model;

import javax.persistence.*;

@Entity
@Table(name = "geouserrole")
public class GeoUserRole {

  @Id
  @Column(name = "user_role_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private GeoUser geoUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", nullable = false)
  private GeoUserAssumableRole role;

  public GeoUserRole() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public GeoUser getGeoUser() {
    return geoUser;
  }

  public void setGeoUser(GeoUser geoUser) {
    this.geoUser = geoUser;
  }

  public GeoUserAssumableRole getRole() {
    return role;
  }

  public void setRole(GeoUserAssumableRole role) {
    this.role = role;
  }
}
