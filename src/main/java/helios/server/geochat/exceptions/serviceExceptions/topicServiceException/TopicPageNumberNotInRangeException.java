package helios.server.geochat.exceptions.serviceExceptions.topicServiceException;

import helios.server.geochat.model.Topic;

public class TopicPageNumberNotInRangeException extends TopicException {

  private static final String MESSAGE = "Page number not in range";

  public TopicPageNumberNotInRangeException() {
    super("TOPIC_BY_PAGE", MESSAGE);
  }
}
