package helios.server.geochat.dto.response.topicresponse;

public class TopicDTOOnAddFailureResponse extends TopicDTOResponse {

  private final String message;

  public TopicDTOOnAddFailureResponse() {
    super(false);
    this.message = "OOPS! Topic could not added";
  }

  public TopicDTOOnAddFailureResponse(String message) {
    super(false);
    this.message = message;
  }

  public String getMSG() {
    return message;
  }
}
