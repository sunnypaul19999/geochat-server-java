package helios.server.geochat.controller;

import helios.server.geochat.dto.response.subTopicResponse.*;
import helios.server.geochat.exceptions.dtoException.InvalidRequestFormatException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicException;
import helios.server.geochat.exceptions.serviceExceptions.topicServiceException.TopicNotFoundException;
import helios.server.geochat.service.SubTopicService;

import helios.server.geochat.dto.request.SubTopicDTO;

import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicPageNumberNotInRangeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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

      return new SubTopicDTOOnFetchSuccessRespnse(List.of(subTopicDTO));

    } catch (SubTopicNotFoundException e) {

      response.setStatus(404);
    }

    return new SubTopicDTOOnFetchFailureResponse();
  }

  @GetMapping(path = "/page/{pageNumber}")
  public SubTopicDTOResponse getTopicsByPageNumber(
      @PathVariable("pageNumber") int pageNumber, HttpServletResponse response) {

    try {

      List<SubTopicDTO> subTopicDTOList = subTopicService.getPagedSubTopics(pageNumber);

      return new SubTopicDTOOnFetchSuccessRespnse(subTopicDTOList);

    } catch (SubTopicPageNumberNotInRangeException e) {

      response.setStatus(404);

      return new SubTopicDTOOnFetchFailureResponse("Page number not in range");

    } catch (SubTopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);
    }

    return new SubTopicDTOOnFetchFailureResponse();
  }

  @GetMapping(path = "/all")
  public SubTopicDTOResponse getAllTopics(HttpServletResponse response) {

    try {

      List<SubTopicDTO> SubTopicDTOList = subTopicService.getSubAllTopics();

      return new SubTopicDTOOnFetchSuccessRespnse(SubTopicDTOList);

    } catch (SubTopicException e) {

      logger.error(e.getMessage(), e);

      response.setStatus(500);
    }

    return new SubTopicDTOOnFetchFailureResponse();
  }

  @PostMapping(path = "/add")
  public SubTopicDTOResponse addTopic(
      HttpServletResponse response,
      @Valid @RequestBody SubTopicDTO subTopicDTO,
      BindingResult bindingResult)
      throws InvalidRequestFormatException {

    try {

      if (bindingResult.hasErrors()) {

        throw new InvalidRequestFormatException();
      }

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
      @PathVariable("subTopicId") int subTopicId,
      @Valid @RequestBody SubTopicDTO subTopicDTO,
      BindingResult bindingResult,
      HttpServletResponse response)
      throws InvalidRequestFormatException {

    try {

      if (bindingResult.hasErrors()) {

        throw new InvalidRequestFormatException();
      }

      // setting the subTopicId received from path variable
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
      @PathVariable("subTopicId") int subTopicId, HttpServletResponse response) {

    try {

      SubTopicDTO SubTopicDTO = subTopicService.deleteSubTopic(subTopicId);

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
