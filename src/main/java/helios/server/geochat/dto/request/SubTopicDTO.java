package helios.server.geochat.dto.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubTopicDTO {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int subTopicId;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private int topicId;

  @NotNull
  @Length(min = 2, max = 250)
  private String subTopicTitle;

  @NotNull
  @Length(min = 2, max = 4000)
  private String subTopicDescription;

  public SubTopicDTO() {}

  public SubTopicDTO(String subTopicTitle, String description) {
    this.subTopicTitle = subTopicTitle;
    this.subTopicDescription = description;
  }

  public SubTopicDTO(int subTopicId, String subTopicTitle, String description) {
    this.subTopicId = subTopicId;
    this.subTopicTitle = subTopicTitle;
    this.subTopicDescription = description;
  }

  public int getSubTopicId() {
    return subTopicId;
  }

  public int getTopicId() {
    return topicId;
  }

  public void setTopicId(int topicId) {
    this.topicId = topicId;
  }

  public String getSubTopicTitle() {
    return subTopicTitle;
  }

  public void setSubTopicTitle(String subTopicTitle) {
    this.subTopicTitle = subTopicTitle;
  }

  public String getSubTopicDescription() {
    return subTopicDescription;
  }

  public void setSubTopicDescription(String subTopicDescription) {
    this.subTopicDescription = subTopicDescription;
  }
}
