package helios.server.geochat.dto.response.geosubtopicmetadiscussion;

import javax.validation.constraints.NotNull;

public class SubTopicMetaDiscussionDTOOnDeleteSuccessResponse
    extends SubTopicMetaDiscussionDTOResponse {

  private static final String MESSAGE = "Message deleted!";

  private int messageId;

  public SubTopicMetaDiscussionDTOOnDeleteSuccessResponse(@NotNull int messageId) {
    super(true);
    this.messageId = messageId;
  }

  public int getMessageId() {
    return messageId;
  }

  public static String getMessage() {
    return MESSAGE;
  }
}
