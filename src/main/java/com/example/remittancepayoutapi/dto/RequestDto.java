package com.example.remittancepayoutapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

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
