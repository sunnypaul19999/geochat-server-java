package helios.server.geochat.exceptions.serviceexceptions.subtopicserviceexception;

public class SubTopicPageNumberNotInRangeException extends SubTopicException {

  private static final String MESSAGE = "Page number not in range";

  public SubTopicPageNumberNotInRangeException() {
    super("SUB_TOPIC_BY_PAGE", MESSAGE);
  }
}
