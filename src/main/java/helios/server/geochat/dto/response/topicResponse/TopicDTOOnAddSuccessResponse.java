package helios.server.geochat.dto.response.topicResponse;

import javax.validation.constraints.NotNull;

public class TopicDTOOnAddSuccessResponse extends TopicDTOResponse {

    private static final String MESSAGE = "Topic added successfully!";

    private int id;

    public TopicDTOOnAddSuccessResponse(@NotNull int id) {
        super(true);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getMessage() {
        return MESSAGE;
    }
}
