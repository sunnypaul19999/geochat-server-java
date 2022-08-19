package helios.server.geochat.exceptions.serviceexceptions.topicserviceexception;

public class TopicPageNumberNotInRangeException extends TopicException {

  private static final String MESSAGE = "Page number not in range";

  public TopicPageNumberNotInRangeException() {
    super("TOPIC_BY_PAGE", MESSAGE);
  }
}
