package helios.server.geochat.dto.response.subTopicResponse;

import javax.validation.constraints.NotNull;

public class SubTopicDTOOnDeleteFailureResponse extends SubTopicDTOResponse {

    private static final String MESSAGE = "OOPS! SubTopic could not deleted";

    public SubTopicDTOOnDeleteFailureResponse(@NotNull int id) {
        super(false, id);
    }

    public static String getMessage() {
        return MESSAGE;
    }
}
