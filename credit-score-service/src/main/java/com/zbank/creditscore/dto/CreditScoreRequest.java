package com.zbank.creditscore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreditScoreRequest(

        @NotBlank
        String documentNumber,

        @Min(0)
        Integer existingCreditCards

) {
}