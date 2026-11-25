package com.zbank.card.repository;

import com.zbank.card.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditCardRepository
        extends JpaRepository<CreditCard, Long> {

    Optional<CreditCard>
    findByCardNumber(String cardNumber);
}