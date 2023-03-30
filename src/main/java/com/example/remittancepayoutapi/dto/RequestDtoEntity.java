package com.example.remittancepayoutapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RequestDtoEntity extends BaseDtoEntity {

    private String publicKey;
    private Transaction transaction;
    private PaymentRequest order;
    private Source source;
}
