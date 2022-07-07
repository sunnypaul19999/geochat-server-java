package helios.server.geochat.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import helios.server.geochat.model.GeoUser;

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

public class GeoSecurityUserDetails implements UserDetails {

    @NotNull
    private final transient GeoUser geoUser;

    public GeoSecurityUserDetails(GeoUser geoUser) {
        this.geoUser = geoUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        ArrayList<GeoSecurityUserGrantedAuthoriy> authorities = new ArrayList<>();

        authorities.add(new GeoSecurityUserGrantedAuthoriy("USER"));

        return authorities;
    }

    @Override
    public String getPassword() {
        return geoUser.getPassword();
    }

    @Override
    public String getUsername() {
        return geoUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {

        return false;
    }

    @Override
    public boolean isAccountNonLocked() {

        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return false;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
