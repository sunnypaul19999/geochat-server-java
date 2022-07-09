package helios.server.geochat.dto.response.subTopicResponse;

import javax.validation.constraints.NotNull;

public class SubTopicDTOOnDeleteSuccessResponse extends SubTopicDTOResponse {

    private static final String MESSAGE = "SubTopic deleted!";

    private Integer id;

    public static String getMessage() {
        return MESSAGE;
    }

    public SubTopicDTOOnDeleteSuccessResponse(@NotNull int id) {
        super(true);
        this.id = id;
    }
}
