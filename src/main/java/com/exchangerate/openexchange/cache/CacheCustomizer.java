package com.exchangerate.openexchange.cache;

import com.exchangerate.config.ExchangeApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CacheCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

    private final ExchangeApiProperties exchangeApiProperties;

    @Override
    public void customize(ConcurrentMapCacheManager cacheManager) {
        cacheManager.setCacheNames(List.of(exchangeApiProperties.cacheName()));
        cacheManager.setAllowNullValues(false);
    }
}
