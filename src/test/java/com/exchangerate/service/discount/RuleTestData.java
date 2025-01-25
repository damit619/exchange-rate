package com.exchangerate.service.discount;

import com.exchangerate.TestUtil;
import com.exchangerate.modle.bill.Category;
import com.exchangerate.modle.bill.Item;
import com.exchangerate.modle.user.UserType;
import com.exchangerate.service.discount.engine.rules.*;

import java.math.BigDecimal;
import java.util.List;

public final class RuleTestData {

    private RuleTestData() {}

    public static RuleData getRuleData (Category category, BigDecimal totalAmt, UserType userType) {
        return RuleData.builder()
                .item(getItem(category))
                .billTotal(totalAmt)
                .userType(userType)
                .tenure(10)
                .build();
    }

    public static Item getItem (Category category) {
        return Item.builder()
                .name(TestUtil.randomStr("name"))
                .value("10")
                .category(category)
                .build();
    }

    public static List<PercentageRule> getPercentageRules () {
        return List.of(
                new EmployeeRule(),
                new AffiliateRule(),
                new CustomerRule()
        );
    }

}
