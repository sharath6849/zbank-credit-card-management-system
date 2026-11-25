package com.zbank.creditscore.service;

import com.zbank.creditscore.client.CustomerClient;
import com.zbank.creditscore.dto.CreditScoreRequest;
import com.zbank.creditscore.dto.CreditScoreResponse;
import com.zbank.creditscore.dto.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditScoreServiceImplTest {

    @Mock
    private CustomerClient customerClient;

    @InjectMocks
    private CreditScoreServiceImpl creditScoreService;

    @Test
    void shouldReturn500WhenSalaryGreaterThan200k() {

        when(customerClient.getCustomerByDocumentNumber(any()))
                .thenReturn(
                        new CustomerResponse(
                                1L,
                                "Sharath",
                                "Katekar",
                                "test@test.com",
                                "9876543210",
                                new BigDecimal("250000"),
                                "FULL_TIME",
                                "AADHAR",
                                "123",
                                null
                        )
                );

        CreditScoreResponse response =
                creditScoreService.calculateCreditScore(
                        new CreditScoreRequest(
                                "123",
                                1
                        ));

        assertEquals(
                500,
                response.creditScore());
    }

    @Test
    void shouldReturn300WhenCardsAreTwoOrMore() {

        when(customerClient.getCustomerByDocumentNumber(any()))
                .thenReturn(
                        new CustomerResponse(
                                1L,
                                "Sharath",
                                "Katekar",
                                "test@test.com",
                                "9876543210",
                                new BigDecimal("500000"),
                                "FULL_TIME",
                                "AADHAR",
                                "123",
                                null
                        )
                );

        CreditScoreResponse response =
                creditScoreService.calculateCreditScore(
                        new CreditScoreRequest(
                                "123",
                                2
                        ));

        assertEquals(
                300,
                response.creditScore());
    }

    @Test
    void shouldReturn150WhenSalaryBetween50kAnd200k() {

        when(customerClient.getCustomerByDocumentNumber(any()))
                .thenReturn(
                        new CustomerResponse(
                                1L,
                                "Sharath",
                                "Katekar",
                                "test@test.com",
                                "9876543210",
                                new BigDecimal("100000"),
                                "FULL_TIME",
                                "AADHAR",
                                "123",
                                null
                        )
                );

        CreditScoreResponse response =
                creditScoreService.calculateCreditScore(
                        new CreditScoreRequest(
                                "123",
                                1
                        ));

        assertEquals(
                150,
                response.creditScore());
    }

    @Test
    void shouldReturn50WhenSalaryBelow50k() {

        when(customerClient.getCustomerByDocumentNumber(any()))
                .thenReturn(
                        new CustomerResponse(
                                1L,
                                "Sharath",
                                "Katekar",
                                "test@test.com",
                                "9876543210",
                                new BigDecimal("40000"),
                                "FULL_TIME",
                                "AADHAR",
                                "123",
                                null
                        )
                );

        CreditScoreResponse response =
                creditScoreService.calculateCreditScore(
                        new CreditScoreRequest(
                                "123",
                                1
                        ));

        assertEquals(
                50,
                response.creditScore());
    }
}