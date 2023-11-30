package com.example.payservice.dto;

import java.util.UUID;

public record PayResponseDto(
        UUID id,
        String address,
        Double valueWei,
        String status,
        UUID gameId
) {
}
