package helios.server.geochat.dto.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class GeoUserDTO {

    @NotNull
    @Length(min = 5, max = 50)
    private String username;

    public GeoUserDTO(@NotNull @Length(min = 5, max = 50) String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
