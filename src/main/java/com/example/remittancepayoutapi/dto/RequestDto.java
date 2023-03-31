package com.example.remittancepayoutapi.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RequestDto implements Serializable {

    private String publickey;
    private Transaction transaction;
    private Order order;
    private Source source;
}
