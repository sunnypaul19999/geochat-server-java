package helios.server.geochat.dto.response.subTopicResponse;

import javax.validation.constraints.NotNull;

public class SubTopicDTOOnAddSuccessResponse extends SubTopicDTOResponse {

    private static final String MESSAGE = "SubTopic added successfully!";

    public SubTopicDTOOnAddSuccessResponse(@NotNull int id) {
        super(true, id);
    }

    public static String getMessage() {
        return MESSAGE;
    }
}
