package com.exchangerate.config.cache;

import com.exchangerate.config.ExchangeApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@EnableCaching
public class CacheConfig {

    private final ExchangeApiProperties exchangeApiProperties;

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(exchangeApiProperties.cacheName());
    }
}
