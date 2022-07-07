package helios.server.geochat.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import helios.server.geochat.model.GeoUser;

public class GeoUserDetails implements UserDetails, GrantedAuthority {

    @NotNull
    GeoUser geoUser;

    public GeoUserDetails(GeoUser geoUser) {
        this.geoUser = geoUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        ArrayList<GeoUserDetails> authorities = new ArrayList<>();

        authorities.add(this);

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

    @Override
    public String getAuthority() {

        return "USER";
    }
}
