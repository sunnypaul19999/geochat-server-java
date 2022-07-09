package helios.server.geochat.dto.response.subTopicResponse;

public class SubTopicDTOOnFetchTopicFailure extends SubTopicDTOResponse {

    private static final String MESSAGE = "OOPs! failed to fetch topics";

    public SubTopicDTOOnFetchTopicFailure() {
        super(true);
    }

    public static String getMessage() {
        return MESSAGE;
    }
}