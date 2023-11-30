package com.example.gameservice.client.fileservice.dto;

import java.util.UUID;

public record FileWithDataResponseDto(
        UUID id,
        byte[] data
) {
}
