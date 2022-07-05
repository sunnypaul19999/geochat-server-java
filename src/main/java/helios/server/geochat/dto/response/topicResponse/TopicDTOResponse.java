package helios.server.geochat.dto.response.topicResponse;

import javax.validation.constraints.NotNull;

public class TopicDTOResponse {

    @NotNull
    private boolean executionStatus;

    @NotNull
    private int id;

    public TopicDTOResponse(@NotNull boolean executionStatus, @NotNull int id) {
        this.executionStatus = executionStatus;
        this.id = id;
    }

    public boolean getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(boolean executionStatus) {
        this.executionStatus = executionStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
