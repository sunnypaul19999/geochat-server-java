package helios.server.geochat.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import helios.server.geochat.dto.UserLocationDTO.UserLocationDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointDefaultRangeNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointException;
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

    public String isGeoPointRegistered(UserLocationDTO userLocationDTO) {
        String plusCode = "";

        Optional<GeoPoint> gOptional = geoPointRepository
                .findById(calcPlusCode(userLocationDTO));

        if (gOptional.isPresent()) {
            plusCode = gOptional.get().getPlusCode();
        }

        return plusCode;
    }

    @Override
    public String registerGeoPoint(UserLocationDTO userLocationDTO) throws GeoPointException {
        try {
            // gets the pluscode from database if exits for the exact location in
            // userLocationDTO
            String plusCode = isGeoPointRegistered(userLocationDTO);

            // if geopoint is not registered already then save it
            if (plusCode.isEmpty()) {
                // gets the pluscode of nearest in range location from database if exits:
                // default range is 500
                plusCode = checkIfInRangeWithOtherGeoPoint(userLocationDTO);

                // if no in range geopoint exits creates on for the location
                if (plusCode.isEmpty()) {
                    GeoPoint geoPoint = new GeoPoint(calcPlusCode(userLocationDTO), userLocationDTO);

                    geoPoint = geoPointRepository.save(geoPoint);

                    // sets the pluscode for that location
                    plusCode = geoPoint.getPlusCode();

                    Logger.getLogger(getClass().getName()).log(
                            java.util.logging.Level.INFO,
                            "location already not-registered", userLocationDTO);
                } else {
                    Logger.getLogger(getClass().getName())
                            .log(java.util.logging.Level.INFO, "nearest location exits");
                }
            }

            return plusCode;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName())
                    .log(java.util.logging.Level.SEVERE, "error while saving geopoint", e);

            throw new GeoPointException("REGISTER", e.getMessage());
        }
    }

    @Override
    public String checkIfInRangeWithOtherGeoPoint(UserLocationDTO userLocationDTO)
            throws GeoPointDefaultRangeNotFoundException {
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

        // empty string is returned if not in range with another geopoint
        return nearestInRangeGeoPointPlusCode;
    }
}
