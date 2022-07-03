package helios.server.geochat.service;

import com.google.openlocationcode.OpenLocationCode;

import helios.server.geochat.dto.UserLocationDTO.UserLocationDTO;

public interface GeoPointService {

    default String calcPlusCode(UserLocationDTO userLocationDTO) {
        OpenLocationCode openLocationCode = new OpenLocationCode(userLocationDTO.getLat(), userLocationDTO.getLon());

        return openLocationCode.getCode();
    }

    boolean registerGeoPoint(UserLocationDTO userLocationDTO);
}
