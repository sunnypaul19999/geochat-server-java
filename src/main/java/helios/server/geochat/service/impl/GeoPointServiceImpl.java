package helios.server.geochat.service.impl;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import helios.server.geochat.dto.UserLocationDTO.UserLocationDTO;
import helios.server.geochat.model.GeoPoint;
import helios.server.geochat.repository.GeoPointRangeRepository;
import helios.server.geochat.repository.GeoPointRepository;
import helios.server.geochat.service.GeoPointService;

@Service
public class GeoPointServiceImpl implements GeoPointService {

    @Autowired
    GeoPointRepository geoPointRepository;

    @Autowired
    GeoPointRangeRepository geoPointRangeRepository;

    @Autowired
    GeoPointRangeServiceImpl geoPointRangeServiceImpl;

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
                String nearestInRangeGeoPointPlusCode = checkIfInRangeWithOtherGeoPoint(userLocationDTO);
                if (nearestInRangeGeoPointPlusCode.isEmpty()) {
                    GeoPoint geoPoint = new GeoPoint(calcPlusCode(userLocationDTO), userLocationDTO);
                    geoPointRepository.save(geoPoint);
                    Logger.getLogger(getClass().getName()).log(java.util.logging.Level.INFO,
                            "location already not-registered", userLocationDTO);
                } else {
                    Logger.getLogger(getClass().getName()).log(java.util.logging.Level.INFO,
                            "nearest location exits");
                }
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName())
                    .log(java.util.logging.Level.SEVERE, "error while saving geopoint", e);
        }

        return true;
    }

    @Override
    public String checkIfInRangeWithOtherGeoPoint(UserLocationDTO userLocationDTO) {
        List<GeoPoint> geoPointList = geoPointRepository.findAllByOrderByLatAsc();
        double defRange = geoPointRangeServiceImpl.getDefaultGeoPointRange().getRadius();
        String nearestInRangeGeoPointPlusCode = "";

        for (GeoPoint geoPoint : geoPointList) {
            double dis = Math.abs(
                    GeoPointService.calDistanceGeoPoints(
                            userLocationDTO.getLat(),
                            userLocationDTO.getLon(),
                            geoPoint.getLat(),
                            geoPoint.getLon()));
            if (!(dis > defRange)) {
                Logger.getLogger(getClass().getName()).info(
                        String.format("distance btw (%f,%f) <--> (%f,%f) = %f",
                                userLocationDTO.getLat(),
                                userLocationDTO.getLon(),
                                geoPoint.getLat(),
                                geoPoint.getLon(), dis));
                nearestInRangeGeoPointPlusCode = geoPoint.getPlusCode();
                break;
            }
        }

        return nearestInRangeGeoPointPlusCode;
    }
}
