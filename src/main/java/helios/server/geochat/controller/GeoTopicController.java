package helios.server.geochat.controller;

import helios.server.geochat.dto.request.TopicDTO;
import helios.server.geochat.dto.response.topicResponse.*;
import helios.server.geochat.exceptions.dtoException.InvalidDTOFieldValueException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicPageNumberNotInRangeException;
import helios.server.geochat.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/geopoint/topic")
public class GeoTopicController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired TopicService topicService;

  @GetMapping(path = "/{id}")
  public TopicDTOResponse getTopicById(@PathVariable("id") int id, HttpServletResponse response) {

    try {

      TopicDTO topicDTO = topicService.getTopicById(id);

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

      List<TopicDTO> topicDTOList = topicService.getPagedTopics(pageNumber);

      return new TopicDTOOnFetchTopicSuccess(topicDTOList);

    } catch (TopicPageNumberNotInRangeException e) {

      response.setStatus(404);

      return new TopicDTOOnFetchTopicFailure("Page number not in range");

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
      @Valid @RequestBody TopicDTO topicDTO,
      BindingResult bindingResult,
      HttpServletResponse response) {

    try {

      if (bindingResult.hasErrors()) {

        throw new InvalidDTOFieldValueException();
      }

      TopicDTO persistedTopicDTO = topicService.addTopic(topicDTO);

      return new TopicDTOOnAddSuccessResponse(persistedTopicDTO.getId());

    } catch (InvalidDTOFieldValueException e) {

      response.setStatus(406);

      return new TopicDTOOnAddFailureResponse(e.getMessage());

    } catch (TopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);

      return new TopicDTOOnFetchTopicFailure();
    }
  }

  @PutMapping(path = "/update/{topicId}")
  public TopicDTOResponse updateSubTopic(
      @PathVariable("topicId") int topicId,
      @Valid @RequestBody TopicDTO topicDTO,
      BindingResult bindingResult,
      HttpServletResponse response) {

    try {

      if (bindingResult.hasErrors()) {

        throw new InvalidDTOFieldValueException();
      }

      // setting topicId received as path variable
      topicDTO.setId(topicId);

      topicService.updateTopic(topicDTO);

      return new TopicDTOOnUpdateTopicSuccess();

    } catch (InvalidDTOFieldValueException e) {

      response.setStatus(406);

      return new TopicDTOOnUpdateFailureResponse(e.getMessage());

    } catch (TopicNotFoundException e) {

      logger.info(e.getMessage(), e);

      response.setStatus(404);

      return new TopicDTOOnUpdateFailureResponse("Topic not found!");

    } catch (TopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);

      return new TopicDTOOnUpdateFailureResponse();
    }
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
