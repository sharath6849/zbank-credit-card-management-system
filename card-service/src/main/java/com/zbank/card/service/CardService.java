package com.zbank.card.service;

import com.zbank.card.dto.ApplyCardRequest;
import com.zbank.card.dto.ApplyCardResponse;
import com.zbank.card.dto.ChangePinRequest;

public interface CardService {

    ApplyCardResponse applyCard(
            ApplyCardRequest request);

    void changePin(
            ChangePinRequest request);
}