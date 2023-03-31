package com.example.remittancepayoutapi.controllers;


import com.example.remittancepayoutapi.dto.ValidateRequestDto;
import com.example.remittancepayoutapi.dto.ValidateResponseDto;
import com.example.remittancepayoutapi.service.CustomerValidatorService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "This is a bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "415", description = "The MediaType is unsupported."),
        @ApiResponse(responseCode = "500", description = "The server is down, please make sure that the Application is running")
})
public class CustomerValidatorController {

    private final CustomerValidatorService customerValidatorService;

    @PostMapping(value = "validate")
    public Mono<ValidateResponseDto> validateCustomer(@RequestBody @Valid ValidateRequestDto validateRequestDto) {
        return customerValidatorService.validateCustomer(validateRequestDto)
                .map(ResponseEntity::getBody);
    }
}
