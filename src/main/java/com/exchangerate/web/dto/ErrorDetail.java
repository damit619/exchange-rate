package com.exchangerate.web.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ErrorDetail (
        LocalDateTime timestamp,
        String path,
        HttpStatus httpStatus,
        List<String> errorDetails
) {
}
