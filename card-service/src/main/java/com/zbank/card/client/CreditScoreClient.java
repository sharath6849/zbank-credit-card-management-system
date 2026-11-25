package com.zbank.card.client;

import com.zbank.card.dto.CreditScoreRequest;
import com.zbank.card.dto.CreditScoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CreditScoreClient {

    private final RestClient restClient;

    public CreditScoreResponse calculateScore(
            CreditScoreRequest request) {

        return restClient.post()
                .uri(
                        "http://localhost:8082/api/v1/credit-score/calculate"
                )
                .body(request)
                .retrieve()
                .body(CreditScoreResponse.class);
    }
}