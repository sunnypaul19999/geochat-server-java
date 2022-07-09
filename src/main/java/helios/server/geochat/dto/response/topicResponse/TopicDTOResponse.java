package helios.server.geochat.dto.response.topicResponse;

import javax.validation.constraints.NotNull;

public class TopicDTOResponse {

    @NotNull
    private boolean executionStatus;

    public TopicDTOResponse(@NotNull boolean executionStatus) {
        this.executionStatus = executionStatus;
    }

    public boolean getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(boolean executionStatus) {
        this.executionStatus = executionStatus;
    }
}
