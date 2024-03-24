package org.scottg.branch.homework.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.CompletionException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgs(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "The provided argument was invalid.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = {HttpStatusCodeException.class})
    protected ResponseEntity<Object> handleClientErrors(
            HttpStatusCodeException ex, WebRequest request) {
        String bodyOfResponse = ex.getStatusCode().toString();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), ex.getStatusCode(), request);
    }

    @ExceptionHandler(value
            = {CompletionException.class})
    protected ResponseEntity<Object> handleCompletionException(
            CompletionException ex, WebRequest request) {
        HttpStatusCode statusCode = HttpStatusCode.valueOf(500);
        String statusText = "Internal Server Exception";
        Throwable cause = ex.getCause();
        if (cause instanceof HttpStatusCodeException) {
            HttpStatusCodeException http = (HttpStatusCodeException) cause;
            statusCode = http.getStatusCode();
            statusText = http.getStatusText();
        }
        return handleExceptionInternal(ex, statusText,
                new HttpHeaders(), statusCode, request);
    }


    @ExceptionHandler(value
            = {ServerErrorException.class})
    protected ResponseEntity<Object> handleServerEx(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Internal Server Exception";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
