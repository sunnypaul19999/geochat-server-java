package helios.server.geochat.exceptions.securityException;

import org.springframework.security.core.AuthenticationException;

public class GeoUserAuthenticationException extends AuthenticationException {

    public GeoUserAuthenticationException(String msg) {
        super(msg);
    }

}