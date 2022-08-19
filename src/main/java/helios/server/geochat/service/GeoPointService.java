package helios.server.geochat.service;

import com.google.openlocationcode.OpenLocationCode;
import helios.server.geochat.dto.request.UserLocationDTO;
import helios.server.geochat.exceptions.serviceexceptions.geopointserviceexception.GeoPointDefaultRangeNotFoundException;
import helios.server.geochat.exceptions.serviceexceptions.geopointserviceexception.GeoPointException;
import helios.server.geochat.exceptions.serviceexceptions.geopointserviceexception.GeoPointNotRegisteredException;
import helios.server.geochat.model.GeoPoint;

public interface GeoPointService {

  static String calcPlusCode(UserLocationDTO userLocationDTO) {
    OpenLocationCode openLocationCode =
        new OpenLocationCode(userLocationDTO.getLat(), userLocationDTO.getLon());

    return openLocationCode.getCode();
  }

  static double calDistanceGeoPoints(double lat1, double lon1, double lat2, double lon2) {
    if ((lat1 == lat2) && (lon1 == lon2)) {
      return 0;
    } else {
      double theta = lon1 - lon2;
      double dist =
          Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
              + Math.cos(Math.toRadians(lat1))
                  * Math.cos(Math.toRadians(lat2))
                  * Math.cos(Math.toRadians(theta));
      dist = Math.acos(dist);
      dist = Math.toDegrees(dist);
      dist = dist * 60 * 1.1515;

      // converting to meters
      dist = dist * 1609.344;

      return dist;
    }
  }

  /*
  @returns plusCode is present and empty string if geopoint not registered
   */
  String isGeoPointRegistered(UserLocationDTO userLocationDTO);

  GeoPoint isGeoPointRegistered(String plusCode) throws GeoPointNotRegisteredException;

  String registerGeoPoint(UserLocationDTO userLocationDTO) throws GeoPointException;

  public String checkIfInRangeWithOtherGeoPoint(UserLocationDTO userLocationDTO)
      throws GeoPointDefaultRangeNotFoundException;
}
