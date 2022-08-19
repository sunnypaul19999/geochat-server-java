package helios.server.geochat.dto.response.subtopicresponse;

public class SubTopicDTOOnUpdateSuccessResponse extends SubTopicDTOResponse {
  private static final String MESSAGE = "SubTopic updated successfully!";

  public SubTopicDTOOnUpdateSuccessResponse() {
    super(true);
  }

  public static String getMessage() {
    return MESSAGE;
  }
}
