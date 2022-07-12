package helios.server.geochat.dto.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

@JsonIgnoreProperties(value = "id", allowGetters = true)
public class TopicDTO {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int id;

  @NotNull
  @Length(min = 2, max = 250)
  private String topicTitle;

  public TopicDTO(@NotNull @Length(min = 80, max = 250) String title) {
    this.topicTitle = title;
  }

  public TopicDTO(@NotNull int id, @NotNull @Length(min = 80, max = 250) String title) {
    this.id = id;
    this.topicTitle = title;
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

  @Override
  public String toString() {
    return "TopicDTO{" + "id=" + id + '}';
  }
}
