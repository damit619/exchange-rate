package com.exchangerate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("exchange-rate.authentication")
public record AuthProperties(String jwtSecret, Long jwtExpirationMillis) {
}
