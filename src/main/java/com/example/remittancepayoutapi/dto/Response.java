package com.example.remittancepayoutapi.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Response {

    private String code;
    private String message;
    private Transaction transaction;
}
