package helios.server.geochat.dto.response.geoSubTopicMetaDiscussion;

import javax.validation.constraints.NotNull;

public class SubTopicMetaDiscussionDTOResponse {

    @NotNull
    private boolean executionStatus;

    public SubTopicMetaDiscussionDTOResponse(@NotNull boolean executionStatus) {
        this.executionStatus = executionStatus;
    }

    public boolean getExecutionStatus() {
        return executionStatus;
    }
}
