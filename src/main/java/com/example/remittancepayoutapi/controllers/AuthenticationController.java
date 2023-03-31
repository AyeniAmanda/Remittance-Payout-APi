package com.example.remittancepayoutapi.controllers;

import com.example.remittancepayoutapi.auth.AuthenticationService;
import com.example.remittancepayoutapi.dto.AuthCredential;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@ApiResponses
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public Mono<ResponseEntity<AuthCredential>> generateToken() {
        return authenticationService.authenticate();
    }
}
