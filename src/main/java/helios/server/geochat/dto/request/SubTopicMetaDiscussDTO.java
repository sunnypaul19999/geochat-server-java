package helios.server.geochat.dto.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class SubTopicMetaDiscussDTO {

    @NotNull
    private String receiverUsername;

    @NotNull
    private int subTopicId;

    @NotNull
    @Length(max = 250)
    private String message;

    public SubTopicMetaDiscussDTO(@NotNull String receiverUsername, @NotNull int subTopicId,
            @NotNull @Length(max = 250) String message) {
        this.receiverUsername = receiverUsername;
        this.subTopicId = subTopicId;
        this.message = message;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public int getSubTopicId() {
        return subTopicId;
    }

    public void setSubTopicId(int subTopicId) {
        this.subTopicId = subTopicId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
