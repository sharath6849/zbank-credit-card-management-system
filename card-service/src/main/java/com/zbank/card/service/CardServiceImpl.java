package com.zbank.card.service;

import com.zbank.card.client.CreditScoreClient;
import com.zbank.card.client.CustomerClient;
import com.zbank.card.dto.*;
import com.zbank.card.entity.AuditLog;
import com.zbank.card.entity.CardStatus;
import com.zbank.card.entity.CardType;
import com.zbank.card.entity.CreditCard;
import com.zbank.card.exception.CardNotFoundException;
import com.zbank.card.exception.InvalidPinException;
import com.zbank.card.exception.PinMismatchException;
import com.zbank.card.repository.AuditLogRepository;
import com.zbank.card.repository.CreditCardRepository;
import com.zbank.card.util.CardNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private static final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

    private final CustomerClient customerClient;
    private final CreditScoreClient creditScoreClient;
    private final CreditCardRepository repository;
    private final AuditLogRepository auditLogRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public ApplyCardResponse applyCard(
            ApplyCardRequest request) {

        CustomerResponse customer =
                customerClient.getCustomer(
                        request.documentNumber());

        CreditScoreResponse creditScoreResponse =
                creditScoreClient.calculateScore(
                        new CreditScoreRequest(
                                request.documentNumber(),
                                request.existingCreditCards()
                        ));

        Integer score =
                creditScoreResponse.creditScore();

        return allocateCard(customer, score);
    }

    @Override
    public void changePin(
            ChangePinRequest request) {

        CreditCard card =
                repository.findByCardNumber(
                                request.cardNumber())
                        .orElseThrow(() ->
                                new CardNotFoundException(
                                        "Card not found"));


        if (!encoder.matches(
                request.firstTimePin(),
                card.getPin())) {

            throw new InvalidPinException(
                    "Invalid first time PIN");
        }

        if (!request.newPin()
                .equals(request.confirmPin())) {

            throw new PinMismatchException(
                    "PIN mismatch");
        }

        if (!request.newPin()
                .matches("\\d{4}")) {

            throw new InvalidPinException(
                    "PIN must contain exactly 4 digits");
        }

        if (request.newPin()
                .equals(request.firstTimePin())) {

            throw new InvalidPinException(
                    "New PIN must be different from current PIN");
        }

        if (!request.confirmPin()
                .matches("\\d{4}")) {

            throw new InvalidPinException(
                    "Confirm PIN must contain exactly 4 digits");
        }

        card.setPin(
                encoder.encode(
                        request.newPin()));

        repository.save(card);

        auditLogRepository.save(

                AuditLog.builder()
                        .customerId(
                                card.getCustomerId())
                        .action("PIN_CHANGED")
                        .createdAt(
                                LocalDateTime.now())
                        .build()
        );
    }

    private ApplyCardResponse allocateCard(
            CustomerResponse customer,
            Integer score) {

        CreditCard card;

        if (score == 500) {

            card = buildCard(
                    customer.id(),
                    score,
                    CardType.PLATINUM,
                    40000,
                    CardStatus.APPROVED);

        } else if (score == 300) {

            card = buildCard(
                    customer.id(),
                    score,
                    CardType.GOLD,
                    20000,
                    CardStatus.APPROVED);

        } else if (score == 150) {

            card = buildCard(
                    customer.id(),
                    score,
                    CardType.VISA,
                    10000,
                    CardStatus.APPROVED);

        } else {

            card = CreditCard.builder()
                    .customerId(customer.id())
                    .creditScore(score)
                    .status(CardStatus.DOCUMENT_REQUIRED)
                    .build();
        }

        CreditCard saved =
                repository.save(card);

        auditLogRepository.save(

                AuditLog.builder()
                        .customerId(saved.getCustomerId())
                        .action("PIN_GENERATED")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        return new ApplyCardResponse(
                saved.getCustomerId(),
                saved.getCreditScore(),
                saved.getCardType() == null ?
                        null :
                        saved.getCardType().name(),
                saved.getCreditLimit(),
                saved.getCardNumber(),
                saved.getStatus().name()
        );
    }

    private CreditCard buildCard(
            Long customerId,
            Integer score,
            CardType cardType,
            Integer limit,
            CardStatus status) {

        String generatedPin =
                generatePin();

        log.info(
                "Generated PIN for testing: {}",
                generatedPin);

        return CreditCard.builder()
                .customerId(customerId)
                .creditScore(score)
                .cardType(cardType)
                .creditLimit(limit)
                .cardNumber(
                        CardNumberGenerator.generate())
                .pin(
                        encoder.encode(
                                generatedPin))
                .status(status)
                .build();
    }

    private String generatePin() {

        return String.format(
                "%04d",
                new Random().nextInt(10000));
    }
}