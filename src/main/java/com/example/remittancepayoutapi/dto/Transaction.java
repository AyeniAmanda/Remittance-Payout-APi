package com.example.remittancepayoutapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Transaction implements Serializable {
    @NotBlank(message = "reference must be provided!")
    private String reference;
    private String linkingreference;
}
