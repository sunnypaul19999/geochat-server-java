package helios.server.geochat.dto.response;

import javax.validation.constraints.NotNull;

public class TopicDTOOnAddFailureResponse extends TopicDTOResponse {

    public TopicDTOOnAddFailureResponse(@NotNull int id) {
        super(false, id);
    }
}
