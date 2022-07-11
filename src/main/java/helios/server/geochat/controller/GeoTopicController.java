package helios.server.geochat.controller;

import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.dto.response.topicResponse.TopicDTOOnFetchTopicFailure;
import helios.server.geochat.dto.response.topicResponse.TopicDTOOnFetchTopicSuccess;
import helios.server.geochat.dto.response.topicResponse.TopicDTOResponse;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicPageNumberNotInRangeException;
import helios.server.geochat.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/geopoint/topic")
public class GeoTopicController {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired TopicService topicService;

  @GetMapping(path = "/{id}")
  public TopicDTOResponse getTopicById(@PathVariable("id") int id, HttpServletResponse response) {

    try {

      TopicDTO topicDTO = topicService.getTopic(id);

      return new TopicDTOOnFetchTopicSuccess(List.of(topicDTO));

    } catch (TopicNotFoundException e) {

      response.setStatus(404);

    } catch (TopicException e) {

      response.setStatus(500);
    }

    return new TopicDTOOnFetchTopicFailure();
  }

  @GetMapping(path = "/page/{pageNumber}")
  public TopicDTOResponse getTopicsByPageNumber(
      @PathVariable("pageNumber") int pageNumber, HttpServletResponse response) {

    try {

      List<TopicDTO> topicDTOList = topicService.getTopicsByPage(pageNumber);

      return new TopicDTOOnFetchTopicSuccess(topicDTOList);

    } catch (TopicPageNumberNotInRangeException e) {

      response.setStatus(404);

      new TopicDTOOnFetchTopicFailure("Page number not in range");

    } catch (TopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);
    }

    return new TopicDTOOnFetchTopicFailure();
  }

  @GetMapping(path = "/all")
  public TopicDTOResponse getAllTopics(HttpServletResponse response) {

    try {

      List<TopicDTO> topicDTOList = topicService.getAllTopics();

      return new TopicDTOOnFetchTopicSuccess(topicDTOList);

    } catch (TopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);
    }

    return new TopicDTOOnFetchTopicFailure();
  }

  @PostMapping(path = "/add")
  public TopicDTOResponse addTopic(
      @Valid @RequestBody TopicDTO topicDTO, HttpServletResponse response) {

    try {

      TopicDTO persistedTopicDTO = topicService.addTopic(topicDTO);

      return new TopicDTOOnFetchTopicSuccess(List.of(persistedTopicDTO));

    } catch (TopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);
    }

    return new TopicDTOOnFetchTopicFailure();
  }

  @DeleteMapping(path = "/delete/{id}")
  public TopicDTOResponse deleteTopic(@PathVariable("id") int id, HttpServletResponse response) {

    try {

      TopicDTO topicDTO = topicService.deleteTopic(id);

      return new TopicDTOOnFetchTopicSuccess(List.of(topicDTO));

    } catch (TopicException e) {

      response.setStatus(500);
    }

    return new TopicDTOOnFetchTopicFailure();
  }
}
