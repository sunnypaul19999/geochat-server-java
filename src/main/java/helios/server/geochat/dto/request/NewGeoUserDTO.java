package helios.server.geochat.dto.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class NewGeoUserDTO extends VerifyGeoUserDTO {

    @NotNull
    private String confirmPassword;

    public NewGeoUserDTO(@NotNull @Length(min = 5, max = 50) String username, @NotNull String password,
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
