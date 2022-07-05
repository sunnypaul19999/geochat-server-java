package helios.server.geochat.exceptions.serviceExceptions.subTopicMetaDiscussionServiceException;

public class SubTopicMetaDiscussionNotFoundException extends SubTopicMetaDiscussionException {

    private static final String MSGFORMAT = "SubTopicMetaDiscussion Message with message Id %d does not exits";

    public SubTopicMetaDiscussionNotFoundException(int topicId, String operation) {
        super(operation, String.format(MSGFORMAT, topicId));
    }
}
