package com.example.remittancepayoutapi.auth;


import com.example.remittancepayoutapi.dto.AuthCredential;
import com.example.remittancepayoutapi.exceptions.CustomWebClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    @Value("${seerbit.baseUrl}")
    private String BASE_URL;
    @Value("${seerbit.grantType}")
    private String GRANT_TYPE;

    @Value("${seerbit.clientId}")
    private String CLIENT_ID;

    @Value("${seerbit.clientSecret}")
    private String CLIENT_SECRET;

    public Mono<ResponseEntity<AuthCredential>> authenticate() {
        URI uri;
        try {
            uri = new URI(BASE_URL + "/auth");

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("grant_type", GRANT_TYPE);
            map.add("client_id", CLIENT_ID);
            map.add("client_secret", CLIENT_SECRET);

            WebClient webClient = WebClient.create();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            return webClient.method(HttpMethod.POST)
                    .uri(uri)
                    .headers(headers -> headers.addAll(httpHeaders))
                    .body(BodyInserters.fromFormData(map))
                    .retrieve()
                    .toEntity(AuthCredential.class)
                    .map(exchange -> {
                        AuthCredential authCredential = exchange.getBody();
                        if (authCredential != null) {
                            authCredential.setCreated_at(System.currentTimeMillis());
                        }
                        return exchange;
                    });
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception.getMessage());
        } catch (WebClientResponseException exception) {
            throw new CustomWebClientException(HttpStatus.valueOf(exception.getStatusCode().value()), exception.getMessage());
        }
    }

    public synchronized String getValidToken() {
        return authenticate().map(token -> token.getBody().getAccess_token()).block();
    }
}