package com.zbank.card.client;

import com.zbank.card.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CustomerClient {

    private final RestClient restClient;

    public CustomerResponse getCustomer(
            String documentNumber) {

        return restClient.get()
                .uri(
                        "http://localhost:8081/api/v1/customers/document/{documentNumber}",
                        documentNumber
                )
                .retrieve()
                .body(CustomerResponse.class);
    }
}