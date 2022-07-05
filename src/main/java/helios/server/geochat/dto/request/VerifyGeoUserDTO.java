package helios.server.geochat.dto.request;

import javax.validation.constraints.NotNull;


public class VerifyGeoUserDTO extends GeoUserDTO {
    @NotNull
    private String password;

    public VerifyGeoUserDTO(
            String username,
            @NotNull String password) {
        super(username);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
