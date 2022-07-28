package helios.server.geochat.model;

import javax.persistence.*;

import helios.server.geochat.dto.request.NewGeoUserDTO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "geouser")
public class GeoUser {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geouser_id_seq_generator")
  @SequenceGenerator(name = "geouser_id_seq_generator", initialValue = 1, allocationSize = 1)
  private int id;

  @Column(name = "username", length = 50, nullable = false)
  private String username;

  @Column(name = "password", length = 256, nullable = false)
  private String password;

  @Column(name = "jwt_token")
  private String jwtToken;

  //  select geouser_assumable_role.role_id, geouser_assumable_role.role_type
  //  from geouserrole //joint table
  //  right outer join geouser_assumable_role
  //  on geouser_assumable_role.role_id = geouserrole.role_id
  //  where geouserrole.user_id = 1
  @ManyToMany(fetch = FetchType.EAGER)
  // mapping: geouserrole(join table fk) to geouser
  // and geouserrole(join table fk) to geouser_assumable_role
  @JoinTable(
      name = "geouserrole",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<GeoUserAssumableRole> role;

  @OneToMany(mappedBy = "geoUser", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<SubTopicMetaDiscussion> subTopicMetaDiscussion;

  public GeoUser() {}

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

  public List<GeoUserAssumableRole> getRole() {
    return role;
  }

  public void setRole(List<GeoUserAssumableRole> role) {
    this.role = role;
  }

  public List<SubTopicMetaDiscussion> getSubTopicMetaDiscussion() {
    return subTopicMetaDiscussion;
  }

  public void setSubTopicMetaDiscussion(List<SubTopicMetaDiscussion> subTopicMetaDiscussion) {
    this.subTopicMetaDiscussion = subTopicMetaDiscussion;
  }

  public String getJwtToken() {

    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {

    this.jwtToken = jwtToken;
  }
}
