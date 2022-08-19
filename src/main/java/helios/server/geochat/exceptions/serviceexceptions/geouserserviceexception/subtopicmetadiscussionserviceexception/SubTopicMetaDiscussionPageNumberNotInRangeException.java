package helios.server.geochat.exceptions.serviceexceptions.geouserserviceexception.subtopicmetadiscussionserviceexception;

public class SubTopicMetaDiscussionPageNumberNotInRangeException
    extends SubTopicMetaDiscussionException {

  private static final String MESSAGE = "Page number not in range";

  public SubTopicMetaDiscussionPageNumberNotInRangeException() {
    super("SubTopicMetaDiscussion_BY_PAGE", MESSAGE);
  }
}
