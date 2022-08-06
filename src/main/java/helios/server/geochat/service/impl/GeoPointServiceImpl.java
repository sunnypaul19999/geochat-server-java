package helios.server.geochat.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointNotRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointDefaultRangeNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointException;
import helios.server.geochat.model.GeoPoint;
import helios.server.geochat.repository.GeoPointRangeRepository;
import helios.server.geochat.repository.GeoPointRepository;
import helios.server.geochat.service.GeoPointService;

@Service
public class GeoPointServiceImpl implements GeoPointService {

  @Autowired GeoPointRepository geoPointRepository;

  @Autowired GeoPointRangeServiceImpl geoPointRangeServiceImpl;

  @Override
  public GeoPoint isGeoPointRegistered(String plusCode) throws GeoPointNotRegisteredException {

    Optional<GeoPoint> geoPoint = geoPointRepository.findById(plusCode);

    if (geoPoint.isPresent()) {
      return geoPoint.get();
    }

    throw new GeoPointNotRegisteredException(plusCode);
  }

  @Override
  public String isGeoPointRegistered(UserLocationDTO userLocationDTO) {
    String plusCode = "";

    Optional<GeoPoint> geoPoint = geoPointRepository.findById(GeoPointService.calcPlusCode(userLocationDTO));

    if (geoPoint.isPresent()) {
      plusCode = geoPoint.get().getPlusCode();
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
        // gets the plusCode of nearest in range location from database if exits:
        // default range is 500
        plusCode = checkIfInRangeWithOtherGeoPoint(userLocationDTO);

        // if no geopoint in range exits creates one for the location
        if (plusCode.isEmpty()) {
          GeoPoint geoPoint = new GeoPoint(GeoPointService.calcPlusCode(userLocationDTO), userLocationDTO);

          geoPoint = geoPointRepository.save(geoPoint);

          // sets the plusCode for that location
          plusCode = geoPoint.getPlusCode();

          Logger.getLogger(getClass().getName())
              .log(
                  java.util.logging.Level.INFO, "location already not-registered", userLocationDTO);
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
      double dis =
          Math.abs(
              GeoPointService.calDistanceGeoPoints(
                  userLocationDTO.getLat(),
                  userLocationDTO.getLon(),
                  geoPoint.getLat(),
                  geoPoint.getLon()));
      if (!(dis > defRange)) {
        Logger.getLogger(getClass().getName())
            .info(
                String.format(
                    "distance btw (%f,%f) <--> (%f,%f) = %f",
                    userLocationDTO.getLat(),
                    userLocationDTO.getLon(),
                    geoPoint.getLat(),
                    geoPoint.getLon(),
                    dis));
        nearestInRangeGeoPointPlusCode = geoPoint.getPlusCode();
        break;
      }
    }

    // empty string is returned if not in range with another geopoint
    return nearestInRangeGeoPointPlusCode;
  }
}
