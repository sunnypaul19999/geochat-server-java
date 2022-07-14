package helios.server.geochat.service.impl;

import helios.server.geochat.model.GeoUserAssumableRole;
import helios.server.geochat.repository.GeoUserAssumableRoleRepository;
import helios.server.geochat.service.GeoUserAssumableRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeoUserAssumableRoleServiceImpl implements GeoUserAssumableRoleService {

  private final GeoUserAssumableRoleRepository geoUserAssumableRoleRepository;

  @Autowired
  public GeoUserAssumableRoleServiceImpl(
      GeoUserAssumableRoleRepository geoUserAssumableRoleRepository) {
    this.geoUserAssumableRoleRepository = geoUserAssumableRoleRepository;
  }

  @Override
  public GeoUserAssumableRole getRole(String roleType) {

    Optional<GeoUserAssumableRole> geoUserAssumableRole =
        geoUserAssumableRoleRepository.findByRoleType(roleType);

    if (geoUserAssumableRole.isPresent()) {

      return geoUserAssumableRole.get();
    }

    return null;
  }

  @Override
  public GeoUserAssumableRole getRole(int roleId) {
    Optional<GeoUserAssumableRole> geoUserAssumableRole =
        geoUserAssumableRoleRepository.findById(roleId);

    if (geoUserAssumableRole.isPresent()) {

      return geoUserAssumableRole.get();
    }

    return null;
  }
}
