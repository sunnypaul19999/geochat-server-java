package helios.server.geochat.dto.response.subTopicResponse;

import javax.validation.constraints.NotNull;

public class SubTopicDTOOnAddSuccessResponse extends SubTopicDTOResponse {

    private static final String MESSAGE = "SubTopic added successfully!";

    private Integer id;

    public SubTopicDTOOnAddSuccessResponse(@NotNull int id) {
        super(true);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static String getMessage() {
        return MESSAGE;
    }
}
