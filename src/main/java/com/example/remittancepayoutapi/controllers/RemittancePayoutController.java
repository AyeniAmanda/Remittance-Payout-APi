package com.example.remittancepayoutapi.controllers;

import com.example.remittancepayoutapi.dto.PayOutStatusResponse;
import com.example.remittancepayoutapi.dto.RequestDto;
import com.example.remittancepayoutapi.dto.Response;
import com.example.remittancepayoutapi.enums.Operation;
import com.example.remittancepayoutapi.service.RemittancePayoutApiService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("payout")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "This is a bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "415", description = "The MediaType is unsupported."),
        @ApiResponse(responseCode = "500", description = "The server is down, please make sure that the Application is running")
})
public class RemittancePayoutController {
    private final RemittancePayoutApiService remittancePayoutApiService;

    @PostMapping
    public Mono<ResponseEntity<Response>> payout(@RequestBody RequestDto body) {
        Optional<Operation> optional = Optional.ofNullable(body.getSource().getOperation());
        optional.orElseThrow(RuntimeException::new);

        return remittancePayoutApiService.payout(body);
    }

    @GetMapping(value = "status")
    public Mono<PayOutStatusResponse> checkStatus(@RequestParam("reference") String reference) {
        return remittancePayoutApiService.checkStatus(reference).map(ResponseEntity::getBody);

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
