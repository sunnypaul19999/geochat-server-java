package helios.server.geochat.dto.response.topicResponse;

import javax.validation.constraints.NotNull;

public class TopicDTOOnAddSuccessResponse extends TopicDTOResponse {

    private static final String MESSAGE = "Topic added successfully!";

    public TopicDTOOnAddSuccessResponse(@NotNull int id) {
        super(true, id);
    }

    public static String getMessage() {
        return MESSAGE;
    }
}
