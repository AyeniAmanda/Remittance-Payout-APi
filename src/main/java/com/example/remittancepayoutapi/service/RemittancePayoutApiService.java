package com.example.remittancepayoutapi.service;

import com.example.remittancepayoutapi.dto.PayOutStatusResponse;
import com.example.remittancepayoutapi.dto.RequestDto;
import com.example.remittancepayoutapi.dto.Response;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface RemittancePayoutApiService {
    Mono<ResponseEntity<Response>> payout(RequestDto body);

    Mono<ResponseEntity<PayOutStatusResponse>> checkStatus(String reference);

    Mono<Response> cancel(RequestDto body);

    Mono<Response> update(RequestDto body);

    Mono<Response> create(RequestDto body);
}