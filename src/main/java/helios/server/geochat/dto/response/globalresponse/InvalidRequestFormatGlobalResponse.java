package helios.server.geochat.dto.response.globalresponse;

public class InvalidRequestFormatGlobalResponse {

  private static final boolean has_data_binding_error = true;

  public static final String MESSAGE = "Request has data binding error!";

  public boolean isHas_data_binding_error() {
    return has_data_binding_error;
  }

  public String getMESSAGE() {
    return MESSAGE;
  }
}
