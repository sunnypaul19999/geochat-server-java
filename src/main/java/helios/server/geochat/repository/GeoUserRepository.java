package helios.server.geochat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import helios.server.geochat.model.GeoUser;

public interface GeoUserRepository extends JpaRepository<GeoUser, Integer> {

    Optional<GeoUser> findByUsername(String username);
}
