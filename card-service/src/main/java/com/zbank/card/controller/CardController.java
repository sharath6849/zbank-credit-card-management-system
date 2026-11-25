package com.zbank.card.controller;

import com.zbank.card.dto.ApplyCardRequest;
import com.zbank.card.dto.ApplyCardResponse;
import com.zbank.card.dto.ChangePinRequest;
import com.zbank.card.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/apply")
    public ApplyCardResponse applyCard(
            @Valid
            @RequestBody ApplyCardRequest request) {

        return cardService.applyCard(request);
    }

    @PostMapping("/change-pin")
    public String changePin(
            @RequestBody
            ChangePinRequest request) {

        cardService.changePin(request);

        return "PIN updated successfully";
    }
}