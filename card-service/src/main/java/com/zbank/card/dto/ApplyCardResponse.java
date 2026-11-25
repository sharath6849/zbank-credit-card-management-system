package com.zbank.card.dto;

public record ApplyCardResponse(

        Long customerId,

        Integer creditScore,

        String cardType,

        Integer creditLimit,

        String cardNumber,

        String status

) {
}