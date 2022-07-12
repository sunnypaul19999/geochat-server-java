package helios.server.geochat.dto.response.geoSubTopicMetaDiscussion;

import helios.server.geochat.dto.request.SubTopicMetaDiscussionDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SubTopicMetaDiscussionDTOOnFetchSuccessResponse
    extends SubTopicMetaDiscussionDTOResponse {
  private static final String MESSAGE = "Message fetched successfully!";

  List<SubTopicMetaDiscussionDTO> subTopicMetaDiscussion;

  public SubTopicMetaDiscussionDTOOnFetchSuccessResponse(
      @NotNull List<SubTopicMetaDiscussionDTO> subTopicMetaDiscussion) {
    super(true);
    this.subTopicMetaDiscussion = subTopicMetaDiscussion;
  }

  public List<SubTopicMetaDiscussionDTO> getSubTopicMetaDiscussion() {
    return subTopicMetaDiscussion;
  }

  public static String getMessage() {
    return MESSAGE;
  }
}
