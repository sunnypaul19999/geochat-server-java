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
@RequestMapping(path = "/geopoint/subTopiMetaDiscussion")
public class SubTopicMetaDiscussionController {

  @Autowired SubTopicMetaDiscussionService subTopicMetaDiscussionService;

  @GetMapping(path = "/subTopic/{subTopicId}/page/{pageNumber}")
  public SubTopicMetaDiscussionDTOResponse getAll(
      @PathVariable("subTopicId") int subTopicId,
      @PathVariable("pageNumber") int pageNumber,
      HttpServletResponse response) {

    try {

      return new SubTopicMetaDiscussionDTOOnFetchSuccessResponse(
          subTopicMetaDiscussionService.getPagedMessages(subTopicId, pageNumber));

    } catch (SubTopicMetaDiscussionPageNumberNotInRangeException e) {

      response.setStatus(404);

      return new SubTopicMetaDiscussionDTOOnFetchFailureResponse("Page number not in range");

    } catch (SubTopicNotFoundException e) {

      response.setStatus(404);

      return new SubTopicMetaDiscussionDTOOnFetchFailureResponse("SubTopic not found!");
    }
  }

  @GetMapping(path = "/subTopic/{subTopicId}/all")
  public SubTopicMetaDiscussionDTOResponse getAll(
      @PathVariable("subTopicId") int subTopicId, HttpServletResponse response) {

    try {

      return new SubTopicMetaDiscussionDTOOnFetchSuccessResponse(
          subTopicMetaDiscussionService.getAllMessages(subTopicId));

    } catch (SubTopicNotFoundException e) {

      response.setStatus(404);

      return new SubTopicMetaDiscussionDTOOnFetchFailureResponse("SubTopic not found!");
    }
  }

  @PostMapping(path = "/add")
  public SubTopicMetaDiscussionDTOResponse addMessage(
      @Valid @RequestBody SubTopicMetaDiscussionDTO subTopicMetaDiscussionDTO,
      BindingResult bindingResult,
      HttpServletResponse response)
      throws InvalidRequestFormatException {

    try {

      if (bindingResult.hasErrors()) {

        throw new InvalidRequestFormatException();
      }

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
      @PathVariable("metaDiscussId") int metaDiscussId, HttpServletResponse response) {

    try {

      subTopicMetaDiscussionService.deleteMessage(metaDiscussId);

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
