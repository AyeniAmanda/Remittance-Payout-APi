package com.example.remittancepayoutapi.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Lock implements Serializable {
    private String status;
    private String by;
    private long time;
    private Order order;
    private Source source;
}
