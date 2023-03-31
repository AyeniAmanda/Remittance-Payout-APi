package com.example.remittancepayoutapi.service;

import com.example.remittancepayoutapi.dto.ValidateRequestDto;
import com.example.remittancepayoutapi.dto.ValidateResponseDto;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<ValidateResponseDto> validateCustomer(ValidateRequestDto validateRequestDto);

}
