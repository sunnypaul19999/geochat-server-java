package helios.server.geochat.security.authentication.provider;

import helios.server.geochat.security.authentication.JWTAuthentication;
import helios.server.geochat.service.impl.GeoSecurityUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class JWTAuthenticationProvider implements AuthenticationProvider {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final GeoSecurityUserServiceImpl geoSecurityUserServiceImpl;

  private final PasswordEncoder passwordEncoder;

  public JWTAuthenticationProvider(
      GeoSecurityUserServiceImpl geoSecurityUserServiceImpl, PasswordEncoder passwordEncoder) {

    this.geoSecurityUserServiceImpl = geoSecurityUserServiceImpl;

    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    logger.debug(String.format("JWT token = %s ", authentication.getCredentials()));
    
    UserDetails userDetails = geoSecurityUserServiceImpl.loadUserByUsername("username");
    
    JWTAuthentication jwtAuthentication = new JWTAuthentication(
            userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    
    //jwtAuthentication.setAuthenticated(true);
    
    return jwtAuthentication;
  }

  @Override
  public boolean supports(Class<?> authentication) {

    return authentication.equals(JWTAuthentication.class);
  }
}
