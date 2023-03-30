package com.example.remittancepayoutapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response extends BaseDtoEntity{

    private String code;
    private String message;
    private Transaction transaction;
}
