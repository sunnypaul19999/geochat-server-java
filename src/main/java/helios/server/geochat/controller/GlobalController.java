package helios.server.geochat.controller;

import helios.server.geochat.dto.response.globalresponse.InvalidRequestFormatGlobalResponse;
import helios.server.geochat.exceptions.dtoException.InvalidRequestFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(
      value = {
        HttpMessageNotReadableException.class,
        HttpMediaTypeNotAcceptableException.class,
        InvalidRequestFormatException.class
      })
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public InvalidRequestFormatGlobalResponse invalidDataRequest(Exception e) {

    logger.trace("Rejecting DTO. Has binding errors.", e);

    InvalidRequestFormatGlobalResponse invalidRequestFormatGlobalResponse =
        new InvalidRequestFormatGlobalResponse();

    return invalidRequestFormatGlobalResponse;
  }
}
