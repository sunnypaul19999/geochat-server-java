package helios.server.geochat.dto.response.subtopicresponse;

import javax.validation.constraints.NotNull;

public class SubTopicDTOResponse {

    @NotNull
    private boolean executionStatus;

    public SubTopicDTOResponse(@NotNull boolean executionStatus) {
        this.executionStatus = executionStatus;
    }

    public boolean getExecutionStatus() {
        return executionStatus;
    }
}
