package helios.server.geochat.dto.response.topicResponse;

import javax.validation.constraints.NotNull;

public class TopicDTOOnAddSuccessResponse extends TopicDTOResponse {

    private static final String MESSAGE = "Topic added successfully!";

    private Integer id;

    public TopicDTOOnAddSuccessResponse(@NotNull int id) {
        super(true);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static String getMessage() {
        return MESSAGE;
    }
}
