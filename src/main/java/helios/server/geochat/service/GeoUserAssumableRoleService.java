package helios.server.geochat.service;

import helios.server.geochat.model.GeoUserAssumableRole;

public interface GeoUserAssumableRoleService {

  GeoUserAssumableRole getRole(String roleType);

  GeoUserAssumableRole getRole(int roleId);
}
