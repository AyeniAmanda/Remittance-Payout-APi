package com.example.remittancepayoutapi.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiException {

    private String message;

    private boolean success;

    private HttpStatus httpStatus;
}