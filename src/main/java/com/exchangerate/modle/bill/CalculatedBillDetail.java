package com.exchangerate.modle.bill;


import com.exchangerate.modle.CurrencyCode;

import java.math.BigDecimal;

public record CalculatedBillDetail(BigDecimal netAmount, CurrencyCode targetCurrency) {}
