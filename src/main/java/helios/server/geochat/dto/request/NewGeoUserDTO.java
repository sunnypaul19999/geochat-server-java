package helios.server.geochat.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewGeoUserDTO extends VerifyGeoUserDTO {

  private String confirmPassword;

  public NewGeoUserDTO(@NotNull String username, @NotNull String password, String confirmPassword) {
    super(username, password);
    this.confirmPassword = confirmPassword;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  @Override
  public String toString() {
    return "NewGeoUserDTO [username=" + getUsername() + "]";
  }
}
