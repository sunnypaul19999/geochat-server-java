package helios.server.geochat.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubTopicDTO implements Comparable<SubTopicDTO> {

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

  public SubTopicDTO(int subTopicId, String subTopicTitle, String subTopicDescription) {
    this.subTopicId = subTopicId;
    this.subTopicTitle = subTopicTitle;
    this.subTopicDescription = subTopicDescription;
  }

  public SubTopicDTO(
      int topicId, int subTopicId, String subTopicTitle, String subTopicDescription) {
    this.topicId = topicId;
    this.subTopicId = subTopicId;
    this.subTopicTitle = subTopicTitle;
    this.subTopicDescription = subTopicDescription;
  }

  public SubTopicDTO(
      String geoPointPlusCode,
      int topicId,
      int subTopicId,
      String subTopicTitle,
      String subTopicDescription) {

    this.geoPointPlusCode = geoPointPlusCode;
    this.topicId = topicId;
    this.subTopicId = subTopicId;
    this.subTopicTitle = subTopicTitle;
    this.subTopicDescription = subTopicDescription;
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
  public int compareTo(SubTopicDTO o) {

    if (o.geoPointPlusCode.equals(geoPointPlusCode)
        && o.topicId == topicId
        && o.subTopicId == subTopicId
        && o.subTopicTitle.equals(subTopicTitle)
        && o.subTopicDescription.equals(subTopicDescription)) return 0;

    return -1;
  }

  @Override
  public String toString() {
    return "SubTopicDTO{" + "subTopicId=" + subTopicId + ", topicId=" + topicId + '}';
  }
}
