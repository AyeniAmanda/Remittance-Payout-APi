package com.example.remittancepayoutapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    @NotBlank(message = "reference must be provided!")
    private String reference;
    private String linkingreference;
}
