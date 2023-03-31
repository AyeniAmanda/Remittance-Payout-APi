package com.example.remittancepayoutapi.service;

import com.example.remittancepayoutapi.dto.RequestDto;
import com.example.remittancepayoutapi.dto.Response;
import com.example.remittancepayoutapi.dto.PayOutStatusResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface RemittancePayoutApiService {
    Mono<ResponseEntity<Response>> payout(RequestDto body);

    ResponseEntity<PayOutStatusResponse> checkStatus(String reference);

    ResponseEntity<Response> cancel(RequestDto body);

    ResponseEntity<Response> update(RequestDto body);

    ResponseEntity<Response> create(RequestDto body);
}