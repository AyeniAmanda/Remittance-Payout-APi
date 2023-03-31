package com.example.remittancepayoutapi.exceptions;

import org.springframework.http.HttpStatus;
public class CustomWebClientException extends RuntimeException {
    private final HttpStatus statusCode;

    public CustomWebClientException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}