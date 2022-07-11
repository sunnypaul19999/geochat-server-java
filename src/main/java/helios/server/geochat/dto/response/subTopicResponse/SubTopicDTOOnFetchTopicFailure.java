package helios.server.geochat.dto.response.subTopicResponse;

public class SubTopicDTOOnFetchTopicFailure extends SubTopicDTOResponse {

  private final String message;

  public SubTopicDTOOnFetchTopicFailure() {
    super(false);
    this.message = "OOPs! failed to fetch topics";
  }

  public SubTopicDTOOnFetchTopicFailure(String message) {
    super(false);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
