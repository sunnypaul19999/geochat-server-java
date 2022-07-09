package helios.server.geochat.dto.response.topicResponse;

public class TopicDTOOnFetchTopicFailure extends TopicDTOResponse {
    private static final String MESSAGE = "OOPs! failed to fetch topics";

    public TopicDTOOnFetchTopicFailure() {
        super(true);
    }

    public static String getMessage() {
        return MESSAGE;
    }
}