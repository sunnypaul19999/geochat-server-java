package helios.server.geochat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import helios.server.geochat.service.impl.GeoSecurityUserServiceImpl;

class AuthEx extends AuthenticationException {

    public AuthEx(String msg) {
        super(msg);
    }

}

public class GeoSecurityUserAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger;

    private final GeoSecurityUserServiceImpl geoSecurityUserServiceImpl;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public GeoSecurityUserAuthenticationProvider(GeoSecurityUserServiceImpl geoSecurityUserServiceImpl,
            PasswordEncoder passwordEncoder) {

        this.geoSecurityUserServiceImpl = geoSecurityUserServiceImpl;

        this.passwordEncoder = passwordEncoder;

        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {

            logger.info(
                    String.format("+++++ auth for username = %s, password = %s, authorites = %s ++++++",
                            authentication.getPrincipal(),
                            authentication.getCredentials(),
                            authentication.getAuthorities()));

            UserDetails userDetails = geoSecurityUserServiceImpl
                    .loadUserByUsername((String) authentication.getPrincipal());

            if (passwordEncoder.matches((String) authentication.getCredentials(), userDetails.getPassword())) {
                return UsernamePasswordAuthenticationToken.authenticated(
                        userDetails.getUsername(),
                        userDetails.getPassword(),
                        userDetails.getAuthorities());
            }

            return UsernamePasswordAuthenticationToken.unauthenticated(
                    userDetails.getUsername(),
                    userDetails.getPassword());

        } catch (UsernameNotFoundException e) {

            throw new AuthEx("You are not user of geochat I cant let you in!!!!!");

        } catch (Exception e) {

            logger.error("Unknown exception occured while authenticating user", e);

            throw new AuthEx("Something happend! we have no idea");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        logger.info(
                String.format(
                        "** GeoUserAuthenticationProvider found = %s %s**",
                        authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class),
                        authentication.toGenericString()));

        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }

}
