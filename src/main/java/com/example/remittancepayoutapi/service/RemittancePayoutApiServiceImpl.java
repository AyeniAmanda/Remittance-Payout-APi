package com.example.remittancepayoutapi.service;

import com.example.remittancepayoutapi.dto.AuthCredential;
import com.example.remittancepayoutapi.dto.BaseDtoEntity;
import com.example.remittancepayoutapi.dto.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:env.properties")
@Service
public class RemittancePayoutApiServiceImpl implements RemittancePayoutApiService {

    @Value("${seerbit.baseUrl}")
    private String baseUrl;

    @Value("${seerbit.grantType}")
    private String grantType;

    @Value("${seerbit.clientId}")
    private String clientId;

    @Value("${seerbit.clientSecret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    private AuthCredential authCredential;

    @Override
    public synchronized String getValidToken() {
        URI uri = getUri("/oauth/token");

        try {
            if (authCredential == null || isTokenExpired()) {
                log.info("Requesting token");
                authCredential = authenticate(uri).getBody();
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        return authCredential.getAccessToken();
    }

    private boolean isTokenExpired() {
        long currentTimeMillis = System.currentTimeMillis();
        long timeSinceTokenRequested = currentTimeMillis - authCredential.getRequestedAt();
        long timeRemainingUntilExpiration = authCredential.getExpiresIn() - timeSinceTokenRequested;
        boolean isExpired = timeRemainingUntilExpiration <= 0;

        if (isExpired) {
            log.info("Token expired, requesting new token");
        }

        return isExpired;
    }

    @Override
    public ResponseEntity<AuthCredential> authenticate(URI uri) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);

        return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, AuthCredential.class);
    }

    @Override
    public ResponseEntity<Response> payout(BaseDtoEntity requestBody) {
        URI uri = getUri("/payout/create");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + getValidToken());

        HttpEntity<BaseDtoEntity> httpEntity = new HttpEntity<>(requestBody, httpHeaders);

        return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Response.class);
    }

    @Override
    public ResponseEntity<Response> cashPickUp(BaseDtoEntity requestBody) {
        URI uri = getUri("/cashPickup/create");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + getValidToken());

        HttpEntity<BaseDtoEntity> httpEntity = new HttpEntity<>(requestBody, httpHeaders);

        return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Response.class);
    }

    private URI getUri(String endpoint) {
        URI uri;
        try {
            uri = new URI(baseUrl.concat(endpoint));
        } catch (URISyntaxException exception) {
            log.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
        return uri;
    }
}