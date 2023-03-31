package com.example.remittancepayoutapi.controllers;


import com.example.remittancepayoutapi.dto.ValidateRequestDto;
import com.example.remittancepayoutapi.dto.ValidateResponseDto;
import com.example.remittancepayoutapi.service.CustomerService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
@ApiResponses
public class CustomerValidatorController {

    private final CustomerService customerService;

    @PostMapping(value = "validate")
    public ValidateResponseDto validateCustomer(
            @RequestBody @Valid ValidateRequestDto validateRequestDto
    ) {
        ResponseEntity<ValidateResponseDto> customerValidateResponseResponseEntity = customerService
                .validateCustomer(validateRequestDto);
        System.out.println("customerValidateResponseResponseEntity: " + customerValidateResponseResponseEntity);
        return customerValidateResponseResponseEntity.getBody();
    }
}
