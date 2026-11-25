package com.zbank.creditscore.service;

import com.zbank.creditscore.client.CustomerClient;
import com.zbank.creditscore.dto.CreditScoreRequest;
import com.zbank.creditscore.dto.CreditScoreResponse;
import com.zbank.creditscore.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditScoreServiceImpl
        implements CreditScoreService {

    private final CustomerClient customerClient;

    @Override
    public CreditScoreResponse calculateCreditScore(
            CreditScoreRequest request) {

        CustomerResponse customer =
                customerClient.getCustomerByDocumentNumber(
                        request.documentNumber());

        if (customer.creditScore() != null) {

            return new CreditScoreResponse(
                    customer.creditScore());
        }

        Integer score;

        if (request.existingCreditCards() >= 2) {
            score = 300;
        }
        else if (customer.annualSalary()
                .doubleValue() > 200000) {

            score = 500;
        }
        else if (customer.annualSalary()
                .doubleValue() > 50000) {

            score = 150;
        }
        else {
            score = 50;
        }

        customerClient.updateCreditScore(
                customer.id(),
                score
        );


        return new CreditScoreResponse(score);
    }
}