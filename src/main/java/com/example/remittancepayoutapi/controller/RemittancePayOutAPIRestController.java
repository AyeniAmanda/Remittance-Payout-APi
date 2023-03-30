package com.example.remittancepayoutapi.controller;


import com.example.remittancepayoutapi.dto.Operation;
import com.example.remittancepayoutapi.dto.RequestDtoEntity;
import com.example.remittancepayoutapi.dto.Response;
import com.example.remittancepayoutapi.service.RemittancePayoutApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/seerbit/api/v1")
@RestController
public class RemittancePayOutAPIRestController {

    private final RemittancePayoutApiService remittancePayoutApiService;

    @GetMapping
    public ResponseEntity<String> welcome () {
        return new ResponseEntity<>("SEERBIT Remittance Payout API Integration.", HttpStatus.OK);
    }

    @PostMapping("/account/payout")
    public ResponseEntity<Response> payout (@RequestBody RequestDtoEntity body) {
        Optional<Operation> optional = Optional.ofNullable(body.getSource().getOperation());
        optional.orElseThrow(RuntimeException::new);

        return remittancePayoutApiService.payout(body);
    }

    @PostMapping("/payout/create")
    public ResponseEntity<Response> cashPickUp (@RequestBody RequestDtoEntity body) {

        return remittancePayoutApiService.cashPickUp(body);
    }
}
