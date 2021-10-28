package ru.lonelydutchhound.remotedevicecontrol.web.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;
import ru.lonelydutchhound.remotedevicecontrol.exceptions.*;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.responses.ApiError;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({DeviceNotFoundException.class, ProgramNotFoundException.class, DeviceBusyException.class, DeviceErrorProgramStatusException.class, DevicePowerOffException.class})
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if ((ex instanceof DeviceNotFoundException) || (ex instanceof ProgramNotFoundException)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            RuntimeException nfe = (RuntimeException) ex;

            return handleNotFoundException(nfe, headers, status, request);
        } else if ((ex instanceof DevicePowerOffException) || (ex instanceof DeviceBusyException) || (ex instanceof DeviceErrorProgramStatusException)) {
            HttpStatus status = HttpStatus.CONFLICT;
            RuntimeException dpoe = (RuntimeException) ex;

            return handleDevicePowerOffException(dpoe, headers, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    private ResponseEntity<ApiError> handleDevicePowerOffException(RuntimeException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new ApiError(ex.getMessage()), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleNotFoundException(RuntimeException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new ApiError(ex.getMessage()), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}