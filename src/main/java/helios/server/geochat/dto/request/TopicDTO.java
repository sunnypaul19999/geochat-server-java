package helios.server.geochat.dto.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class TopicDTO {
    @NotNull
    @Length(min = 80, max = 250)
    private String title;

    public TopicDTO(@NotNull @Length(min = 80, max = 250) String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
