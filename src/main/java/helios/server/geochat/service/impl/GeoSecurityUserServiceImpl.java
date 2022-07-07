package helios.server.geochat.service.impl;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import helios.server.geochat.dto.request.GeoUserDTO;
import helios.server.geochat.dto.request.NewGeoUserDTO;
import helios.server.geochat.dto.request.VerifyGeoUserDTO;

import helios.server.geochat.model.GeoUser;

import helios.server.geochat.repository.GeoUserRepository;

import helios.server.geochat.service.GeoSecurityUserDetails;
import helios.server.geochat.service.GeoUserService;

import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserConfirmPasswordMismatchException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserExistsException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserNotFoundException;

@Service
public class GeoSecurityUserServiceImpl implements GeoUserService, UserDetailsService {

    @Autowired
    GeoUserRepository geoUserRepository;

    // @Override
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

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // (1)
        // 1. Load the user from the users table by username. If not found, throw
        // UsernameNotFoundException.
        // 2. Convert/wrap the user to a UserDetails object and return it.
        LoggerFactory.getLogger(getClass()).trace("*****************************************loadUserByUsername");
        try {

            GeoUser geoUser = getUser(new GeoUserDTO(username));

            return new GeoSecurityUserDetails(geoUser);

        } catch (GeoUserNotFoundException e) {
            LoggerFactory.getLogger(getClass()).trace("GeoUserNotFoundException");
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    // @Override
    public GeoUser getUser(GeoUserDTO geoUserDTO) throws GeoUserNotFoundException {
        Optional<GeoUser> geoUser = geoUserRepository.findByUsername(geoUserDTO.getUsername());

        if (geoUser.isPresent()) {
            return geoUser.get();
        }

        throw new GeoUserNotFoundException(geoUserDTO.getUsername(), "GET_USER");
    }

    // @Override
    public void addUser(NewGeoUserDTO newGeoUserDTO) throws GeoUserException {
        final String operation = "ADD_USER";

        try {
            if (!(newGeoUserDTO.getPassword().equals(newGeoUserDTO.getConfirmPassword()))) {
                throw new GeoUserConfirmPasswordMismatchException(operation);
            }

            getUser(newGeoUserDTO);

            throw new GeoUserExistsException(newGeoUserDTO.getUsername(), operation);

        } catch (GeoUserNotFoundException e) {
            GeoUser geoUser = new GeoUser(newGeoUserDTO);

            geoUserRepository.save(geoUser);
        } catch (GeoUserConfirmPasswordMismatchException | GeoUserExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new GeoUserException(operation, e.getMessage());
        }
    }

    // @Override
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
