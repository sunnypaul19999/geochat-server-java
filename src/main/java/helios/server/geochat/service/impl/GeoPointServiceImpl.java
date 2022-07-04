package helios.server.geochat.service.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import helios.server.geochat.dto.UserLocationDTO.UserLocationDTO;
import helios.server.geochat.model.GeoPoint;
import helios.server.geochat.model.GeoPointRange;
import helios.server.geochat.repository.GeoPointRangeRepository;
import helios.server.geochat.repository.GeoPointRepository;
import helios.server.geochat.service.GeoPointService;

@Service
public class GeoPointServiceImpl implements GeoPointService {

    @Autowired
    GeoPointRepository geoPointRepository;

    @Autowired
    GeoPointRangeRepository geoPointRangeRepository;

    public boolean isGeoPointRegistered(UserLocationDTO userLocationDTO) {
        return geoPointRepository
                .findById(calcPlusCode(userLocationDTO))
                .isPresent();
    }

    @Override
    public boolean registerGeoPoint(UserLocationDTO userLocationDTO) {
        try {
            // if geopoint is not registered then save it with the geopointrange radius
            if (!isGeoPointRegistered(userLocationDTO)) {
                GeoPoint geoPoint = new GeoPoint(calcPlusCode(userLocationDTO), userLocationDTO);
                geoPoint.setGeoPointRange(new GeoPointRange(userLocationDTO.getRadius()));
                geoPointRepository.save(geoPoint);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName())
                    .log(java.util.logging.Level.SEVERE, "error while saving geopoint", e);
        }

        return true;
    }

    @Override
    public boolean checkIfInRangeWithOtherGeoPoint() {

        return false;
    }
}
