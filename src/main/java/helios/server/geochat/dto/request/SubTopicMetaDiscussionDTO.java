package helios.server.geochat.dto.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class SubTopicMetaDiscussionDTO {

  @NotNull private int subTopicId;

  @NotNull private String senderUsername;

  @NotNull
  @Length(max = 250)
  private String message;

  public SubTopicMetaDiscussionDTO(
      @NotNull String senderUsername,
      @NotNull int subTopicId,
      @NotNull @Length(max = 250) String message) {
    this.senderUsername = senderUsername;
    this.subTopicId = subTopicId;
    this.message = message;
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
