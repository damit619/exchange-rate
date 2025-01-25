package com.exchangerate.service.discount.engine.rules;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AffiliateRule extends PercentageRule {

    @Override
    protected BigDecimal percentage() {
        return new BigDecimal(10);
    }

    @Override
    public boolean isApplicable(RuleData ruleData) {
        return !isApplied() && !ruleData.item().isGroceryItem() && ruleData.isAffiliate();
    }
}
