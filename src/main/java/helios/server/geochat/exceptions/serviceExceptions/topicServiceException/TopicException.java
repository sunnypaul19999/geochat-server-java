package helios.server.geochat.exceptions.serviceExceptions.topicServiceException;

public class TopicException extends Exception {

    private static final String MSGFORMAT = "Failure to perform operation %s as %s";

    public TopicException(String operation, String message) {
        super(String.format(MSGFORMAT, operation.toUpperCase(), message));
    }
}
