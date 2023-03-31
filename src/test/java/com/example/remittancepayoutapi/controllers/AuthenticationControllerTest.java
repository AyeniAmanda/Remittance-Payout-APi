package com.example.remittancepayoutapi.controllers;

import com.example.remittancepayoutapi.auth.AuthenticationService;
import com.example.remittancepayoutapi.dto.AuthCredential;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;

@WebFluxTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void testGenerateToken() {

        AuthCredential credential = new AuthCredential();
        credential.setAccess_token("12345");

        Mockito.when(authenticationService.authenticate())
                .thenReturn(Mono.just(ResponseEntity.ok().body(credential)));

        webTestClient.post()
                .uri("/auth")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(AuthCredential.class)
                .value(response -> {
                    assertEquals("12345", response.getAccess_token());
                });
    }
}
