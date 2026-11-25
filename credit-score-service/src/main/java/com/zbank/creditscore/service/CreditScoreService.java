package com.zbank.creditscore.service;

import com.zbank.creditscore.dto.CreditScoreRequest;
import com.zbank.creditscore.dto.CreditScoreResponse;

public interface CreditScoreService {

    CreditScoreResponse calculateCreditScore(
            CreditScoreRequest request);
}