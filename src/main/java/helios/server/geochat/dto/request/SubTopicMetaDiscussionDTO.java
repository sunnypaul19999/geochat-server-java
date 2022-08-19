package helios.server.geochat.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubTopicMetaDiscussionDTO {

  @JsonIgnore private String geoPointPlusCode;

  @JsonIgnore private int topicId;

  @NotNull @JsonIgnore private int subTopicId;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int messageId;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String senderUsername;

  @NotNull
  @Length(max = 250)
  @JsonProperty(access = JsonProperty.Access.READ_WRITE)
  private String message;

  public SubTopicMetaDiscussionDTO() {}

  public SubTopicMetaDiscussionDTO(
      int topicId, int subTopicId, int messageId, String senderUsername, String message) {
    this.topicId = topicId;
    this.subTopicId = subTopicId;
    this.messageId = messageId;
    this.senderUsername = senderUsername;
    this.message = message;
  }

  public String getGeoPointPlusCode() {
    return geoPointPlusCode;
  }

  public SubTopicMetaDiscussionDTO setGeoPointPlusCode(String geoPointPlusCode) {
    this.geoPointPlusCode = geoPointPlusCode;
    return this;
  }

  public int getTopicId() {
    return topicId;
  }

  public SubTopicMetaDiscussionDTO setTopicId(int topicId) {
    this.topicId = topicId;
    return this;
  }

  public int getSubTopicId() {
    return subTopicId;
  }

  public SubTopicMetaDiscussionDTO setSubTopicId(int subTopicId) {
    this.subTopicId = subTopicId;
    return this;
  }

  public int getMessageId() {
    return messageId;
  }

  public SubTopicMetaDiscussionDTO setMessageId(int messageId) {
    this.messageId = messageId;
    return this;
  }

  public String getSenderUsername() {
    return senderUsername;
  }

  public SubTopicMetaDiscussionDTO setSenderUsername(String senderUsername) {
    this.senderUsername = senderUsername;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public SubTopicMetaDiscussionDTO setMessage(String message) {
    this.message = message;
    return this;
  }

  @Override
  public String toString() {

    return "SubTopicMetaDiscussionDTO{"
        + "topicId="
        + topicId
        + ", subTopicId="
        + subTopicId
        + ", messageId="
        + messageId
        + ", senderUsername='"
        + senderUsername
        + '\''
        + ", message='"
        + message
        + '\''
        + '}';
  }
}
