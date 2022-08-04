package helios.server.geochat.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(value = "id", allowGetters = true, ignoreUnknown = true)
public class TopicDTO {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String plusCode;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int id;

  @NotNull
  @Length(min = 2, max = 250)
  @JsonProperty(access = JsonProperty.Access.READ_WRITE)
  private String topicTitle;

  public TopicDTO(@NotNull @Length(min = 80, max = 250) String title) {
    this.topicTitle = title;
  }

  public TopicDTO(@NotNull int id, @NotNull @Length(min = 80, max = 250) String title) {
    this.topicTitle = title;
  }

  public TopicDTO(String plusCode, int id, String topicTitle) {
    this.plusCode = plusCode;
    this.id = id;
    this.topicTitle = topicTitle;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTopicTitle() {
    return topicTitle;
  }

  public void setTopicTitle(String topicTitle) {
    this.topicTitle = topicTitle;
  }

  public String getPlusCode() {
    return plusCode;
  }

  public void setPlusCode(String plusCode) {
    this.plusCode = plusCode;
  }

  @Override
  public String toString() {
    return "TopicDTO{" + "id=" + id + '}';
  }
}
