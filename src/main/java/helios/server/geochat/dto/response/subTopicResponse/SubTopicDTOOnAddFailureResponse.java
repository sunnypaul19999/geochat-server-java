package helios.server.geochat.dto.response.subTopicResponse;

public class SubTopicDTOOnAddFailureResponse extends SubTopicDTOResponse {

  private final String message;

  public SubTopicDTOOnAddFailureResponse() {
    super(false);
    this.message = "OOPS! SubTopic could not added";
  }

  public SubTopicDTOOnAddFailureResponse(String message) {
    super(false);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
