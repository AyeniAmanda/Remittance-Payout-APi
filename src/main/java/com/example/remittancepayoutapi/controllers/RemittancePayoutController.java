package com.example.remittancepayoutapi.controllers;

import com.example.remittancepayoutapi.dto.RequestDto;
import com.example.remittancepayoutapi.dto.Response;
import com.example.remittancepayoutapi.dto.StatusResponse;
import com.example.remittancepayoutapi.enums.Operation;
import com.example.remittancepayoutapi.service.RemittancePayoutApiService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("payout")
@RequiredArgsConstructor
@ApiResponses
public class RemittancePayoutController {
    private final RemittancePayoutApiService remittancePayoutApiService;

    @PostMapping
    public ResponseEntity<Response> payout(@RequestBody RequestDto body) {
        Optional<Operation> optional = Optional.ofNullable(body.getSource().getOperation());
        optional.orElseThrow(RuntimeException::new);

        return remittancePayoutApiService.payout(body);
    }

    @GetMapping(value = "status")
    public StatusResponse checkStatus(@RequestParam("reference") String reference) {

        return remittancePayoutApiService.checkStatus(reference).getBody();
    }

    @PostMapping("cancel")
    public ResponseEntity<Response> cancel(@RequestBody RequestDto body) {

        return remittancePayoutApiService.cancel(body);
    }

    @PostMapping("update")
    public ResponseEntity<Response> update(@RequestBody RequestDto body) {
        return remittancePayoutApiService.update(body);
    }

    @PostMapping("create")
    public ResponseEntity<Response> create(@RequestBody RequestDto body) {

        return remittancePayoutApiService.create(body);
    }

}
