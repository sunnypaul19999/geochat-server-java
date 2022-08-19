package helios.server.geochat.dto.response.topicresponse;

public class TopicDTOOnUpdateFailureResponse extends TopicDTOResponse {
  private final String message;

  public TopicDTOOnUpdateFailureResponse() {
    super(false);
    this.message = "Topic not updated!";
  }

  public TopicDTOOnUpdateFailureResponse(String message) {
    super(false);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
