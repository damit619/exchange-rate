package com.exchangerate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("exchange-rate.open-exchange-api")
public record ExchangeApiProperties(
    String apiUrl,
    String apiId,
    String apiLimit,
    String cacheName,
    String cacheTtl
){ }
