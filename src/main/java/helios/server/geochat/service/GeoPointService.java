package helios.server.geochat.service;

import com.google.openlocationcode.OpenLocationCode;

import helios.server.geochat.dto.UserLocationDTO.UserLocationDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointDefaultRangeNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.geoPointServiceException.GeoPointException;

public interface GeoPointService {

    default String calcPlusCode(UserLocationDTO userLocationDTO) {
        OpenLocationCode openLocationCode = new OpenLocationCode(userLocationDTO.getLat(), userLocationDTO.getLon());

        return openLocationCode.getCode();
    }

    String registerGeoPoint(UserLocationDTO userLocationDTO) throws GeoPointException;

    public static double calDistanceGeoPoints(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;

            // converting to meters
            dist = dist * 1609.344;

            return dist;
        }
    }

    public String checkIfInRangeWithOtherGeoPoint(UserLocationDTO userLocationDTO)
            throws GeoPointDefaultRangeNotFoundException;
}
