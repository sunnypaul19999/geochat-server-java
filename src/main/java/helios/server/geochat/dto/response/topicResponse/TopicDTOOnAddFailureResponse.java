package helios.server.geochat.dto.response.topicResponse;

public class TopicDTOOnAddFailureResponse extends TopicDTOResponse {

    private static final String MESSAGE = "OOPS! Topic could not added";

    public TopicDTOOnAddFailureResponse() {
        super(false);
    }

    public String getMSG() {
        return MESSAGE;
    }
}
