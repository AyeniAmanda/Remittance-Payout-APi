package com.example.remittancepayoutapi.dto;

import com.example.remittancepayoutapi.enums.Currency;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private BigDecimal amount;
    private String description;
    private String reason;
    private Currency currency;
    private String country;
    private String secretquestion;
    private String secretanswer;
}
