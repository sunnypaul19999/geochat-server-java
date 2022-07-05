package helios.server.geochat.dto.response.subTopicResponse;

import javax.validation.constraints.NotNull;

public class SubTopicDTOResponse {

    @NotNull
    private boolean executionStatus;

    private Integer id;

    public SubTopicDTOResponse(@NotNull boolean executionStatus, Integer id) {
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
