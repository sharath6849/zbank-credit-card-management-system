package com.zbank.creditscore.controller;

import com.zbank.creditscore.dto.CreditScoreRequest;
import com.zbank.creditscore.dto.CreditScoreResponse;
import com.zbank.creditscore.service.CreditScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/credit-score")
@RequiredArgsConstructor
public class CreditScoreController {

    private final CreditScoreService service;

    @PostMapping("/calculate")
    public CreditScoreResponse calculateCreditScore(
            @Valid
            @RequestBody CreditScoreRequest request) {

        return service.calculateCreditScore(request);
    }
}