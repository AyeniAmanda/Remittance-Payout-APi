package com.example.remittancepayoutapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Source {

    private RemittancePayout sender;
    private RemittancePayout recipient;
    private Operation operation;
}
