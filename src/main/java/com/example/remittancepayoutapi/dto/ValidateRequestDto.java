package com.example.remittancepayoutapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ValidateRequestDto implements Serializable {
    @NotBlank(message = "supply a public key")
    private String publickey;
    private Source source;
    private Order order;
}
