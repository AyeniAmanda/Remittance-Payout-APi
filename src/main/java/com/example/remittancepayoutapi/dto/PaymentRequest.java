package com.example.remittancepayoutapi.dto;

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
public class PaymentRequest {
    @NotNull(message = "Amount is required!")
    private BigDecimal amount;
    private String description;
    private String reason;
    @NotNull(message = "Currency is required!")
    private Currency currency;
    @NotEmpty(message = "Country is required!")
    private String country;
    private String secretQuestion;
    private String secretAnswer;
}
