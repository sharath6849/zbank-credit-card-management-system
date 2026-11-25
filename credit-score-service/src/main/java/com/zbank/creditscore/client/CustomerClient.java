package com.zbank.creditscore.client;

import com.zbank.creditscore.dto.CreditScoreUpdateRequest;
import com.zbank.creditscore.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CustomerClient {

    private final RestClient restClient;

    public CustomerResponse getCustomerByDocumentNumber(
            String documentNumber) {

        return restClient.get()
                .uri(
                        "http://localhost:8081/api/v1/customers/document/{documentNumber}",
                        documentNumber
                )
                .retrieve()
                .body(CustomerResponse.class);
    }

    public void updateCreditScore(
            Long customerId,
            Integer creditScore) {

        restClient.put()
                .uri(
                        "http://localhost:8081/api/v1/customers/{id}/credit-score",
                        customerId
                )
                .body(
                        new CreditScoreUpdateRequest(
                                creditScore
                        )
                )
                .retrieve()
                .toBodilessEntity();
    }
}