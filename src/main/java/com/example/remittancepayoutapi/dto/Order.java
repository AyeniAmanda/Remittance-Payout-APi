package com.example.remittancepayoutapi.dto;

import com.example.remittancepayoutapi.enums.Currency;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Order implements Serializable {
    private BigDecimal amount;
    private String description;
    private String reason;
    private Currency currency;
    private String country;
    private String secretquestion;
    private String secretanswer;
}
