package helios.server.geochat.controller;

import helios.server.geochat.dto.response.subTopicResponse.SubTopicDTOOnAddFailureResponse;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicNotFoundException;
import helios.server.geochat.service.SubTopicService;

import helios.server.geochat.dto.request.SubTopicDTO;
import helios.server.geochat.dto.response.subTopicResponse.SubTopicDTOOnFetchTopicFailure;
import helios.server.geochat.dto.response.subTopicResponse.SubTopicDTOOnFetchTopicSuccess;
import helios.server.geochat.dto.response.subTopicResponse.SubTopicDTOResponse;

import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicPageNumberNotInRangeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.Valid;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/geopoint/subtopic")
public class GeoSubTopicController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired SubTopicService subTopicService;

  @GetMapping(path = "/{subTopicId}")
  public SubTopicDTOResponse getTopicById(
      @PathVariable("subTopicId") int subTopicId, HttpServletResponse response) {

    try {

      SubTopicDTO subTopicDTO = subTopicService.getSubTopicById(subTopicId);

      return new SubTopicDTOOnFetchTopicSuccess(List.of(subTopicDTO));

    } catch (SubTopicNotFoundException e) {

      response.setStatus(404);
    }

    return new SubTopicDTOOnFetchTopicFailure();
  }

  @GetMapping(path = "/page/{pageNumber}")
  public SubTopicDTOResponse getTopicsByPageNumber(
      @PathVariable("pageNumber") int pageNumber, HttpServletResponse response) {

    try {

      List<SubTopicDTO> subTopicDTOList = subTopicService.getSubTopicsByPaged(pageNumber);

      return new SubTopicDTOOnFetchTopicSuccess(subTopicDTOList);

    } catch (SubTopicPageNumberNotInRangeException e) {

      response.setStatus(404);

      return new SubTopicDTOOnFetchTopicFailure("Page number not in range");

    } catch (SubTopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);
    }

    return new SubTopicDTOOnFetchTopicFailure();
  }

  @GetMapping(path = "/all")
  public SubTopicDTOResponse getAllTopics(HttpServletResponse response) {

    try {

      List<SubTopicDTO> SubTopicDTOList = subTopicService.getSubAllTopics();

      return new SubTopicDTOOnFetchTopicSuccess(SubTopicDTOList);

    } catch (SubTopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);
    }

    return new SubTopicDTOOnFetchTopicFailure();
  }

  @PostMapping(path = "/add")
  public SubTopicDTOResponse addTopic(
      @Valid @RequestBody SubTopicDTO subTopicDTO, HttpServletResponse response) {

    try {

      SubTopicDTO persistedSubTopicDTO = subTopicService.addSubTopic(subTopicDTO);

      return new SubTopicDTOOnFetchTopicSuccess(List.of(persistedSubTopicDTO));

    } catch (TopicNotFoundException e) {

      logger.info(e.getMessage(), e);

      response.setStatus(404);

      return new SubTopicDTOOnAddFailureResponse("Topic does not exists");

    } catch (SubTopicException | TopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);
    }

    return new SubTopicDTOOnFetchTopicFailure();
  }

  @DeleteMapping(path = "/delete/{subTopicId}")
  public SubTopicDTOResponse deleteTopic(
      @PathVariable("subTopicId") int subTopicId, HttpServletResponse response) {

    try {

      SubTopicDTO SubTopicDTO = subTopicService.deleteSubTopic(subTopicId);

      return new SubTopicDTOOnFetchTopicSuccess(List.of(SubTopicDTO));

    } catch (SubTopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);
    }

    return new SubTopicDTOOnFetchTopicFailure();
  }
}
