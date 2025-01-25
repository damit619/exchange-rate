package com.exchangerate.service.exchangerate;

import com.exchangerate.config.ExchangeApiProperties;
import com.exchangerate.exception.ExchangeNotFoundException;
import com.exchangerate.modle.CurrencyCode;
import com.exchangerate.modle.exchange.Rate;
import com.exchangerate.modle.exchange.RateInfo;
import com.exchangerate.openexchange.ExchangeResource;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;
import java.util.stream.Collectors;

import static com.exchangerate.modle.CurrencyCode.getCurrencyCode;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@CacheConfig(cacheNames = "exchange-rates")
@Service
public class RateServiceImpl implements RateService {
    private static final Logger logger = LoggerFactory.getLogger(RateServiceImpl.class);

    private final ExchangeResource exchangeResource;
    private final ExchangeApiProperties exchangeApiProperties;

    @Cacheable(key = "#base")
    @Override
    public Rate getExchangeRate(CurrencyCode base) {
        return exchangeResource.getExchangeRateByBaseCurrency(exchangeApiProperties.apiId(), base.name())
                .getRates().entrySet().stream()
                .filter(entry -> CurrencyCode.isCurrencyCodeValid(entry.getKey()))
                .map(this::rateInfo)
                .collect(Collectors.collectingAndThen(toList(), rates ->
                                Rate.builder()
                                        .rates(rates)
                                        .baseCurrency(base)
                                        .build()
                        ));
    }

    @Override
    public RateInfo findTargetRate (CurrencyCode base, CurrencyCode targetCode) {
        return getExchangeRate(base).rates().stream()
                .filter(rateInfo -> rateInfo.currencyCode() == targetCode)
                .findFirst()
                .orElseThrow(() -> new ExchangeNotFoundException("Failed to calculate bill."));
    }

    @Override
    public BigDecimal convert(RateInfo rateInfo, BigDecimal amount) {
        MathContext context = new MathContext(5, RoundingMode.CEILING);
        return rateInfo.rate().multiply(amount, context);
    }

    private RateInfo rateInfo (Map.Entry<String, BigDecimal> entry) {
        return RateInfo.builder()
                .currencyCode(getCurrencyCode(entry.getKey()))
                .rate(entry.getValue())
                .build();
    }

    @CacheEvict(allEntries = true)
    @PostConstruct
    @Scheduled(fixedRateString = "${exchange-rate.open-exchange-api.cache-ttl:10000}")
    public void clearCache(){
        logger.info("RateService#clearCache: Caches  cleared");
    }
}
