package helios.server.geochat.repository;

import helios.server.geochat.model.GeoUserAssumableRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoUserRoleRepository extends JpaRepository<GeoUserAssumableRole, Integer> {}
