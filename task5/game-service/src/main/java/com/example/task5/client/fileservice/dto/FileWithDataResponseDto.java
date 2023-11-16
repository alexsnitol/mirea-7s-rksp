package com.example.task5.client.fileservice.dto;

import java.util.UUID;

public record FileWithDataResponseDto(
        UUID id,
        byte[] data
) {
}
