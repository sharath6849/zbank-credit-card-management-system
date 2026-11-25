package com.zbank.card.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ApplyCardRequest(

        @NotBlank
        String documentNumber,

        @Min(0)
        Integer existingCreditCards

) {
}