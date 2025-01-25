package com.exchangerate.config.feign;

public record FeignClientError (
        Integer status,
        String error,
        String message,
        String description
) {
}
