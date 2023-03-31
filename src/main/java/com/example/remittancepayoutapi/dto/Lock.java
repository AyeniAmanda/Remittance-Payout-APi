package com.example.remittancepayoutapi.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Lock {
    private String status;
    private String by;
    private long time;
    private Order order;
    private Source source;
}
