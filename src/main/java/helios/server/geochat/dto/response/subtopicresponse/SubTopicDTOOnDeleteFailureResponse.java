package helios.server.geochat.dto.response.subtopicresponse;

import javax.validation.constraints.NotNull;

public class SubTopicDTOOnDeleteFailureResponse extends SubTopicDTOResponse {

    private static final String MESSAGE = "OOPS! SubTopic could not deleted";

    private Integer id;

    public SubTopicDTOOnDeleteFailureResponse(@NotNull int id) {
        super(false);
        this.id = id;
    }

    public static String getMessage() {
        return MESSAGE;
    }
}
