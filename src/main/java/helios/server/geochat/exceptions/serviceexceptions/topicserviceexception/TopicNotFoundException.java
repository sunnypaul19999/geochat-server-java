package helios.server.geochat.exceptions.serviceexceptions.topicserviceexception;

public class TopicNotFoundException extends TopicException {

    private static final String MSGFORMAT = "Topic with topic Id %d does not exits";

    public TopicNotFoundException(int topicId, String operation) {
        super(operation, String.format(MSGFORMAT, topicId));
    }
}
