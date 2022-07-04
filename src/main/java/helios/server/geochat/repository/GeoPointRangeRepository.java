package helios.server.geochat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import helios.server.geochat.model.GeoPointRange;

public interface GeoPointRangeRepository extends JpaRepository<GeoPointRange, Integer> {

}
