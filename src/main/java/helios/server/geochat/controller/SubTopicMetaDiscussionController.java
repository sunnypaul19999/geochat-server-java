package helios.server.geochat.controller;

import helios.server.geochat.dto.request.SubTopicMetaDiscussionDTO;
import helios.server.geochat.dto.response.geoSubTopicMetaDiscussion.*;
import helios.server.geochat.exceptions.dtoException.InvalidRequestFormatException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionNotFoundException;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.subTopicMetaDiscussionServiceException.SubTopicMetaDiscussionPageNumberNotInRangeException;
import helios.server.geochat.exceptions.serviceExceptions.subTopicServiceException.SubTopicNotFoundException;
import helios.server.geochat.service.SubTopicMetaDiscussionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/geopoint/topic/{topicId}/subTopic/{subTopicId}/subTopiMetaDiscussion")
public class SubTopicMetaDiscussionController {

  @Autowired SubTopicMetaDiscussionService subTopicMetaDiscussionService;

  @GetMapping(path = "/page/{pageNumber}")
  public SubTopicMetaDiscussionDTOResponse getAll(
      @PathVariable("topicId") int topicId,
      @PathVariable("subTopicId") int subTopicId,
      @PathVariable("pageNumber") int pageNumber,
      HttpServletResponse response) {

    try {

      return new SubTopicMetaDiscussionDTOOnFetchSuccessResponse(
          subTopicMetaDiscussionService.getPagedMessages(topicId, subTopicId, pageNumber));

    } catch (SubTopicMetaDiscussionPageNumberNotInRangeException e) {

      response.setStatus(404);

      return new SubTopicMetaDiscussionDTOOnFetchFailureResponse("Page number not in range");

    } catch (SubTopicNotFoundException e) {

      response.setStatus(404);

      return new SubTopicMetaDiscussionDTOOnFetchFailureResponse("SubTopic not found!");
    }
  }

  @GetMapping(path = "/all")
  public SubTopicMetaDiscussionDTOResponse getAll(
      @PathVariable("topicId") int topicId,
      @PathVariable("subTopicId") int subTopicId,
      HttpServletResponse response) {

    try {

      return new SubTopicMetaDiscussionDTOOnFetchSuccessResponse(
          subTopicMetaDiscussionService.getAllMessages(topicId, subTopicId));

    } catch (SubTopicNotFoundException e) {

      response.setStatus(404);

      return new SubTopicMetaDiscussionDTOOnFetchFailureResponse("SubTopic not found!");
    }
  }

  @PostMapping(path = "/add")
  public SubTopicMetaDiscussionDTOResponse addMessage(
      @PathVariable("topicId") int topicId,
      @PathVariable("subTopicId") int subTopicId,
      @Valid @RequestBody SubTopicMetaDiscussionDTO subTopicMetaDiscussionDTO,
      BindingResult bindingResult,
      HttpServletResponse response)
      throws InvalidRequestFormatException {

    try {

      if (bindingResult.hasErrors()) {

        throw new InvalidRequestFormatException();
      }

      subTopicMetaDiscussionDTO.setTopicId(topicId);

      subTopicMetaDiscussionDTO.setSubTopicId(subTopicId);

      int messageId = subTopicMetaDiscussionService.addMessage(subTopicMetaDiscussionDTO);

      return new SubTopicMetaDiscussionDTOOnAddSuccessResponse(messageId);

    } catch (SubTopicNotFoundException e) {

      response.setStatus(404);

      return new SubTopicMetaDiscussionDTOOnAddFailureResponse("SubTopic not found!");

    } catch (GeoUserNotFoundException e) {

      response.setStatus(404);

      return new SubTopicMetaDiscussionDTOOnAddFailureResponse("User not found!");

    } catch (SubTopicMetaDiscussionException e) {

      response.setStatus(500);

      return new SubTopicMetaDiscussionDTOOnAddFailureResponse();
    }
  }

  @DeleteMapping(path = "/message/delete/{metaDiscussId}")
  public SubTopicMetaDiscussionDTOResponse deleteSubtopic(
      @PathVariable("topicId") int topicId,
      @PathVariable("subTopicId") int subTopicId,
      @PathVariable("metaDiscussId") int metaDiscussId,
      HttpServletResponse response) {

    try {

      subTopicMetaDiscussionService.deleteMessage(topicId, subTopicId, metaDiscussId);

      return new SubTopicMetaDiscussionDTOOnAddSuccessResponse(metaDiscussId);

    } catch (SubTopicMetaDiscussionNotFoundException e) {

      response.setStatus(404);

      return new SubTopicMetaDiscussionDTOOnAddFailureResponse("Message does not exists!");

    } catch (SubTopicMetaDiscussionException e) {

      response.setStatus(500);

      return new SubTopicMetaDiscussionDTOOnAddFailureResponse();
    }
  }
}
