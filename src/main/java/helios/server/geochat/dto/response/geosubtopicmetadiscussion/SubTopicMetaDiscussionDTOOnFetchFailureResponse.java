package helios.server.geochat.dto.response.geosubtopicmetadiscussion;

public class SubTopicMetaDiscussionDTOOnFetchFailureResponse
    extends SubTopicMetaDiscussionDTOResponse {

  private final String message;

  public SubTopicMetaDiscussionDTOOnFetchFailureResponse() {
    super(false);
    this.message = "OOPs! failed to fetch messages";
  }

  public SubTopicMetaDiscussionDTOOnFetchFailureResponse(String message) {
    super(false);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
