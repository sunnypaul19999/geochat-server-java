package helios.server.geochat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import helios.server.geochat.model.GeoPoint;

public interface GeoPointRepository extends JpaRepository<GeoPoint, String> {

    List<GeoPoint> findAllByOrderByLatAsc();
}
