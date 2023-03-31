package com.example.remittancepayoutapi.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PayOutStatusResponse implements Serializable {
    private String code;
    private String message;
    private Transaction transaction;
    private Lock lock;
}
