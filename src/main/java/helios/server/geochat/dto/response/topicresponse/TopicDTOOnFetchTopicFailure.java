package helios.server.geochat.dto.response.topicresponse;

public class TopicDTOOnFetchTopicFailure extends TopicDTOResponse {
  private static String message = "OOPs! failed to fetch topics";

  public TopicDTOOnFetchTopicFailure() {
    super(false);
  }

  public TopicDTOOnFetchTopicFailure(String message) {
    super(false);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
