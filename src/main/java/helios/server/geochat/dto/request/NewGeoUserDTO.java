package helios.server.geochat.dto.request;

import javax.validation.constraints.NotNull;

public class NewGeoUserDTO extends VerifyGeoUserDTO {

    @NotNull
    private String confirmPassword;

    public NewGeoUserDTO(
            @NotNull String username,
            @NotNull String password,
            @NotNull String confirmPassword) {
        super(username, password);
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
