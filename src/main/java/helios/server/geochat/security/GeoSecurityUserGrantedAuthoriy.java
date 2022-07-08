package helios.server.geochat.security;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;

class GeoSecurityUserGrantedAuthoriy implements GrantedAuthority {

    @NotNull
    private final String authority;

    GeoSecurityUserGrantedAuthoriy(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {

        return this.authority;
    }

}