package com.exchangerate.service.discount.engine.rules;

import com.exchangerate.modle.bill.Item;
import com.exchangerate.modle.user.UserType;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RuleData(
        Item item,
        BigDecimal billTotal,
        UserType userType,
        Integer tenure
) {
    public boolean isEmployee () {
        return userType == UserType.EMPLOYEE;
    }

    public boolean isCustomer() {
        return userType == UserType.CUSTOMER;
    }

    public boolean isAffiliate () {
        return userType == UserType.AFFILIATE;
    }
}
