package helios.server.geochat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import helios.server.geochat.dto.UserLocationDTO;
import helios.server.geochat.model.GeoPoint;
import helios.server.geochat.repository.GeoPointRepository;
import helios.server.geochat.service.GeoPointService;

@Service
public class GeoPointServiceImpl implements GeoPointService {

    @Autowired
    GeoPointRepository geoPointRepository;

    @Override
    public boolean registerGeoPoint(UserLocationDTO userLocationDTO) {
        GeoPoint geoPoint = new GeoPoint(userLocationDTO);
        geoPointRepository.save(geoPoint);
        return false;
    }

}
