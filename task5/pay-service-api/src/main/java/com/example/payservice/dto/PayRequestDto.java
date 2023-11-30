package com.example.payservice.dto;

import java.util.UUID;

public record PayRequestDto(
        UUID gameId,
        String privateKeyConsumerAccount
) {
}
