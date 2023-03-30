package com.example.remittancepayoutapi.service;

import com.example.remittancepayoutapi.dto.AuthCredential;
import com.example.remittancepayoutapi.dto.BaseDtoEntity;
import com.example.remittancepayoutapi.dto.Response;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public interface RemittancePayoutApiService {
    String getValidToken();
    ResponseEntity<AuthCredential> authenticate(URI uri);
    ResponseEntity<Response> payout(BaseDtoEntity requestBody);
    ResponseEntity<Response> cashPickUp(BaseDtoEntity requestBody);

}
