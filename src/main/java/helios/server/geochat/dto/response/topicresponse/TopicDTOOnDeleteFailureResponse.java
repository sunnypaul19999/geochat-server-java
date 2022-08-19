package helios.server.geochat.dto.response.topicresponse;

import javax.validation.constraints.NotNull;

public class TopicDTOOnDeleteFailureResponse extends TopicDTOResponse {

    private static final String MESSAGE = "OOPS! Topic could not deleted";

    private Integer id;

    public TopicDTOOnDeleteFailureResponse(@NotNull int id) {
        super(false);
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
