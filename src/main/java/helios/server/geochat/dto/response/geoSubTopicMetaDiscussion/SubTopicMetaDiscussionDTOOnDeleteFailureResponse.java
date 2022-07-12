package helios.server.geochat.dto.response.geoSubTopicMetaDiscussion;

import javax.validation.constraints.NotNull;

public class SubTopicMetaDiscussionDTOOnDeleteFailureResponse
    extends SubTopicMetaDiscussionDTOResponse {

  private static final String MESSAGE = "OOPS! Message could not deleted";

  private int messageId;

  public SubTopicMetaDiscussionDTOOnDeleteFailureResponse(@NotNull int messageId) {
    super(false);
    this.messageId = messageId;
  }

  public int getMessageId() {
    return messageId;
  }

  public static String getMessage() {
    return MESSAGE;
  }
}
