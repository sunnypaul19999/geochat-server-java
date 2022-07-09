package helios.server.geochat.dto.response.topicResponse;

import javax.validation.constraints.NotNull;

public class TopicDTOOnDeleteSuccessResponse extends TopicDTOResponse {

    private static final String MESSAGE = "Topic deleted!";

    private Integer id;

    public TopicDTOOnDeleteSuccessResponse(@NotNull int id) {
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
