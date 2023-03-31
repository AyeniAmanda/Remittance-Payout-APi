package com.example.remittancepayoutapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ValidateRequestDto {
    @NotBlank(message = "supply a public key")
    private String publickey;
    private Source source;
    private Order order;
}
