package com.exchangerate.service.discount.engine;

import com.exchangerate.service.discount.engine.rules.FixedRule;
import com.exchangerate.service.discount.engine.rules.RuleData;
import com.exchangerate.service.discount.engine.rules.PercentageRule;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DiscountRuleEngine {
    private static final Logger logger = LoggerFactory.getLogger(DiscountRuleEngine.class);

    private final List<PercentageRule> percentageRules;
    private final List<FixedRule> fixedRules;

    public BigDecimal runPercentageRule (RuleData item) {
        return percentageRules
                .stream()
                .filter(discountRule -> discountRule.isApplicable(item))
                .map(discountRule -> discountRule.apply(item))
                .peek(discount -> logger.info("Percentage Discount: category={}, discountedAmount={}", item.item().category(), discount))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal runFixedRule (BigDecimal totalAmount) {
        return fixedRules.stream()
                .filter(fixedRule -> fixedRule.isApplicable(totalAmount))
                .map(fixedRule -> fixedRule.apply(totalAmount))
                .peek(discount -> logger.info("Fixed Discount: totalAmount={}, discount={}", totalAmount, discount))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
