package helios.server.geochat.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import helios.server.geochat.model.GeoUserAssumableRole;
import helios.server.geochat.service.GeoUserAssumableRoleService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
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
import helios.server.geochat.security.GeoSecurityUserDetails;
import helios.server.geochat.service.GeoUserService;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserConfirmPasswordMismatchException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserExistsException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserNotFoundException;

@Service
public class GeoSecurityUserServiceImpl implements GeoUserService, UserDetailsService {

  private final GeoUserRepository geoUserRepository;

  private final GeoUserAssumableRoleService geoUserAssumableRoleService;

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  public GeoSecurityUserServiceImpl(
      GeoUserRepository geoUserRepository,
      GeoUserAssumableRoleService geoUserAssumableRoleService) {
    this.geoUserRepository = geoUserRepository;
    this.geoUserAssumableRoleService = geoUserAssumableRoleService;
  }

  @Override
  public Optional<String> verifyUser(VerifyGeoUserDTO geoUserDTO) throws GeoUserException {
    try {
      GeoUser geoUser = getUser(geoUserDTO);

      if (geoUser.getPassword().equals(geoUserDTO.getPassword())) {

        geoUser.setJwtToken("my token is good token");

        geoUserRepository.save(geoUser);

        geoUserRepository.flush();

        logger.error(getJWTToken(geoUser));

        return Optional.of(geoUser.getJwtToken());
      }

      return Optional.empty();

    } catch (GeoUserNotFoundException e) {

      throw e;

    } catch (Exception e) {

      throw new GeoUserException("VERIFY_USER", e.getMessage());
    }
  }

  public Optional<String> setJwtToken(GeoUserDTO geoUserDTO) throws GeoUserException {
    try {
      GeoUser geoUser = getUser(geoUserDTO);

      geoUser.setJwtToken(getJWTToken(geoUser));

      geoUserRepository.save(geoUser);

      logger.error(getJWTToken(geoUser));

      return Optional.of(geoUser.getJwtToken());

    } catch (GeoUserNotFoundException e) {

      throw e;

    } catch (Exception e) {

      throw new GeoUserException("VERIFY_USER", e.getMessage());
    }
  }

  public String getJWTToken(GeoUser geoUser) {

    final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    return Jwts.builder()
        .setIssuer("geo-chat")
        .setSubject("jwt-token")
        .claim("username", geoUser.getUsername())
        .setIssuedAt(timestamp)
        .setExpiration(new Date(timestamp.getTime() + Duration.ofDays(14).toMillis()))
        .signWith(SignatureAlgorithm.HS256, "TjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8".getBytes())
        .compact();
  }

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // (1)
    try {

      GeoUser geoUser = getUser(new GeoUserDTO(username));

      return new GeoSecurityUserDetails(geoUser);

    } catch (GeoUserNotFoundException e) {

      LoggerFactory.getLogger(getClass())
          .trace(String.format("GeoUserNotFoundException %s", username));

      throw new UsernameNotFoundException("You are not user of geochat I cant let you in!!!!!");
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

      throw new GeoUserExistsException(newGeoUserDTO.getUsername(), operation);

    } catch (GeoUserNotFoundException e) {
      GeoUser geoUser = new GeoUser(newGeoUserDTO);

      // do later, check null
      GeoUserAssumableRole role = geoUserAssumableRoleService.getRole("USER");

      geoUser.setRole(List.of(role));

      geoUserRepository.save(geoUser);

    } catch (GeoUserConfirmPasswordMismatchException | GeoUserExistsException e) {
      throw e;
    } catch (Exception e) {
      throw new GeoUserException(operation, e.getMessage());
    }
  }

  @Override
  public void addUserAsAdmin(NewGeoUserDTO newGeoUserDTO) throws GeoUserException {}

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
