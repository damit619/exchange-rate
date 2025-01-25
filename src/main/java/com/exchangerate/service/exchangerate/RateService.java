package com.exchangerate.service.exchangerate;

import com.exchangerate.modle.CurrencyCode;
import com.exchangerate.modle.exchange.Rate;
import com.exchangerate.modle.exchange.RateInfo;

import java.math.BigDecimal;

public interface RateService {

    Rate getExchangeRate (CurrencyCode base);

    RateInfo findTargetRate (CurrencyCode base, CurrencyCode target);

    BigDecimal convert(RateInfo rateInfo, BigDecimal amount);
}
