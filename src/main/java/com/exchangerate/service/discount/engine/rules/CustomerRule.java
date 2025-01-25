package com.exchangerate.service.discount.engine.rules;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CustomerRule extends PercentageRule {

    @Override
    public boolean isApplicable(RuleData ruleData) {
        return !isApplied() && !ruleData.item().isGroceryItem() && ruleData.isCustomer() && ruleData.tenure() > 2;
    }

    @Override
    protected BigDecimal percentage() {
        return new BigDecimal(5);
    }
}
