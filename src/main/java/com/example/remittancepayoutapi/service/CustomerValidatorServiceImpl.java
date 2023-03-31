package com.example.remittancepayoutapi.service;


import com.example.remittancepayoutapi.auth.AuthenticationService;
import com.example.remittancepayoutapi.dto.ValidateRequestDto;
import com.example.remittancepayoutapi.dto.ValidateResponseDto;
import com.example.remittancepayoutapi.exceptions.BadRequestException;
import com.example.remittancepayoutapi.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class CustomerValidatorServiceImpl implements CustomerValidatorService {

    private final AuthenticationService authenticationService;
    @Value("${seerbit.baseUrl}")
    private String BASE_URL;

    @Override
    public Mono<ResponseEntity<ValidateResponseDto>> validateCustomer(ValidateRequestDto validateRequestDto) {
        String uri = BASE_URL + "/customer/validate";
        String validToken = authenticationService.getValidToken();
        WebClient webClient = WebClient.create();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return webClient.method(HttpMethod.POST)
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validateRequestDto)
                .retrieve()
                .toEntity(ValidateResponseDto.class)
                .map(responseEntity -> {
                    ValidateResponseDto responseBody = responseEntity.getBody();
                    if (responseBody == null) {
                        throw new ResourceNotFoundException("Resource not found");
                    } else if (!"Successful".equals(responseBody.getMessage())) {
                        throw new BadRequestException(responseBody.getMessage());
                    }
                    return responseEntity;
                });
    }
}