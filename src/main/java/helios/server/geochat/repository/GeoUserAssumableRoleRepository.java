package helios.server.geochat.repository;

import helios.server.geochat.model.GeoUserAssumableRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeoUserAssumableRoleRepository
    extends JpaRepository<GeoUserAssumableRole, Integer> {

  Optional<GeoUserAssumableRole> findByRoleType(String roleType);
}
