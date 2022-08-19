package helios.server.geochat.exceptions.securityexception;

import org.springframework.security.core.AuthenticationException;

public class GeoUserAuthenticationException extends AuthenticationException {

    public GeoUserAuthenticationException(String msg) {
        super(msg);
    }

}