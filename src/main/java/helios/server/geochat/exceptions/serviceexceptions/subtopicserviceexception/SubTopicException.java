package helios.server.geochat.exceptions.serviceexceptions.subtopicserviceexception;

public class SubTopicException extends Exception {

    private static final String MSGFORMAT = "Failure to perform operation %s as %s";

    public SubTopicException(String operation, String message) {
        super(String.format(MSGFORMAT, operation.toUpperCase(), message));
    }
}
