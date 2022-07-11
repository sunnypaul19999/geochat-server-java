package helios.server.geochat.dto.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

@JsonIgnoreProperties(value = "id", allowGetters = true)
public class TopicDTO {

  private int id;

  public void setTitle(String title) {
    this.title = title;
  }

  @NotNull
  @Length(min = 80, max = 250)
  private String title;

  public TopicDTO(@NotNull @Length(min = 80, max = 250) String title) {
    this.title = title;
  }

  public TopicDTO(@NotNull int id, @NotNull @Length(min = 80, max = 250) String title) {
    this.id = id;
    this.title = title;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }
}
