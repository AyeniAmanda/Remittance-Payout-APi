package com.example.remittancepayoutapi.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Response implements Serializable {

    private String code;
    private String message;
    private Transaction transaction;
}
