package com.exchangerate.service;

import com.exchangerate.modle.CurrencyCode;
import com.exchangerate.modle.bill.BillDetail;
import com.exchangerate.modle.bill.Category;
import com.exchangerate.modle.exchange.RateInfo;
import com.exchangerate.modle.user.UserType;
import com.exchangerate.openexchange.dto.OpenExchangeRateDTO;
import com.exchangerate.service.discount.RuleTestData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public final class TestData {

    private TestData() {}

    public static BillDetail billDetail (CurrencyCode base, CurrencyCode target, BigDecimal totalAmount) {
        return BillDetail.builder()
                .items(List.of(RuleTestData.getItem(Category.ELECTRONICS)))
                .base(base)
                .target(target)
                .totalAmount(totalAmount)
                .userType(UserType.EMPLOYEE)
                .tenure(3)
                .build();
    }

    public static RateInfo rateInfo (CurrencyCode code, BigDecimal rate) {
        return RateInfo.builder()
                .currencyCode(code)
                .rate(rate)
                .build();
    }

    public static OpenExchangeRateDTO getExchangeRates () {
        Map<String, BigDecimal> rates = Map.of(
                "INR", new BigDecimal(88),
                "AED", new BigDecimal(24)
        );
        return OpenExchangeRateDTO.builder()
                .base("USD")
                .rates(rates)
                .build();
    }
}
