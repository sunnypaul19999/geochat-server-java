package helios.server.geochat.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import helios.server.geochat.model.GeoUser;

public class GeoSecurityUserDetails implements UserDetails {

    @NotNull
    private final transient GeoUser geoUser;

    public GeoSecurityUserDetails(GeoUser geoUser) {
        this.geoUser = geoUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new GeoSecurityUserGrantedAuthoriy("USER"));
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
