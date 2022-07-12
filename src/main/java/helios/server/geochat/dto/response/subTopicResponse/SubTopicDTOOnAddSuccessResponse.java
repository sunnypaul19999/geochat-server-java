package helios.server.geochat.dto.response.subTopicResponse;

import javax.validation.constraints.NotNull;

public class SubTopicDTOOnAddSuccessResponse extends SubTopicDTOResponse {

  private static final String MESSAGE = "SubTopic added successfully!";

  private int subTopicId;

  public SubTopicDTOOnAddSuccessResponse(@NotNull int subTopicId) {
    super(true);
    this.subTopicId = subTopicId;
  }

  public int getSubTopicId() {
    return subTopicId;
  }

  public static String getMessage() {
    return MESSAGE;
  }
}
