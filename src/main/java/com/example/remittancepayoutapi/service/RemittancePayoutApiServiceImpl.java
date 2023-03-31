package com.example.remittancepayoutapi.service;


import com.example.remittancepayoutapi.auth.AuthenticationService;
import com.example.remittancepayoutapi.dto.RequestDto;
import com.example.remittancepayoutapi.dto.Response;
import com.example.remittancepayoutapi.dto.StatusResponse;
import com.example.remittancepayoutapi.exceptions.BadRequestException;
import com.example.remittancepayoutapi.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;


@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:env.properties")
@Service
public class RemittancePayoutApiServiceImpl implements RemittancePayoutApiService {

    @Value("${seerbit.baseUrl}")
    private String BASE_URL;

    private final AuthenticationService authentication;
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<Response> payout(RequestDto body) {
        URI uri = getURI("/account/payout");
        return getResponseResponseEntity(body, uri, "Successful");
    }

    @Override
    public ResponseEntity<StatusResponse> checkStatus(String reference) {
        URI uri = getURI("/payout/status?reference=" + reference);
        HttpHeaders httpHeaders = getHeaders();

        HttpEntity<String> httpEntity = new HttpEntity<>(reference, httpHeaders);
        ResponseEntity<StatusResponse> exchange = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, StatusResponse.class);

        if (exchange.getBody() == null ) {
            throw new ResourceNotFoundException("Resource not found");
        }

        if (!"Successful".equalsIgnoreCase(exchange.getBody().getMessage())) {
            throw new BadRequestException(exchange.getBody().getMessage());
        }

        return exchange;
    }


    private ResponseEntity<Response> getResponseResponseEntity(RequestDto body, URI uri, String message) {
        HttpHeaders httpHeaders = getHeaders();
        HttpEntity<RequestDto> httpEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<Response> exchange = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Response.class);
        if (exchange.getBody() == null ) {
            throw new ResourceNotFoundException("Resource does not exist");
        }

        System.out.println("======> exchange: " + exchange.getBody());

        if (!message.equalsIgnoreCase(exchange.getBody().getMessage())) {
            throw new BadRequestException(exchange.getBody().getMessage());
        }
        return exchange;
    }


    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String validToken = authentication.getValidToken();
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

    @Override
    public ResponseEntity<Response> cancel(RequestDto body) {
        URI uri = getURI("/payout/cancel");
        return getResponseResponseEntity(body, uri, "Cancelled");
    }

    @Override
    public ResponseEntity<Response> update(RequestDto body) {
        URI uri = getURI("/payout/update");

        return getResponseResponseEntity(body, uri, "Successful");
    }

    @Override
    public ResponseEntity<Response> create(RequestDto body) {
        URI uri = getURI("/payout/create");

        return getResponseResponseEntity(body, uri, "Successful");
    }

}