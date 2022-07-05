package helios.server.geochat.dto.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class SubTopicDTO {
    @NotNull
    @Length(min = 80, max = 250)
    private String title;

    @NotNull
    @Length(min = 80, max = 4000)
    private String description;

    public SubTopicDTO(
            @NotNull @Length(min = 80, max = 250) String title,
            @NotNull @Length(min = 80, max = 4000) String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
