package com.example.remittancepayoutapi.dto;

import com.example.remittancepayoutapi.enums.Operation;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Source {

    private RemittancePayout sender;
    private RemittancePayout recipient;
    private Operation operation;
}
