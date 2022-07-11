package helios.server.geochat.dto.response.subTopicResponse;

import java.util.List;
import javax.validation.constraints.NotNull;

import helios.server.geochat.dto.request.SubTopicDTO;

public class SubTopicDTOOnFetchTopicSuccess extends SubTopicDTOResponse {
    private static final String MESSAGE = "Topic added successfully!";

    List<SubTopicDTO> subtopic;

    public SubTopicDTOOnFetchTopicSuccess(@NotNull List<SubTopicDTO> subtopic) {
        super(true);
        this.subtopic = subtopic;
    }

    public List<SubTopicDTO> getSubtopic() {
        return subtopic;
    }

    public static String getMessage() {
        return MESSAGE;
    }
}
