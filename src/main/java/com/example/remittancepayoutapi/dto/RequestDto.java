package com.example.remittancepayoutapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RequestDto {

    private String publickey;
    private Transaction transaction;
    private Order order;
    private Source source;
}
