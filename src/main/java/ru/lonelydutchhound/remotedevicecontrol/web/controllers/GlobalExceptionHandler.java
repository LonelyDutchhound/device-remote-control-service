package ru.lonelydutchhound.remotedevicecontrol.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;
import ru.lonelydutchhound.remotedevicecontrol.exceptions.IncorrectDeviceStateException;
import ru.lonelydutchhound.remotedevicecontrol.exceptions.NotFoundException;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.responses.ApiError;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException notFoundException, WebRequest webRequest) {
        return handleExceptionInternal(notFoundException, new ApiError(notFoundException.getMessage()), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(IncorrectDeviceStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleIncorrectDeviceStateException(IncorrectDeviceStateException incorrectDeviceStateException, WebRequest webRequest) {
        return handleExceptionInternal(incorrectDeviceStateException, new ApiError(incorrectDeviceStateException.getMessage()), HttpStatus.CONFLICT, webRequest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, WebRequest webRequest) {
        return handleExceptionInternal(methodArgumentNotValidException, new ApiError(methodArgumentNotValidException.getMessage()), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleAllUncaughtException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, new ApiError("Unknown error occurred"), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, status);
    }
}