package com.example.remittancepayoutapi.service;

import com.example.remittancepayoutapi.dto.ValidateRequestDto;
import com.example.remittancepayoutapi.dto.ValidateResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface CustomerValidatorService {
    Mono<ResponseEntity<ValidateResponseDto>> validateCustomer(ValidateRequestDto validateRequestDto);
}