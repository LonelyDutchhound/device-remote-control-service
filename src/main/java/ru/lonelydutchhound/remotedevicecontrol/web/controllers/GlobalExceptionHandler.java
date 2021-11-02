package ru.lonelydutchhound.remotedevicecontrol.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;
import ru.lonelydutchhound.remotedevicecontrol.exceptions.IncorrectDeviceStateException;
import ru.lonelydutchhound.remotedevicecontrol.exceptions.NotFoundException;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.responses.ApiError;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GlobalExceptionHandler {
  Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler({NotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ApiError> handleNotFoundException (NotFoundException notFoundException, WebRequest webRequest) {
    return handleExceptionInternal(notFoundException, new ApiError(notFoundException.getMessage()), HttpStatus.NOT_FOUND, webRequest);
  }

  @ExceptionHandler(IncorrectDeviceStateException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<ApiError> handleIncorrectDeviceStateException (IncorrectDeviceStateException incorrectDeviceStateException, WebRequest webRequest) {
    return handleExceptionInternal(incorrectDeviceStateException, new ApiError(incorrectDeviceStateException.getMessage()), HttpStatus.CONFLICT, webRequest);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiError> handleMethodArgumentNotValidException (MethodArgumentNotValidException methodArgumentNotValidException, WebRequest webRequest) {
    LOGGER.error(methodArgumentNotValidException.getMessage());
    return handleExceptionInternal(methodArgumentNotValidException, new ApiError(methodArgumentNotValidException.getMessage()), HttpStatus.BAD_REQUEST, webRequest);
  }

  @ExceptionHandler({DataIntegrityViolationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiError> handleDataIntegritySystemException (DataIntegrityViolationException exception, WebRequest webRequest) {
    Throwable cause = exception.getCause();
    if (cause instanceof org.hibernate.exception.ConstraintViolationException) {
      org.hibernate.exception.ConstraintViolationException ce = (org.hibernate.exception.ConstraintViolationException) cause;
      String errorMessage = ce.getSQLException().getMessage();

      LOGGER.error("Data constraints violated: " + errorMessage);
      return handleExceptionInternal(exception, new ApiError(errorMessage), HttpStatus.BAD_REQUEST, webRequest);
    }
    return handleExceptionInternal(exception, new ApiError(exception.getMessage()), HttpStatus.BAD_REQUEST, webRequest);
  }

  @ExceptionHandler({javax.validation.ConstraintViolationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiError> handleConstraintViolationException (javax.validation.ConstraintViolationException exception, WebRequest webRequest) {
    String errorMessage = exception.getMessage();

    LOGGER.error("Data constraints violated: " + errorMessage);
    return handleExceptionInternal(exception, new ApiError(errorMessage), HttpStatus.BAD_REQUEST, webRequest);
  }


  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ApiError> handleAllUncaughtException (Exception exception, WebRequest request) {
    return handleExceptionInternal(exception, new ApiError("Unknown error occurred"), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  protected ResponseEntity<ApiError> handleExceptionInternal (Exception ex, ApiError body, HttpStatus status, WebRequest request) {
    if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
      request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
    }

    return new ResponseEntity<>(body, status);
  }
}