package helios.server.geochat.dto.response.topicresponse;

public class TopicDTOOnUpdateTopicSuccess extends TopicDTOResponse {
  private static final String MESSAGE = "SubTopic updated successfully!";

  public TopicDTOOnUpdateTopicSuccess() {
    super(true);
  }

  public static String getMessage() {
    return MESSAGE;
  }
}
