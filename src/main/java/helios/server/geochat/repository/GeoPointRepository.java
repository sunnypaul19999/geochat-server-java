package helios.server.geochat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import helios.server.geochat.model.GeoPoint;

public interface GeoPointRepository extends JpaRepository<GeoPoint, Integer> {

}
