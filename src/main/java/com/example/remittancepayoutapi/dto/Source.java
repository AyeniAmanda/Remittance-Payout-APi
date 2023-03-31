package com.example.remittancepayoutapi.dto;

import com.example.remittancepayoutapi.enums.Operation;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Source implements Serializable {

    private RemittancePayout sender;
    private RemittancePayout recipient;
    private Operation operation;
}
