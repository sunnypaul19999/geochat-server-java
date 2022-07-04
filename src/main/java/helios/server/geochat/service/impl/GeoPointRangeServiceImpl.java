package helios.server.geochat.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import helios.server.geochat.model.GeoPointRange;
import helios.server.geochat.repository.GeoPointRangeRepository;
import helios.server.geochat.service.GeoPointRangeService;

@Service
public class GeoPointRangeServiceImpl implements GeoPointRangeService {

    @Autowired
    GeoPointRangeRepository geoPointRangeRepository;

    @Override
    public GeoPointRange getDefaultGeoPointRange() {
        Optional<GeoPointRange> geoPointRange = geoPointRangeRepository.findById(1);
        if (geoPointRange.isPresent()) {
            return geoPointRange.get();
        }

        // this should not happen there should always be a default value range at row 1
        return null;
    }

}
