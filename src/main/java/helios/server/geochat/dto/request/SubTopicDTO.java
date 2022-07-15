package helios.server.geochat.dto.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubTopicDTO {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String geoPointPlusCode;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int topicId;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int subTopicId;

  @NotNull
  @Length(min = 2, max = 250)
  @JsonProperty(access = JsonProperty.Access.READ_WRITE)
  private String subTopicTitle;

  @NotNull
  @Length(min = 2, max = 4000)
  @JsonProperty(access = JsonProperty.Access.READ_WRITE)
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

  @Override
  public String toString() {
    return "SubTopicDTO{" + "subTopicId=" + subTopicId + ", topicId=" + topicId + '}';
  }
}
