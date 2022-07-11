package helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException;

public class SubTopicPageNumberNotInRangeException extends SubTopicException {

  private static final String MESSAGE = "Page number not in range";

  public SubTopicPageNumberNotInRangeException() {
    super("SUB_TOPIC_BY_PAGE", MESSAGE);
  }
}
