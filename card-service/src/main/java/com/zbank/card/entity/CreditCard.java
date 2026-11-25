package com.zbank.card.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "credit_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private Integer creditScore;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private Integer creditLimit;

    private String cardNumber;

    private String pin;

    @Enumerated(EnumType.STRING)
    private CardStatus status;
}