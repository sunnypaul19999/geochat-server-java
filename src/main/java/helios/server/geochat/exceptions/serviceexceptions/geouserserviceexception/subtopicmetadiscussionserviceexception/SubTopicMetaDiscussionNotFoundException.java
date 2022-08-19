package helios.server.geochat.exceptions.serviceexceptions.geouserserviceexception.subtopicmetadiscussionserviceexception;

public class SubTopicMetaDiscussionNotFoundException extends SubTopicMetaDiscussionException {

    private static final String MSGFORMAT = "SubTopicMetaDiscussion Message with message Id %d does not exits";

    public SubTopicMetaDiscussionNotFoundException(int messageId, String operation) {
        super(operation, String.format(MSGFORMAT, messageId));
    }
}
