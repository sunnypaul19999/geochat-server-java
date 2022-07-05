package helios.server.geochat.dto.response.topicResponse;

import javax.validation.constraints.NotNull;

public class TopicDTOOnAddFailureResponse extends TopicDTOResponse {

    private static final String MESSAGE = "OOPS! topic could not added";

    public TopicDTOOnAddFailureResponse(@NotNull int id) {
        super(false, id);
    }

    public String getMSG() {
        return MESSAGE;
    }
}
