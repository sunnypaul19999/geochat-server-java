package helios.server.geochat.dto.response;

import javax.validation.constraints.NotNull;

public class TopicDTOOnAddSuccessResponse extends TopicDTOResponse {

    public TopicDTOOnAddSuccessResponse(@NotNull int id) {
        super(true, id);
    }
}
