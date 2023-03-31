package com.example.remittancepayoutapi.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ValidateResponseDto {
    private String code;
    private String message;
    private String cutomername;

}
