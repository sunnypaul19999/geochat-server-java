package helios.server.geochat.exceptions.dtoexception;

public class InvalidRequestFormatException extends Exception {

  private static final String MESSAGE = "Invalid request format";

  public String getMessage() {

    return MESSAGE;
  }
}
