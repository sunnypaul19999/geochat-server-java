package helios.server.geochat.model;

import org.hibernate.annotations.GeneratorType;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "geouser_assumable_role")
public class GeoUserAssumableRole implements GrantedAuthority {

  @Id
  @Column(name = "role_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "role_type", nullable = false)
  private String roleType;

  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private List<GeoUserRole> geoUserRoles;

  public GeoUserAssumableRole() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getRoleType() {
    return roleType;
  }

  public void setRoleType(String roleType) {
    this.roleType = roleType;
  }

  public List<GeoUserRole> getGeoUserRoles() {
    return geoUserRoles;
  }

  public void setGeoUserRoles(List<GeoUserRole> geoUserRoles) {
    this.geoUserRoles = geoUserRoles;
  }

  @Override
  public String getAuthority() {
    return getRoleType();
  }
}
