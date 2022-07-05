package helios.server.geochat.dto.response.topicResponse;

import javax.validation.constraints.NotNull;

public class TopicDTOOnDeleteFailureResponse extends TopicDTOResponse {

    private static final String MESSAGE = "OOPS! Topic could not deleted";

    public TopicDTOOnDeleteFailureResponse(@NotNull int id) {
        super(false, id);
    }

    public static String getMessage() {
        return MESSAGE;
    }
}
