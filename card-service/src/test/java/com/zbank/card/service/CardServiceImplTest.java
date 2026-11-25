package com.zbank.card.service;

import com.zbank.card.client.CreditScoreClient;
import com.zbank.card.client.CustomerClient;
import com.zbank.card.repository.AuditLogRepository;
import com.zbank.card.repository.CreditCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.zbank.card.dto.*;
import com.zbank.card.entity.CreditCard;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CardServiceImplTest {

    @Mock
    private CustomerClient customerClient;

    @Mock
    private CreditScoreClient creditScoreClient;

    @Mock
    private CreditCardRepository repository;

    @Mock
    private AuditLogRepository auditLogRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private CardServiceImpl cardService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDummy() {

    }
    @Test
    void shouldAllocatePlatinumCard() {

        ApplyCardRequest request =
                new ApplyCardRequest(
                        "123456789012",
                        1
                );

        when(customerClient.getCustomer(any()))
                .thenReturn(
                        new CustomerResponse(
                                1L,
                                "Sharath",
                                "Katekar",
                                "mail@test.com",
                                "9876543210",
                                null,
                                "FULL_TIME",
                                "AADHAR",
                                "123456789012",
                                500
                        ));

        when(creditScoreClient.calculateScore(any()))
                .thenReturn(
                        new CreditScoreResponse(500));

        when(encoder.encode(any()))
                .thenReturn("encryptedPin");

        when(repository.save(any()))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        ApplyCardResponse response =
                cardService.applyCard(request);

        assertEquals(
                "PLATINUM",
                response.cardType());

        assertEquals(
                40000,
                response.creditLimit());

        assertEquals(
                "APPROVED",
                response.status());
    }
    @Test
    void shouldAllocateGoldCard() {

        ApplyCardRequest request =
                new ApplyCardRequest(
                        "123456789012",
                        1
                );

        when(customerClient.getCustomer(any()))
                .thenReturn(
                        new CustomerResponse(
                                1L,
                                "Sharath",
                                "Katekar",
                                "mail@test.com",
                                "9876543210",
                                null,
                                "FULL_TIME",
                                "AADHAR",
                                "123456789012",
                                300
                        ));

        when(creditScoreClient.calculateScore(any()))
                .thenReturn(
                        new CreditScoreResponse(300));

        when(encoder.encode(any()))
                .thenReturn("encryptedPin");

        when(repository.save(any()))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        ApplyCardResponse response =
                cardService.applyCard(request);

        assertEquals("GOLD",
                response.cardType());

        assertEquals(20000,
                response.creditLimit());

        assertEquals(
                "APPROVED",
                response.status());
    }
    @Test
    void shouldAllocateVisaCard() {

        ApplyCardRequest request =
                new ApplyCardRequest(
                        "123456789012",
                        1
                );

        when(customerClient.getCustomer(any()))
                .thenReturn(
                        new CustomerResponse(
                                1L,
                                "Sharath",
                                "Katekar",
                                "mail@test.com",
                                "9876543210",
                                null,
                                "FULL_TIME",
                                "AADHAR",
                                "123456789012",
                                150
                        ));

        when(creditScoreClient.calculateScore(any()))
                .thenReturn(
                        new CreditScoreResponse(150));

        when(encoder.encode(any()))
                .thenReturn("encryptedPin");

        when(repository.save(any()))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        ApplyCardResponse response =
                cardService.applyCard(request);

        assertEquals("VISA",
                response.cardType());

        assertEquals(10000,
                response.creditLimit());

        assertEquals(
                "APPROVED",
                response.status());
    }
    @Test
    void shouldAllocateNOCard() {

        ApplyCardRequest request =
                new ApplyCardRequest(
                        "123456789012",
                        1
                );

        when(customerClient.getCustomer(any()))
                .thenReturn(
                        new CustomerResponse(
                                1L,
                                "Sharath",
                                "Katekar",
                                "mail@test.com",
                                "9876543210",
                                null,
                                "FULL_TIME",
                                "AADHAR",
                                "123456789012",
                                50
                        ));

        when(creditScoreClient.calculateScore(any()))
                .thenReturn(
                        new CreditScoreResponse(50));

        when(encoder.encode(any()))
                .thenReturn("encryptedPin");

        when(repository.save(any()))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        ApplyCardResponse response =
                cardService.applyCard(request);

        assertEquals(
                "DOCUMENT_REQUIRED",
                response.status());

        assertEquals(
                "DOCUMENT_REQUIRED",
                response.status());
    }
}