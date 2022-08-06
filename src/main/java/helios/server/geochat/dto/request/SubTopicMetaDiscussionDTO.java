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

  public void setGeoPointPlusCode(String geoPointPlusCode) {
    this.geoPointPlusCode = geoPointPlusCode;
  }

  public int getTopicId() {
    return topicId;
  }

  public void setTopicId(int topicId) {
    this.topicId = topicId;
  }

  public int getSubTopicId() {
    return subTopicId;
  }

  public void setSubTopicId(int subTopicId) {
    this.subTopicId = subTopicId;
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

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
