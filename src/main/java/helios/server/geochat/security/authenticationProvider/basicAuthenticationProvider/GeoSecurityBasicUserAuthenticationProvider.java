package helios.server.geochat.security.authenticationProvider.basicAuthenticationProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import helios.server.geochat.exceptions.securityException.GeoUserAuthenticationException;
import helios.server.geochat.service.impl.GeoSecurityUserServiceImpl;

public class GeoSecurityBasicUserAuthenticationProvider implements AuthenticationProvider {

  private final Logger logger;

  private final GeoSecurityUserServiceImpl geoSecurityUserServiceImpl;

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public GeoSecurityBasicUserAuthenticationProvider(
      GeoSecurityUserServiceImpl geoSecurityUserServiceImpl, PasswordEncoder passwordEncoder) {

    this.geoSecurityUserServiceImpl = geoSecurityUserServiceImpl;

    this.passwordEncoder = passwordEncoder;

    this.logger = LoggerFactory.getLogger(getClass());
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    try {

      logger.info(
          String.format(
              "+++++ auth for username = %s, password = %s, authorities = %s ++++++",
              authentication.getPrincipal(),
              authentication.getCredentials(),
              authentication.getAuthorities()));

      UserDetails userDetails =
          geoSecurityUserServiceImpl.loadUserByUsername((String) authentication.getPrincipal());

      if (passwordEncoder.matches(
          (String) authentication.getCredentials(), userDetails.getPassword())) {

        StringBuilder authString = new StringBuilder();
        for (GrantedAuthority auth : userDetails.getAuthorities()) {
          authString = authString.append(auth.getAuthority());
          authString.append(" ");
        }

        logger.info(
            String.format(
                "********* Username = %s with authorities = %s *************",
                userDetails.getUsername(), authString));

        return UsernamePasswordAuthenticationToken.authenticated(
            userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
      }

      throw new BadCredentialsException("User sent invalid credential");

    } catch (UsernameNotFoundException e) {

      throw e;

    } catch (Exception e) {

      logger.error("Unknown exception occurred while authenticating user", e);

      throw new GeoUserAuthenticationException("Something happened! we have no idea");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    logger.info(
        String.format(
            "** GeoUserAuthenticationProvider found = %s %s**",
            authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class),
            authentication.toGenericString()));

    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
