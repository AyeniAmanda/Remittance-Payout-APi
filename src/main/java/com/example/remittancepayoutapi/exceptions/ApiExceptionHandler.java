package com.example.remittancepayoutapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<ApiException> handleApiBadRequestException(BadRequestException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(e.getMessage(),
                false, status);

        return new ResponseEntity<>(apiException, status);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ApiException> handleApiResourceNotFoundException(ResourceNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ApiException apiException = new ApiException(ex.getMessage(),
                false, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(apiException, status);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<ApiException> handleUnauthorizedException(UnauthorizedException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ApiException apiException = new ApiException(ex.getMessage(),
                false, status);

        return new ResponseEntity<>(apiException, status);
    }
}
