package com.exchangerate.service.discount.engine.rules;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Component
public class FixedRule implements DiscountRule<BigDecimal, BigDecimal> {
    private static final BigDecimal HUNDRED = new BigDecimal(100);
    @Override
    public boolean isApplicable(BigDecimal totalAmount) {
        return totalAmount.compareTo(new BigDecimal(100)) > 0;
    }

    @Override
    public BigDecimal apply(BigDecimal totalAmount) {
        if (totalAmount.compareTo(BigDecimal.ZERO) <= 0)
            return BigDecimal.ZERO;
        MathContext roundCtx = new MathContext(5, RoundingMode.CEILING);
        return totalAmount.divide(HUNDRED, roundCtx).multiply(new BigDecimal(5));
    }
}
