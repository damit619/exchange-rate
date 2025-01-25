package com.exchangerate.modle.bill;

import com.exchangerate.modle.CurrencyCode;
import com.exchangerate.modle.user.UserType;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record BillDetail (
        List<Item> items,
        BigDecimal totalAmount,
        CurrencyCode base,
        CurrencyCode target,
        UserType userType,
        Integer tenure
) { }
