package helios.server.geochat.exceptions.serviceExceptions.subTopicMetaDiscussionServiceException;

public class SubTopicMetaDiscussionOnDeleteException extends SubTopicMetaDiscussionException {

    private static final String MSGFORMAT = "SubTopicMetaDiscussion Message with message Id %d does not exits";

    public SubTopicMetaDiscussionOnDeleteException(int topicId, String operation) {
        super(operation, String.format(MSGFORMAT, topicId));
    }
}
