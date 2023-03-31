package com.example.remittancepayoutapi.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PayOutStatusResponse {
    private String code;
    private String message;
    private Transaction transaction;
    private Lock lock;
}
