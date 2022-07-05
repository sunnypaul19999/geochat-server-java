package helios.server.geochat.dto.response.subTopicResponse;

public class SubTopicDTOOnAddFailureResponse extends SubTopicDTOResponse {

    private static final String MESSAGE = "OOPS! SubTopic could not added";

    public SubTopicDTOOnAddFailureResponse() {
        super(false, null);
    }

    public String getMSG() {
        return MESSAGE;
    }
}
