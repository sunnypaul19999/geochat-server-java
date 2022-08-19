package helios.server.geochat.controller;

import helios.server.geochat.dto.response.subtopicresponse.*;
import helios.server.geochat.exceptions.dtoexception.InvalidRequestFormatException;
import helios.server.geochat.exceptions.serviceexceptions.topicserviceexception.TopicException;
import helios.server.geochat.exceptions.serviceexceptions.topicserviceexception.TopicNotFoundException;
import helios.server.geochat.service.SubTopicService;

import helios.server.geochat.dto.request.SubTopicDTO;

import helios.server.geochat.exceptions.serviceexceptions.subtopicserviceexception.SubTopicException;
import helios.server.geochat.exceptions.serviceexceptions.subtopicserviceexception.SubTopicNotFoundException;
import helios.server.geochat.exceptions.serviceexceptions.subtopicserviceexception.SubTopicPageNumberNotInRangeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.Valid;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/geopoint/topic/{topicId}/subtopic")
public class GeoSubTopicController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired SubTopicService subTopicService;

  @GetMapping(path = "/all")
  public SubTopicDTOResponse getAllSubTopics(
      @PathVariable("topicId") int topicId, HttpServletResponse response) {

    try {

      List<SubTopicDTO> SubTopicDTOList = subTopicService.getAllSubTopics(topicId);

      return new SubTopicDTOOnFetchSuccessRespnse(SubTopicDTOList);

    } catch (TopicNotFoundException e) {

      response.setStatus(404);

      return new SubTopicDTOOnAddFailureResponse("Topic not found");

    } catch (SubTopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);
    }

    return new SubTopicDTOOnFetchFailureResponse();
  }

  @GetMapping(path = "/{subTopicId}")
  public SubTopicDTOResponse getSubTopicById(
      @PathVariable("topicId") int topicId,
      @PathVariable("subTopicId") int subTopicId,
      HttpServletResponse response) {

    try {

      SubTopicDTO subTopicDTO = subTopicService.getSubTopic(topicId, subTopicId);

      return new SubTopicDTOOnFetchSuccessRespnse(List.of(subTopicDTO));

    } catch (SubTopicNotFoundException e) {

      response.setStatus(404);
    }

    return new SubTopicDTOOnFetchFailureResponse();
  }

  @GetMapping(path = "/page/{subTopicPageNumber}")
  public SubTopicDTOResponse getSubTopicsByPageNumber(
      @PathVariable("topicId") int topicId,
      @PathVariable("subTopicPageNumber") int subTopicPageNumber,
      HttpServletResponse response) {

    try {

      List<SubTopicDTO> subTopicDTOList =
          subTopicService.getPagedSubTopics(topicId, subTopicPageNumber);

      return new SubTopicDTOOnFetchSuccessRespnse(subTopicDTOList);

    } catch (TopicNotFoundException e) {

      response.setStatus(404);

      return new SubTopicDTOOnAddFailureResponse("Topic not found");

    } catch (SubTopicPageNumberNotInRangeException e) {

      response.setStatus(404);

      return new SubTopicDTOOnFetchFailureResponse("Page number not in range");

    } catch (SubTopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);
    }

    return new SubTopicDTOOnFetchFailureResponse();
  }

  @PostMapping(path = "/add")
  public SubTopicDTOResponse addSubTopic(
      @PathVariable("topicId") int topicId,
      @Valid @RequestBody SubTopicDTO subTopicDTO,
      BindingResult bindingResult,
      HttpServletResponse response)
      throws InvalidRequestFormatException {

    try {

      if (bindingResult.hasErrors()) {

        throw new InvalidRequestFormatException();
      }

      subTopicDTO.setTopicId(topicId);

      SubTopicDTO persistedSubTopicDTO = subTopicService.addSubTopic(subTopicDTO);

      return new SubTopicDTOOnAddSuccessResponse(persistedSubTopicDTO.getSubTopicId());

    } catch (TopicNotFoundException e) {

      response.setStatus(404);

      return new SubTopicDTOOnAddFailureResponse("Topic not found");

    } catch (SubTopicException | TopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);

      return new SubTopicDTOOnAddFailureResponse();
    }
  }

  @PutMapping(path = "/update/{subTopicId}")
  public SubTopicDTOResponse updateSubTopic(
      @PathVariable("topicId") int topicId,
      @PathVariable("subTopicId") int subTopicId,
      @Valid @RequestBody SubTopicDTO subTopicDTO,
      BindingResult bindingResult,
      HttpServletResponse response)
      throws InvalidRequestFormatException {

    try {

      if (bindingResult.hasErrors()) {

        throw new InvalidRequestFormatException();
      }

      subTopicDTO.setTopicId(topicId);

      subTopicDTO.setSubTopicId(subTopicId);

      subTopicService.updateSubTopic(subTopicDTO);

      return new SubTopicDTOOnUpdateSuccessResponse();

    } catch (SubTopicNotFoundException e) {

      logger.info(e.getMessage(), e);

      response.setStatus(404);

      return new SubTopicDTOOnUpdateFailureResponse("SubTopic not found!");

    } catch (SubTopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);

      return new SubTopicDTOOnUpdateFailureResponse();
    }
  }

  @DeleteMapping(path = "/delete/{subTopicId}")
  public SubTopicDTOResponse deleteTopic(
      @PathVariable("topicId") int topicId,
      @PathVariable("subTopicId") int subTopicId,
      HttpServletResponse response) {

    try {

      SubTopicDTO SubTopicDTO = subTopicService.deleteSubTopic(topicId, subTopicId);

      return new SubTopicDTOOnFetchSuccessRespnse(List.of(SubTopicDTO));

    } catch (SubTopicNotFoundException e) {

      response.setStatus(404);

      return new SubTopicDTOOnUpdateFailureResponse("SubTopic not found!");

    } catch (SubTopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);
    }

    return new SubTopicDTOOnFetchFailureResponse();
  }
}
