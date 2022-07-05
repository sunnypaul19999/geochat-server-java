package helios.server.geochat.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import helios.server.geochat.dto.request.GeoUserDTO;
import helios.server.geochat.dto.request.NewGeoUserDTO;
import helios.server.geochat.dto.request.VerifyGeoUserDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserConfirmPasswordMismatchException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserExitsException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserNotFoundException;
import helios.server.geochat.model.GeoUser;
import helios.server.geochat.repository.GeoUserRepository;
import helios.server.geochat.service.GeoUserService;

@Service
public class GeoUserServiceImpl implements GeoUserService {

    @Autowired
    GeoUserRepository geoUserRepository;

    @Override
    public boolean verifyUser(VerifyGeoUserDTO geoUserDTO) throws GeoUserException {
        try {
            GeoUser geoUser = getUser(geoUserDTO);
            if (geoUser.getPassword().equals(geoUserDTO.getPassword())) {
                return true;
            }

            return false;
        } catch (GeoUserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeoUserException("VERIFY_USER", e.getMessage());
        }
    }

    @Override
    public GeoUser getUser(GeoUserDTO geoUserDTO) throws GeoUserNotFoundException {
        Optional<GeoUser> geoUser = geoUserRepository.findByUsername(geoUserDTO.getUsername());

        if (geoUser.isPresent()) {
            return geoUser.get();
        }

        throw new GeoUserNotFoundException(geoUserDTO.getUsername(), "GET_USER");
    }

    @Override
    public void addUser(NewGeoUserDTO newGeoUserDTO) throws GeoUserException {
        final String operation = "ADD_USER";

        try {
            if (!(newGeoUserDTO.getPassword().equals(newGeoUserDTO.getConfirmPassword()))) {
                throw new GeoUserConfirmPasswordMismatchException(operation);
            }

            getUser(newGeoUserDTO);

            throw new GeoUserExitsException(newGeoUserDTO.getUsername(), operation);

        } catch (GeoUserNotFoundException e) {
            GeoUser geoUser = new GeoUser(newGeoUserDTO);

            geoUserRepository.save(geoUser);
        } catch (GeoUserConfirmPasswordMismatchException | GeoUserExitsException e) {
            throw e;
        } catch (Exception e) {
            throw new GeoUserException(operation, e.getMessage());
        }
    }

    @Override
    public void deleteUser(GeoUserDTO geoUserDTO) throws GeoUserException {
        try {
            GeoUser geoUser = getUser(geoUserDTO);
            geoUserRepository.delete(geoUser);
        } catch (GeoUserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeoUserException("DELETE_OPERATION", e.getMessage());
        }
    }
}
