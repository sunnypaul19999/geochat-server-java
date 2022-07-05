package helios.server.geochat.dto.response.topicResponse;

import javax.validation.constraints.NotNull;

public class TopicDTOOnDeleteSuccessResponse extends TopicDTOResponse {

    private static final String MESSAGE = "Topic deleted!";

    public static String getMessage() {
        return MESSAGE;
    }

    public TopicDTOOnDeleteSuccessResponse(@NotNull int id) {
        super(true, id);
    }
}
