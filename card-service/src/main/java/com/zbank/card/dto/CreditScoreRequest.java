package com.zbank.card.dto;

public record CreditScoreRequest(

        String documentNumber,

        Integer existingCreditCards

) {
}
