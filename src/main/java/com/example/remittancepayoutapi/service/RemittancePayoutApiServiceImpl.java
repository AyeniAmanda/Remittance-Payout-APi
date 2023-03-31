package com.example.remittancepayoutapi.service;


import com.example.remittancepayoutapi.auth.AuthenticationService;
import com.example.remittancepayoutapi.dto.PayOutStatusResponse;
import com.example.remittancepayoutapi.dto.RequestDto;
import com.example.remittancepayoutapi.dto.Response;
import com.example.remittancepayoutapi.exceptions.BadRequestException;
import com.example.remittancepayoutapi.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;


@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:env.properties")
@Service
public class RemittancePayoutApiServiceImpl implements RemittancePayoutApiService {

    private final AuthenticationService authentication;
    @Value("${seerbit.baseUrl}")
    private String BASE_URL;

    @Override
    public Mono<ResponseEntity<Response>> payout(RequestDto body) {
        URI uri = getURI("/account/payout");
        WebClient webClient = WebClient.create();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return webClient.method(HttpMethod.POST)
                .uri(uri)
                .headers(headers -> headers.addAll(getHeaders()))
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Response.class)
                .map(response -> {
                    if (!"Successful" .equalsIgnoreCase(response.getMessage())) {
                        throw new BadRequestException(response.getMessage());
                    }
                    return ResponseEntity.ok(response);
                });
    }

    @Override
    public Mono<ResponseEntity<PayOutStatusResponse>> checkStatus(String reference) {
        URI uri = getURI("/payout/status?reference=" + reference);
        WebClient webClient = WebClient.create();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return webClient.method(HttpMethod.GET)
                .uri(uri)
                .headers(headers -> headers.addAll(getHeaders()))
                .retrieve()
                .bodyToMono(PayOutStatusResponse.class)
                .map(response -> {
                    if (response == null) {
                        throw new ResourceNotFoundException("Resource not found");
                    }
                    if (!"Successful" .equalsIgnoreCase(response.getMessage())) {
                        throw new BadRequestException(response.getMessage());
                    }
                    return ResponseEntity.ok(response);
                });
    }


    private Mono<Response> getResponse(RequestDto body, URI uri, String message) {
        WebClient webClient = WebClient.create();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return webClient.method(HttpMethod.POST)
                .uri(uri)
                .headers(headers -> headers.setBearerAuth(authentication.getValidToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(Response.class)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Resource does not exist")))
                .flatMap(response -> {
                    if (!message.equalsIgnoreCase(response.getMessage())) {
                        return Mono.error(new BadRequestException(response.getMessage()));
                    }
                    return Mono.just(response);
                });
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
    public Mono<Response> cancel(RequestDto body) {
        URI uri;
        try {
            uri = new URI(BASE_URL.concat("/payout/cancel"));
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
        return getResponse(body, uri, "Cancelled");
    }

    @Override
    public Mono<Response> update(RequestDto body) {
        URI uri;
        try {
            uri = new URI(BASE_URL.concat("/payout/update"));
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
        return getResponse(body, uri, "Successful");
    }


    @Override
    public Mono<Response> create(RequestDto body) {
        URI uri = getURI("/payout/create");

        return getResponse(body, uri, "Successful");
    }
}