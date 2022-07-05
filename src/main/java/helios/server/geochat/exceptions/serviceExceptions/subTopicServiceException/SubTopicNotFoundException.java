package helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException;

public class SubTopicNotFoundException extends SubTopicException {

    private static final String MSGFORMAT = "SubTopic with topic Id %d does not exits";

    public SubTopicNotFoundException(int topicId, String operation) {
        super(operation, String.format(MSGFORMAT, topicId));
    }
}
