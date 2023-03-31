package com.example.remittancepayoutapi.service;

import com.example.remittancepayoutapi.dto.RequestDto;
import com.example.remittancepayoutapi.dto.Response;
import com.example.remittancepayoutapi.dto.StatusResponse;
import org.springframework.http.ResponseEntity;

public interface RemittancePayoutApiService {
    ResponseEntity<Response> payout(RequestDto body);

    ResponseEntity<StatusResponse> checkStatus(String reference);

    ResponseEntity<Response> cancel(RequestDto body);

    ResponseEntity<Response> update(RequestDto body);

    ResponseEntity<Response> create(RequestDto body);
}