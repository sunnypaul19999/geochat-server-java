package helios.server.geochat.dto.response.geoSubTopicMetaDiscussion;

import javax.validation.constraints.NotNull;

public class SubTopicMetaDiscussionDTOOnAddSuccessResponse
    extends SubTopicMetaDiscussionDTOResponse {

  private static final String MESSAGE = "Message added successfully!";

  private int messageId;

  public SubTopicMetaDiscussionDTOOnAddSuccessResponse(@NotNull int messageId) {
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
