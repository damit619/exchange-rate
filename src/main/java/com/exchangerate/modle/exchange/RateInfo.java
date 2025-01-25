package com.exchangerate.modle.exchange;

import com.exchangerate.modle.CurrencyCode;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RateInfo(
        CurrencyCode currencyCode,
        BigDecimal rate
) {
}
