package helios.server.geochat.dto.response.geosubtopicmetadiscussion;

public class SubTopicMetaDiscussionDTOOnAddFailureResponse
    extends SubTopicMetaDiscussionDTOResponse {

  private final String message;

  public SubTopicMetaDiscussionDTOOnAddFailureResponse() {
    super(false);
    this.message = "OOPS! Message could not added";
  }

  public SubTopicMetaDiscussionDTOOnAddFailureResponse(String message) {
    super(false);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
