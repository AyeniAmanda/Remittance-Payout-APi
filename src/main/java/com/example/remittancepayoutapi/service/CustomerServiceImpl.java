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
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @Value("${seerbit.baseUrl}")
    private String BASE_URL;

    private final AuthenticationService authenticationService;
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<ValidateResponseDto> validateCustomer(ValidateRequestDto validateRequestDto) {

        URI uri = getURI("/customer/validate");
        HttpHeaders httpHeaders = getHeaders();
        HttpEntity<ValidateRequestDto> httpEntity = new HttpEntity<>(validateRequestDto, httpHeaders);
        ResponseEntity<ValidateResponseDto> exchange = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, ValidateResponseDto.class);

        if (exchange.getBody() == null ) {
            throw new ResourceNotFoundException("Resource not found");
        }

        if (!"Successful".equals(exchange.getBody().getMessage())) {
            throw new BadRequestException(exchange.getBody().getMessage());
        }
        return exchange;
    }

    private HttpHeaders getHeaders() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String validToken = authenticationService.getValidToken();
        httpHeaders.add("Authorization", "Bearer " + validToken);
        return httpHeaders;

    }
    private URI getURI(String url) {
        URI uri;
        try {
            uri = new URI(BASE_URL.concat(url));
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
        return uri;
    }
}
