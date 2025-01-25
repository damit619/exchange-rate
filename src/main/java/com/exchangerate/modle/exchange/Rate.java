package com.exchangerate.modle.exchange;

import com.exchangerate.modle.CurrencyCode;
import lombok.Builder;

import java.util.List;
@Builder
public record Rate(
        CurrencyCode baseCurrency,
        List<RateInfo> rates
) {
}
