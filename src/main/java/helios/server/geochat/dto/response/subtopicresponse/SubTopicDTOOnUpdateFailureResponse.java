package helios.server.geochat.dto.response.subtopicresponse;

public class SubTopicDTOOnUpdateFailureResponse extends SubTopicDTOResponse {
  private final String message;

  public SubTopicDTOOnUpdateFailureResponse() {
    super(false);
    this.message = "SubTopic not updated!";
  }

  public SubTopicDTOOnUpdateFailureResponse(String message) {
    super(false);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
