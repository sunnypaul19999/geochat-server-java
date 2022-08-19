package helios.server.geochat.dto.response.subtopicresponse;

public class SubTopicDTOOnFetchFailureResponse extends SubTopicDTOResponse {

  private final String message;

  public SubTopicDTOOnFetchFailureResponse() {
    super(false);
    this.message = "OOPs! failed to fetch topics";
  }

  public SubTopicDTOOnFetchFailureResponse(String message) {
    super(false);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
