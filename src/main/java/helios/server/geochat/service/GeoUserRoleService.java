package helios.server.geochat.service;

import helios.server.geochat.model.GeoUser;
import helios.server.geochat.model.GeoUserAssumableRole;

import java.util.List;

public interface GeoUserRoleService {

  void assignRole(GeoUser geoUser, GeoUserAssumableRole role);
}
