package com.exchangerate.service;

import com.exchangerate.modle.CurrencyCode;
import com.exchangerate.modle.bill.BillDetail;
import com.exchangerate.modle.bill.Category;
import com.exchangerate.modle.exchange.RateInfo;
import com.exchangerate.modle.user.UserType;
import com.exchangerate.service.discount.RuleTestData;

import java.math.BigDecimal;
import java.util.List;

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
}
