package com.example.remittancepayoutapi.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ValidateResponseDto implements Serializable {
    private String code;
    private String message;
    private String cutomername;
}
