package helios.server.geochat.dto.response.topicresponse;

import helios.server.geochat.dto.request.TopicDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

public class TopicDTOOnFetchTopicSuccess extends TopicDTOResponse {
    private static final String MESSAGE = "Topic added successfully!";

    List<TopicDTO> topics;

    public TopicDTOOnFetchTopicSuccess(@NotNull List<TopicDTO> topics) {
        super(true);
        this.topics = topics;
    }

    public static String getMessage() {
        return MESSAGE;
    }

    public List<TopicDTO> getTopics() {
        return topics;
    }
}
