package helios.server.geochat.exceptions.serviceexceptions.geouserserviceexception.subtopicmetadiscussionserviceexception;

public class SubTopicMetaDiscussionException extends Exception {

    private static final String MSGFORMAT = "Failure to perform operation %s as %s";

    public SubTopicMetaDiscussionException(String operation, String message) {
        super(String.format(MSGFORMAT, operation.toUpperCase(), message));
    }
}
