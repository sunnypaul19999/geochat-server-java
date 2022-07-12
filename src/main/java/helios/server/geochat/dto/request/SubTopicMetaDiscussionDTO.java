package helios.server.geochat.dto.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class SubTopicMetaDiscussionDTO {

  @NotNull private int subTopicId;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int messageId;

  @NotNull private String senderUsername;

  @NotNull
  @Length(max = 250)
  private String message;

  public SubTopicMetaDiscussionDTO(
      int messageId, String senderUsername, int subTopicId, String message) {
    this.messageId = messageId;
    this.senderUsername = senderUsername;
    this.subTopicId = subTopicId;
    this.message = message;
  }

  public int getMessageId() {
    return messageId;
  }

  public void setMessageId(int messageId) {
    this.messageId = messageId;
  }

  public String getSenderUsername() {
    return senderUsername;
  }

  public void setSenderUsername(String senderUsername) {
    this.senderUsername = senderUsername;
  }

  public int getSubTopicId() {
    return subTopicId;
  }

  public void setSubTopicId(int subTopicId) {
    this.subTopicId = subTopicId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
