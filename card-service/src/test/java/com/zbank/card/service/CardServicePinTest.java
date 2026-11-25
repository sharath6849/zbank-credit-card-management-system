package com.zbank.card.service;

import com.zbank.card.client.CreditScoreClient;
import com.zbank.card.client.CustomerClient;
import com.zbank.card.dto.ChangePinRequest;
import com.zbank.card.entity.CreditCard;
import com.zbank.card.exception.InvalidPinException;
import com.zbank.card.exception.PinMismatchException;
import com.zbank.card.repository.AuditLogRepository;
import com.zbank.card.repository.CreditCardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServicePinTest {

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

    @Test
    void shouldChangePinSuccessfully() {

        ChangePinRequest request =
                new ChangePinRequest(
                        "4556123456789012",
                        "1234",
                        "DOC123",
                        "5678",
                        "5678"
                );

        CreditCard card =
                CreditCard.builder()
                        .cardNumber("4556123456789012")
                        .pin("encryptedPin")
                        .customerId(1L)
                        .build();

        when(repository.findByCardNumber(any()))
                .thenReturn(Optional.of(card));

        when(encoder.matches(
                "1234",
                "encryptedPin"))
                .thenReturn(true);

        when(encoder.encode(any()))
                .thenReturn("newEncryptedPin");

        cardService.changePin(request);

        verify(repository)
                .save(any(CreditCard.class));

        verify(auditLogRepository)
                .save(any());
    }

    @Test
    void shouldThrowExceptionForInvalidPin() {

        ChangePinRequest request =
                new ChangePinRequest(
                        "4556123456789012",
                        "9999",
                        "DOC123",
                        "5678",
                        "5678"
                );

        CreditCard card =
                CreditCard.builder()
                        .cardNumber("4556123456789012")
                        .pin("encryptedPin")
                        .customerId(1L)
                        .build();

        when(repository.findByCardNumber(any()))
                .thenReturn(Optional.of(card));

        when(encoder.matches(
                "9999",
                "encryptedPin"))
                .thenReturn(false);

        assertThrows(
                InvalidPinException.class,
                () -> cardService.changePin(request)
        );
    }

    @Test
    void shouldThrowExceptionForPinMismatch() {

        ChangePinRequest request =
                new ChangePinRequest(
                        "4556123456789012",
                        "1234",
                        "DOC123",
                        "5678",
                        "9999"
                );

        CreditCard card =
                CreditCard.builder()
                        .cardNumber("4556123456789012")
                        .pin("encryptedPin")
                        .customerId(1L)
                        .build();

        when(repository.findByCardNumber(any()))
                .thenReturn(Optional.of(card));

        when(encoder.matches(
                "1234",
                "encryptedPin"))
                .thenReturn(true);

        assertThrows(
                PinMismatchException.class,
                () -> cardService.changePin(request)
        );
    }

    @Test
    void shouldThrowExceptionForInvalidPinFormat() {

        ChangePinRequest request =
                new ChangePinRequest(
                        "4556123456789012",
                        "1234",
                        "DOC123",
                        "abcd",
                        "abcd"
                );

        CreditCard card =
                CreditCard.builder()
                        .cardNumber("4556123456789012")
                        .pin("encryptedPin")
                        .customerId(1L)
                        .build();

        when(repository.findByCardNumber(any()))
                .thenReturn(Optional.of(card));

        when(encoder.matches(
                "1234",
                "encryptedPin"))
                .thenReturn(true);

        assertThrows(
                InvalidPinException.class,
                () -> cardService.changePin(request)
        );
    }
}