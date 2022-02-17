package com.musala.drone.commons.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .timeStamp(new Date())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public final ResponseEntity<Object> ObjectNotFoundException(ObjectNotFoundException ex, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .timeStamp(new Date())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> MethodNotAllowedE(BadRequestException ex, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .timeStamp(new Date())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
