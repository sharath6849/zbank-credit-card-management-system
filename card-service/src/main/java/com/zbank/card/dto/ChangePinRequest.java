package com.zbank.card.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePinRequest(

        @NotBlank
        String cardNumber,

        @NotBlank
        String firstTimePin,

        @NotBlank
        String documentNumber,

        @NotBlank
        String newPin,

        @NotBlank
        String confirmPin

) {
}