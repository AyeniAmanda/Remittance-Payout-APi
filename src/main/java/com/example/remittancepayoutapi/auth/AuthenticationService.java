package com.example.remittancepayoutapi.auth;


import com.example.remittancepayoutapi.dto.AuthCredential;
import com.example.remittancepayoutapi.exceptions.BadRequestException;
import com.example.remittancepayoutapi.exceptions.ResourceNotFoundException;
import com.example.remittancepayoutapi.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

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
    private final RestTemplate restTemplate;

    private AuthCredential authCredential;


    public ResponseEntity<AuthCredential> authenticate() {
        URI uri;
        try {
            uri = new URI(BASE_URL + "/auth");
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("grant_type", GRANT_TYPE);
            map.add("client_id", CLIENT_ID);
            map.add("client_secret", CLIENT_SECRET);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, httpHeaders);

            ResponseEntity<AuthCredential> exchange = restTemplate.exchange(uri, HttpMethod.POST, entity, AuthCredential.class);

            authCredential = exchange.getBody();

            if (authCredential == null) {
                throw new ResourceNotFoundException("Resource does not exist");
            }
            System.out.println("=====>authCredentials: " + authCredential);
            authCredential.setCreated_at(System.currentTimeMillis());

            return exchange;


        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception.getMessage());

        } catch (UnauthorizedException | HttpStatusCodeException exception) {
            throw new UnauthorizedException(exception.getMessage());
        }

    }

    public synchronized String getValidToken()
    {

        AuthCredential credentials = authenticate().getBody();
        System.out.println("=========> credentials: " + credentials);
        if (credentials == null) {
            throw new BadRequestException("Invalid token");
        }
        return credentials.getAccess_token();
    }

}
