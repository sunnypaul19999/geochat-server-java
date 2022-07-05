package helios.server.geochat.dto.response.topicResponse;

public class TopicDTOOnAddFailureResponse extends TopicDTOResponse {

    private static final String MESSAGE = "OOPS! Topic could not added";

    public TopicDTOOnAddFailureResponse() {
        super(false, null);
    }

    public String getMSG() {
        return MESSAGE;
    }
}
