package com.exchangerate.service;

import com.exchangerate.config.ExchangeApiProperties;
import com.exchangerate.modle.exchange.Rate;
import com.exchangerate.modle.exchange.RateInfo;
import com.exchangerate.openexchange.ExchangeResource;
import com.exchangerate.service.exchangerate.RateService;
import com.exchangerate.service.exchangerate.RateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static com.exchangerate.modle.CurrencyCode.*;
import static com.exchangerate.service.TestData.getExchangeRates;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RateServiceTest {

    @Mock
    private ExchangeResource exchangeResource;
    @Mock
    private ExchangeApiProperties exchangeApiProperties;

    private RateService rateService;

    @BeforeEach
    public void setUp () {
        rateService = new RateServiceImpl(exchangeResource, exchangeApiProperties);
    }

    @Test
    public void testGetExchangeRateShouldReturnRates () {
        when(exchangeApiProperties.apiId()).thenReturn(UUID.randomUUID().toString());
        when(exchangeResource.getExchangeRateByBaseCurrency(any(), any())).thenReturn(getExchangeRates());
        Rate rate = rateService.getExchangeRate(USD);
        assertThat(rate.baseCurrency(), equalTo(USD));
        assertThat(rate.rates(), hasSize(2));
        assertThat("Should contain currency code INR", rate.rates().stream().anyMatch(rateInfo -> rateInfo.currencyCode() == INR), is(true));
        assertThat("Should contain rate INR 88", rate.rates().stream().anyMatch(rateInfo -> rateInfo.rate().equals(new BigDecimal(88))), is(true));
        assertThat("Should contain currency code AED", rate.rates().stream().anyMatch(rateInfo -> rateInfo.currencyCode() == AED), is(true));
        assertThat("Should contain rate AED 24", rate.rates().stream().anyMatch(rateInfo -> rateInfo.rate().equals(new BigDecimal(24))), is(true));
    }

    @Test
    public void testFindTargetRateShouldReturnCorrectRate () {
        when(exchangeApiProperties.apiId()).thenReturn(UUID.randomUUID().toString());
        when(exchangeResource.getExchangeRateByBaseCurrency(any(), any())).thenReturn(getExchangeRates());
        RateInfo rateInfo = rateService.findTargetRate(USD, INR);

        assertThat("RateInfo should have target currency code INR", rateInfo.currencyCode(), equalTo(INR));
        assertThat("RateInfo should have correct rate 88", rateInfo.rate(), equalTo(new BigDecimal(88)));
    }

    @Test
    public void testConvertShouldBeCorrectRate () {
        BigDecimal conversion = rateService.convert(TestData.rateInfo(USD, new BigDecimal(88)), new BigDecimal(2));
        assertThat(conversion.doubleValue(), equalTo(176.0));
    }

}
