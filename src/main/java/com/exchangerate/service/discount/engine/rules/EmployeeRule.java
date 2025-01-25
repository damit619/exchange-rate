package com.exchangerate.service.discount.engine.rules;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EmployeeRule extends PercentageRule {

    @Override
    public boolean isApplicable(RuleData ruleData) {
        return !isApplied() && !ruleData.item().isGroceryItem() && ruleData.isEmployee();
    }

    @Override
    protected BigDecimal percentage() {
        return new BigDecimal(30);
    }
}
